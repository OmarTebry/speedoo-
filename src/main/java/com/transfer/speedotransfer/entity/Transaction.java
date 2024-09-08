package com.transfer.speedotransfer.entity;

import com.transfer.speedotransfer.dto.TransactionDTO;
import com.transfer.speedotransfer.dto.TransactionResponseDTO;
import com.transfer.speedotransfer.dto.enums.AccountCurrency;
import com.transfer.speedotransfer.dto.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String senderAccountNumber;

    @Column(nullable = false)
    private String recipientAccountNumber;

    @Column(nullable = false)
    private Double amount;

    @CreationTimestamp
    private LocalDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountCurrency currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(nullable = false)
    private String message;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public TransactionDTO toDTO() {
        return TransactionDTO.builder()
                .id(this.id)
                .senderAccountNumber(this.senderAccountNumber)
                .recipientAccountNumber(this.recipientAccountNumber)
                .amount(this.amount)
                .transactionDate(this.transactionDate)
                .currency(this.currency)
                .status(this.status)
                .message(this.message)
                .build();
    }

    public TransactionResponseDTO toResponse() {
        return TransactionResponseDTO.builder()
                .transactionDate(this.transactionDate)
                .status(this.status)
                .build();
    }

}