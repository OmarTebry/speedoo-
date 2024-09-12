package com.transfer.speedotransfer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transfer.speedotransfer.dto.LoginRequestDTO;
import com.transfer.speedotransfer.dto.LoginResponseDTO;
import com.transfer.speedotransfer.dto.RegisterRequestDTO;
import com.transfer.speedotransfer.dto.RegisterResponseDTO;
import com.transfer.speedotransfer.exception.TransferServiceExceptionHandler;
import com.transfer.speedotransfer.exception.custom.UserAlreadyExistException;
import com.transfer.speedotransfer.service.security.IAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticationControllerTest {

    @Mock
    private IAuthService authService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(new TransferServiceExceptionHandler()) // Add global exception handler if needed
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testRegister_Success() throws Exception {
        RegisterRequestDTO requestDTO = new RegisterRequestDTO();
        requestDTO.setName("John Doe");
        requestDTO.setEmail("johndoe@example.com");
        requestDTO.setPassword("password123");
        requestDTO.setCountry("USA");

        RegisterResponseDTO responseDTO = RegisterResponseDTO.builder()
                .id(1L)
                .name("John Doe")
                .email("johndoe@example.com")
                .build();

        when(authService.register(any(RegisterRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDTO)));
    }

    @Test
    public void testRegister_UserAlreadyExists() throws Exception {
        RegisterRequestDTO requestDTO = new RegisterRequestDTO();
        requestDTO.setName("John Doe");
        requestDTO.setEmail("johndoe@example.com");
        requestDTO.setPassword("password123");
        requestDTO.setCountry("USA");

        doThrow(new UserAlreadyExistException("User already exists")).when(authService).register(any(RegisterRequestDTO.class));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegister_InvalidRequest() throws Exception {
        RegisterRequestDTO requestDTO = new RegisterRequestDTO(); // Missing required fields

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> result.getResolvedException().getClass().equals(MethodArgumentNotValidException.class));
    }

    @Test
    public void testLogin_Success() throws Exception {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setEmail("johndoe@example.com");
        loginRequestDTO.setPassword("password123");

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setToken("jwt-token");
        loginResponseDTO.setMessage("Login Successful");
        loginResponseDTO.setStatus(HttpStatus.ACCEPTED);

        when(authService.login(any(LoginRequestDTO.class))).thenReturn(loginResponseDTO);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(loginResponseDTO)));
    }

    @Test
    public void testLogin_InvalidCredentials() throws Exception {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setEmail("johndoe@example.com");
        loginRequestDTO.setPassword("wrongpassword");

        when(authService.login(any(LoginRequestDTO.class))).thenThrow(new BadCredentialsException("Invalid credentials"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDTO)))
                .andExpect(status().isUnauthorized());
    }
}