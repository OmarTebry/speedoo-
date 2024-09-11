package com.transfer.speedotransfer.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequestDTO {
    @NotNull
    private Long userId;
    @NotNull
    private String accountName;
    @NotNull
    private String senderAccountNumber;
    @NotNull
    private String recipientAccountNumber;
    @NotNull
    @Positive(message = "Amount must be greater than 0")
    private Double amount;
}
