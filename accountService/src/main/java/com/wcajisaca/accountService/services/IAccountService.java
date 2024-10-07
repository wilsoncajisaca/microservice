package com.wcajisaca.accountService.services;

import com.wcajisaca.accountService.dtos.AccountDTO;
import com.wcajisaca.accountService.exception.GeneralException;

import java.util.List;
import java.util.UUID;

/**
 * Interface for account service
 * @author wilson
 */
public interface IAccountService {
    /**
     * Find all accounts
     * @return
     */
    List<AccountDTO> findAll();

    /**
     * Create account
     * @param accountDTO
     * @return
     */
    AccountDTO createAccount(AccountDTO accountDTO);

    /**
     * Get account by id
     * @param accountId
     * @return
     * @throws GeneralException
     */
    AccountDTO getAccountById(UUID accountId) throws GeneralException;

    /**
     * Delete account
     * @param accountId
     * @throws GeneralException
     */
    void deleteAccount(UUID accountId) throws GeneralException;
}
