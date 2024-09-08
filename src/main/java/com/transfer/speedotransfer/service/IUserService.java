package com.transfer.speedotransfer.service;

import com.transfer.speedotransfer.dto.PasswordChangeRequestDTO;
import com.transfer.speedotransfer.dto.UpdateUserDTO;
import com.transfer.speedotransfer.dto.UserDTO;
import com.transfer.speedotransfer.exception.custom.ResourceNotFoundException;
import com.transfer.speedotransfer.exception.custom.UserAlreadyExistException;

public interface IUserService {

    /**
     * Get user by id
     *
     * @param userId the user id
     * @return the created user
     * @throws ResourceNotFoundException if the user is not found
     */
    UserDTO getUserById(Long userId) throws ResourceNotFoundException;

    UserDTO updateUser(Long userId, UpdateUserDTO update) throws UserAlreadyExistException;

    void changePassword(Long userId, PasswordChangeRequestDTO passwordChangeRequestDTO) throws ResourceNotFoundException;
}
