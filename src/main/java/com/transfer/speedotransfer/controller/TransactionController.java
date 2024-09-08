package com.transfer.speedotransfer.controller;

import com.transfer.speedotransfer.dto.TransactionDTO;
import com.transfer.speedotransfer.dto.TransactionRequestDTO;
import com.transfer.speedotransfer.dto.TransactionResponseDTO;
import com.transfer.speedotransfer.exception.custom.ResourceNotFoundException;
import com.transfer.speedotransfer.exception.response.ErrorDetails;
import com.transfer.speedotransfer.service.ITransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Validated
@Tag(name = "Transaction Controller", description = "Transaction controller")
public class TransactionController {

    private final ITransactionService transactionService;

    @Operation(summary = "Transfer money")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = TransactionResponseDTO.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @PostMapping
    public TransactionResponseDTO transfer(@Valid @RequestBody TransactionRequestDTO transactionRequestDTO) throws ResourceNotFoundException {
        return this.transactionService.transfer(transactionRequestDTO);
    }


    @Operation(summary = "Get transactions by ID")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = TransactionDTO.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @GetMapping("/user/{userId}")
    public List<TransactionDTO> transfer(@Valid @PathVariable Long userId) throws ResourceNotFoundException {
        return this.transactionService.getTransactionsByUserId(userId);
    }
}