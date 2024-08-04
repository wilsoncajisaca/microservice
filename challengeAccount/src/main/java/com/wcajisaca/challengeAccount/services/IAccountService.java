package com.wcajisaca.challengeAccount.services;

import com.wcajisaca.challengeAccount.dtos.AccountDTO;
import com.wcajisaca.challengeAccount.exception.GeneralException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface for account service
 * @author wilson
 */
public interface IAccountService {
    List<AccountDTO> findAll();
    AccountDTO createAccount(AccountDTO accountDTO);
    AccountDTO getAccountById(UUID accountId) throws GeneralException;
    void deleteAccount(UUID accountId) throws GeneralException;
}
