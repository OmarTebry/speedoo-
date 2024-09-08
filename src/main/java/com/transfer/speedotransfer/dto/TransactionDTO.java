package com.transfer.speedotransfer.dto;

import com.transfer.speedotransfer.dto.enums.AccountCurrency;
import com.transfer.speedotransfer.dto.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    private Long id;

    private String senderAccountNumber;

    private String recipientAccountNumber;

    private Double amount;

    private LocalDateTime transactionDate;

    private AccountCurrency currency;

    private TransactionStatus status;

    private String message;

    private String direction;
}