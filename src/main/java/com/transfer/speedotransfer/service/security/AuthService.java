package com.transfer.speedotransfer.service.security;

import com.transfer.speedotransfer.dto.LoginRequestDTO;
import com.transfer.speedotransfer.dto.LoginResponseDTO;
import com.transfer.speedotransfer.dto.RegisterRequestDTO;
import com.transfer.speedotransfer.dto.RegisterResponseDTO;
import com.transfer.speedotransfer.dto.enums.AccountCurrency;
import com.transfer.speedotransfer.dto.enums.AccountType;
import com.transfer.speedotransfer.entity.Account;
import com.transfer.speedotransfer.entity.User;
import com.transfer.speedotransfer.exception.custom.UserAlreadyExistException;
import com.transfer.speedotransfer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;


    @Transactional
    public RegisterResponseDTO register(RegisterRequestDTO userRequest) throws UserAlreadyExistException {

        if (Boolean.TRUE.equals(this.userRepository.existsByEmail(userRequest.getEmail()))) {
            throw new UserAlreadyExistException("User with email " +  userRequest.getEmail() + " already exists");
        }

        if ( userRequest.getPassword().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }

        User user = User.builder()
                .email(userRequest.getEmail())
                .password(this.passwordEncoder.encode(userRequest.getPassword()))
                .name(userRequest.getName())
                .country(userRequest.getCountry())
                .dateOfBirth(userRequest.getDateOfBirth())
                .build();

        Account account = Account.builder()
                .balance(1000.0)
                .accountType(AccountType.SAVINGS)
                .accountDescription("Savings Account")
                .accountName("Savings Account")
                .currency(AccountCurrency.EGP)
                .accountNumber(new SecureRandom().nextInt(1000000000) + "")
                .user(user)
                .build();

        user.getAccounts().add(account);

        User savedUser = userRepository.save(user);

        return savedUser.toResponse();
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        return LoginResponseDTO.builder()
                .token(jwt)
                .message("Login Successful")
                .status(HttpStatus.ACCEPTED)
                .tokenType("Bearer")
                .build();
    }

}
