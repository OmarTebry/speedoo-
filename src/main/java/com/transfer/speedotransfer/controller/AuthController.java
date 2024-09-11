package com.transfer.speedotransfer.controller;

import com.transfer.speedotransfer.dto.LoginRequestDTO;
import com.transfer.speedotransfer.dto.LoginResponseDTO;
import com.transfer.speedotransfer.dto.RegisterRequestDTO;
import com.transfer.speedotransfer.dto.RegisterResponseDTO;
import com.transfer.speedotransfer.exception.custom.UserAlreadyExistException;
import com.transfer.speedotransfer.exception.response.ErrorDetails;
import com.transfer.speedotransfer.service.security.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "User Auth Controller", description = "User Auth controller")
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register new User")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = RegisterResponseDTO.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    public RegisterResponseDTO register(@RequestBody @Valid RegisterRequestDTO user) throws UserAlreadyExistException {
        return this.authService.register(user);
    }

    @Operation(summary = "Login and generate JWT")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = LoginResponseDTO.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @CrossOrigin(origins = "https://speedo-production.up.railway.app")
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        return this.authService.login(loginRequestDTO);
    }

}