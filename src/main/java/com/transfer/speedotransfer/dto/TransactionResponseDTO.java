package com.transfer.speedotransfer.dto;


import com.transfer.speedotransfer.dto.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponseDTO {
    private TransactionStatus status;
    private LocalDateTime transactionDate;
}
