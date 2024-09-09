package com.transfer.speedotransfer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDTO {

    private Long userid;
    private String token;
    private String tokenType;
    private String message;
    private HttpStatus status;
}
