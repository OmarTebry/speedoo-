package com.transfer.speedotransfer.service;

import com.transfer.speedotransfer.dto.AccountDTO;
import com.transfer.speedotransfer.dto.CreateAccountDTO;
import com.transfer.speedotransfer.dto.UpdateAccountDTO;
import com.transfer.speedotransfer.entity.Account;
import com.transfer.speedotransfer.exception.custom.ResourceNotFoundException;

public interface IAccountService {

    /**
     * Create a new account
     *
     * @param accountDTO the account to be created
     * @return the created account
     * @throws ResourceNotFoundException if the account is not found
     */
    AccountDTO createAccount(CreateAccountDTO accountDTO) throws ResourceNotFoundException;

    /**
     * Get account by id
     *
     * @param accountId the account id
     * @return the account
     * @throws ResourceNotFoundException if the account is not found
     */
    AccountDTO getAccountById(Long accountId) throws ResourceNotFoundException;

    Account getAccountByNum(String accountNum) throws ResourceNotFoundException;

    AccountDTO updateAccount(Long accountId, UpdateAccountDTO accountDTO);

    void deleteAccount(Long accountId);



}
