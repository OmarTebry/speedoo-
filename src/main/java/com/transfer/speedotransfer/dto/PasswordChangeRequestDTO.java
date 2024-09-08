package com.transfer.speedotransfer.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeRequestDTO {

    @NotNull
    private String oldPassword;

    @NotNull
    private String newPassword;
}