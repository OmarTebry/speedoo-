package com.transfer.speedotransfer.service;

import com.transfer.speedotransfer.dto.TransactionDTO;
import com.transfer.speedotransfer.dto.TransactionRequestDTO;
import com.transfer.speedotransfer.dto.TransactionResponseDTO;
import com.transfer.speedotransfer.exception.custom.ResourceNotFoundException;

import java.util.List;

public interface ITransactionService {
    public TransactionResponseDTO transfer(TransactionRequestDTO transactionRequestDTO)throws ResourceNotFoundException;
    public List<TransactionDTO> getTransactionsByUserId(Long userId) throws ResourceNotFoundException;
}
