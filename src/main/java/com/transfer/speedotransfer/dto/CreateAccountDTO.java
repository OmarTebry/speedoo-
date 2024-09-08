package com.transfer.speedotransfer.dto;

import com.transfer.speedotransfer.dto.enums.AccountCurrency;
import com.transfer.speedotransfer.dto.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAccountDTO {

    @NotNull
    private AccountType accountType;

    @NotNull
    private AccountCurrency currency;

    @NotBlank
    private String accountName;

    @NotBlank
    private String accountDescription;

    @NotNull
    private Long userId;

}

