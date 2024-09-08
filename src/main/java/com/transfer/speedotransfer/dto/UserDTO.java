package com.transfer.speedotransfer.dto;


import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class UserDTO {

    private Long id;

    private String name;

    private String email;

    private LocalDate dateOfBirth;

    private String country;

    private String phoneNumber;  // Phone number, can be null

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Set<AccountDTO> accounts;

}