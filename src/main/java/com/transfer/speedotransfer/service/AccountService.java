package com.transfer.speedotransfer.service;


import com.transfer.speedotransfer.dto.AccountDTO;
import com.transfer.speedotransfer.dto.CreateAccountDTO;
import com.transfer.speedotransfer.dto.UpdateAccountDTO;
import com.transfer.speedotransfer.entity.Account;
import com.transfer.speedotransfer.entity.User;
import com.transfer.speedotransfer.exception.custom.ResourceNotFoundException;
import com.transfer.speedotransfer.repository.AccountRepository;
import com.transfer.speedotransfer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public AccountDTO createAccount(CreateAccountDTO accountDTO) throws ResourceNotFoundException {

        User user = this.userRepository.findById(accountDTO.getUserId()).orElseThrow(()
                -> new ResourceNotFoundException("User with id " + accountDTO.getUserId() + " not found"));

        Account account = Account.builder()
                .accountNumber(new SecureRandom().nextInt(1000000000) + "")
                .accountType(accountDTO.getAccountType())
                .accountName(accountDTO.getAccountName())
                .accountDescription(accountDTO.getAccountDescription())
                .currency(accountDTO.getCurrency())
                .balance(0.0)
                .user(user)
                .build();

        Account savedAccount = this.accountRepository.save(account);

        return savedAccount.toDTO();
    }

    @Override
    public AccountDTO getAccountById(Long accountId) throws ResourceNotFoundException {
        return this.accountRepository.findById(accountId).orElseThrow(()
                -> new ResourceNotFoundException("Account not found")).toDTO();
    }

    @Override
    public Account getAccountByNum(String accountNum) throws ResourceNotFoundException {
        Optional<Account> accountOpt = accountRepository.findByAccountNumber(accountNum);

        return accountOpt.orElse(null);
    }

    @Override
    public AccountDTO updateAccount(Long accountId, UpdateAccountDTO accountDTO) {
        return null;
    }

    @Override
    public void deleteAccount(Long accountId) {

    }

}
