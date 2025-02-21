package com.wcajisaca.accountService.services;

import com.wcajisaca.accountService.dtos.AccountDTO;
import com.wcajisaca.accountService.exception.AccountException;
import org.springframework.transaction.annotation.Transactional;

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
     * @throws AccountException
     */
    AccountDTO getAccountById(UUID accountId) throws AccountException;

    /**
     * Delete account
     * @param accountId
     * @throws AccountException
     */
    void deleteAccount(UUID accountId) throws AccountException;

    /**
     * Delete account by client
     * @param personId
     * @throws AccountException
     */
    void deleteAccountByClient(UUID personId) throws AccountException;
}
