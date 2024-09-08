package com.transfer.speedotransfer.service.security;

import com.transfer.speedotransfer.dto.LoginRequestDTO;
import com.transfer.speedotransfer.dto.LoginResponseDTO;
import com.transfer.speedotransfer.dto.RegisterRequestDTO;
import com.transfer.speedotransfer.dto.RegisterResponseDTO;
import com.transfer.speedotransfer.exception.custom.UserAlreadyExistException;

public interface IAuthService {

    /**
     * Register a new user
     *
     * @param user the user to be registered
     * @return the registered user
     * @throws UserAlreadyExistException if the user already exists
     */
    RegisterResponseDTO register(RegisterRequestDTO user) throws UserAlreadyExistException;


    /**
     * Login a user
     *
     * @param loginRequestDTO login details
     * @return login response @{@link LoginResponseDTO}
     */
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
}

