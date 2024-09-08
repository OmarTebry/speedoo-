package com.transfer.speedotransfer.controller;

import com.transfer.speedotransfer.dto.AccountDTO;
import com.transfer.speedotransfer.dto.CreateAccountDTO;
import com.transfer.speedotransfer.exception.custom.ResourceNotFoundException;
import com.transfer.speedotransfer.exception.response.ErrorDetails;
import com.transfer.speedotransfer.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
@Validated
@Tag(name = "Account Controller", description = "Account controller")
public class AccountController {

    private final IAccountService accountService;

    @Operation(summary = "Create new Account")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = AccountDTO.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @PostMapping
    public AccountDTO createAccount(@Valid @RequestBody CreateAccountDTO accountDTO) throws ResourceNotFoundException {
        return this.accountService.createAccount(accountDTO);
    }

    @Operation(summary = "Get Account by Id")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = AccountDTO.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @GetMapping("/{accountId}")
    public AccountDTO getAccountById(@PathVariable Long accountId) throws ResourceNotFoundException {
        return this.accountService.getAccountById(accountId);
    }
}
