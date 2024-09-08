package com.transfer.speedotransfer.controller;

import com.transfer.speedotransfer.dto.PasswordChangeRequestDTO;
import com.transfer.speedotransfer.dto.UpdateUserDTO;
import com.transfer.speedotransfer.dto.UserDTO;
import com.transfer.speedotransfer.exception.custom.ResourceNotFoundException;
import com.transfer.speedotransfer.exception.custom.UserAlreadyExistException;
import com.transfer.speedotransfer.exception.response.ErrorDetails;
import com.transfer.speedotransfer.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Validated
@Tag(name = "User Controller", description = "User controller")
public class UserController {

    private final IUserService userService;

    @Operation(summary = "Get user by id")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable Long userId) throws ResourceNotFoundException {
        return this.userService.getUserById(userId);
    }

    @Operation(summary = "Update user")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @PutMapping("/updateInfo/{userId}")
    public UserDTO updateUser(@PathVariable Long userId, @Valid @RequestBody UpdateUserDTO updateUserDTO) throws UserAlreadyExistException {
        return this.userService.updateUser(userId, updateUserDTO);
    }

    @Operation(summary = "Change user password")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = String.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @PutMapping("/updatePass/{userId}")
    public ResponseEntity<String> changePassword(@PathVariable Long userId, @Valid @RequestBody PasswordChangeRequestDTO passwordChangeRequestDTO) throws ResourceNotFoundException {
        userService.changePassword(userId, passwordChangeRequestDTO);
        return ResponseEntity.ok("Password changed successfully.");
    }

}

