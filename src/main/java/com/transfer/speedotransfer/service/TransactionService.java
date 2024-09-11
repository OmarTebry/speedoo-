package com.transfer.speedotransfer.service;


import com.transfer.speedotransfer.dto.TransactionDTO;
import com.transfer.speedotransfer.dto.TransactionRequestDTO;
import com.transfer.speedotransfer.dto.TransactionResponseDTO;
import com.transfer.speedotransfer.dto.enums.TransactionStatus;
import com.transfer.speedotransfer.entity.Account;
import com.transfer.speedotransfer.entity.Transaction;
import com.transfer.speedotransfer.entity.User;
import com.transfer.speedotransfer.exception.custom.ResourceNotFoundException;
import com.transfer.speedotransfer.repository.AccountRepository;
import com.transfer.speedotransfer.repository.TransactionRepository;
import com.transfer.speedotransfer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {

    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;

    private final UserRepository userRepository;

    @Autowired
    AccountService accountService;

    @Override
    @Transactional
    public TransactionResponseDTO transfer(TransactionRequestDTO transactionRequestDTO) throws ResourceNotFoundException {

        User user = this.userRepository.findById(transactionRequestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + transactionRequestDTO.getUserId() + " not found"));


        Account sender = accountService.getAccountByNum(transactionRequestDTO.getSenderAccountNumber());
        Account recipient = accountService.getAccountByNum(transactionRequestDTO.getRecipientAccountNumber());


        if (sender == null || !Objects.equals(user.getId(), sender.getUser().getId())) {
            throw new ResourceNotFoundException("Sender account with number " + transactionRequestDTO.getSenderAccountNumber() + " is incorrect");
        }
        if (recipient == null) {
            throw new ResourceNotFoundException("Recipient account with number " + transactionRequestDTO.getRecipientAccountNumber() + " not found");
        }
        if (!recipient.getAccountName().equals(transactionRequestDTO.getAccountName())) {
            throw new IllegalArgumentException("Account name does not match the provided account number");
        }


        if (sender.getBalance() < transactionRequestDTO.getAmount()) {
            Transaction failedTransaction = Transaction.builder()
                    .senderAccountNumber(transactionRequestDTO.getSenderAccountNumber())
                    .recipientAccountNumber(transactionRequestDTO.getRecipientAccountNumber())
                    .amount(transactionRequestDTO.getAmount())
                    .currency(sender.getCurrency())
                    .status(TransactionStatus.FAILED)
                    .message("Insufficient Funds, Invalid Transaction.")
                    .user(user)
                    .build();

            this.transactionRepository.save(failedTransaction);
            return failedTransaction.toResponse();
        }

        if (!sender.getCurrency().equals(recipient.getCurrency())) {
            Transaction failedTransaction = Transaction.builder()
                    .senderAccountNumber(transactionRequestDTO.getSenderAccountNumber())
                    .recipientAccountNumber(transactionRequestDTO.getRecipientAccountNumber())
                    .amount(transactionRequestDTO.getAmount())
                    .currency(sender.getCurrency())
                    .status(TransactionStatus.FAILED)
                    .message("Currency mismatch: Transaction cannot be completed between different currencies.")
                    .user(user)
                    .build();

            this.transactionRepository.save(failedTransaction);
            return failedTransaction.toResponse();
        }

        Transaction successfulTransaction = Transaction.builder()
                .senderAccountNumber(transactionRequestDTO.getSenderAccountNumber())
                .recipientAccountNumber(transactionRequestDTO.getRecipientAccountNumber())
                .amount(transactionRequestDTO.getAmount())
                .currency(sender.getCurrency())
                .status(TransactionStatus.SUCCEEDED)
                .message("Transaction Successful.")
                .user(user)
                .build();


        sender.setBalance(sender.getBalance() - transactionRequestDTO.getAmount());
        recipient.setBalance(recipient.getBalance() + transactionRequestDTO.getAmount());


        Transaction savedTransaction = this.transactionRepository.save(successfulTransaction);
        return savedTransaction.toResponse();
    }

    @Override
    public List<TransactionDTO> getTransactionsByUserId(Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        // Get the account numbers associated with the user
        Set<String> userAccountNumbers = user.getAccounts().stream()
                .map(Account::getAccountNumber)
                .collect(Collectors.toSet());

        // Fetch all transactions where the user is either the sender or recipient, sorted by date in descending order
        List<Transaction> transactions = transactionRepository.findAllBySenderAccountNumberInOrRecipientAccountNumberIn(
                userAccountNumbers, userAccountNumbers, Sort.by(Sort.Direction.DESC, "transactionDate")
        );

        // Convert transactions to DTO and determine direction (sent/received)
        return transactions.stream()
                .map(transaction -> {
                    TransactionDTO transactionDTO = transaction.toDTO();

                    // Set the direction based on whether the user is the sender or recipient
                    if (userAccountNumbers.contains(transaction.getSenderAccountNumber())) {
                        transactionDTO.setDirection("sent");
                    } else if (userAccountNumbers.contains(transaction.getRecipientAccountNumber())) {
                        transactionDTO.setDirection("received");
                    }

                    return transactionDTO;
                })
                .collect(Collectors.toList());
    }
}
