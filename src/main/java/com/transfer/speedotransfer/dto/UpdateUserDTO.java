package com.transfer.speedotransfer.dto;


import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserDTO {

    private String name;

    private String country;

    private String phoneNumber;

    @Email
    private String email;
}
