package com.transfer.speedotransfer.service;
import com.transfer.speedotransfer.dto.PasswordChangeRequestDTO;
import com.transfer.speedotransfer.dto.UpdateUserDTO;
import com.transfer.speedotransfer.dto.UserDTO;
import com.transfer.speedotransfer.entity.User;
import com.transfer.speedotransfer.exception.custom.ResourceNotFoundException;
import com.transfer.speedotransfer.exception.custom.UserAlreadyExistException;
import com.transfer.speedotransfer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Cacheable("users")
    public UserDTO getUserById(Long UserId) throws ResourceNotFoundException {
        return this.userRepository.findById(UserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"))
                .toDTO();
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long userId, UpdateUserDTO update) throws UserAlreadyExistException {


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (update.getName() != null && !update.getName().isBlank()) {
            user.setName(update.getName());
        }

        if (update.getCountry() != null && !update.getCountry().isBlank()) {
            user.setCountry(update.getCountry());
        }

        if (update.getPhoneNumber() != null && !update.getPhoneNumber().isBlank()) {
            user.setPhoneNumber(update.getPhoneNumber());
        }

        if (update.getEmail() != null && !update.getEmail().isBlank()) {

            if (Boolean.TRUE.equals(this.userRepository.existsByEmail(update.getEmail()))) {
                throw new UserAlreadyExistException("User with email " +  update.getEmail() + " already exists");
            }
            user.setEmail(update.getEmail());
        }

        User updatedUser = userRepository.save(user);

        return updatedUser.toDTO();
    }

    @Override
    @Transactional
    public void changePassword(Long userId, PasswordChangeRequestDTO passwordChangeRequestDTO) throws ResourceNotFoundException {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        if (!passwordEncoder.matches(passwordChangeRequestDTO.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect.");
        }

        if (passwordEncoder.matches(passwordChangeRequestDTO.getNewPassword(), user.getPassword())) {
            throw new IllegalArgumentException("New password cannot be the same as the old password.");
        }

        if (passwordChangeRequestDTO.getNewPassword().length() < 8) {
            throw new IllegalArgumentException("New password must be at least 8 characters long.");
        }

        user.setPassword(passwordEncoder.encode(passwordChangeRequestDTO.getNewPassword()));

        userRepository.save(user);
    }
}
