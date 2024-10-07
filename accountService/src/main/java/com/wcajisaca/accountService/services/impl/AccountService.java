package com.wcajisaca.accountService.services.impl;

import com.wcajisaca.accountService.constants.Errors;
import com.wcajisaca.accountService.dtos.AccountDTO;
import com.wcajisaca.accountService.entities.Account;
import com.wcajisaca.accountService.exception.GeneralException;
import com.wcajisaca.accountService.mapper.Mapper;
import com.wcajisaca.accountService.repositories.IAccountRepository;
import com.wcajisaca.accountService.services.IAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.wcajisaca.accountService.constants.ChallengeAccountConstant.INITIAL_BALANCE;
import static com.wcajisaca.accountService.constants.ChallengeAccountConstant.RANDOM_ACCOUNT_NUMBER;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService implements IAccountService {
    private final IAccountRepository repository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AccountDTO> findAll() {
        log.info("Find all accounts");
        return repository.findAll().stream()
                .map(Mapper::toAccountDTO)
                .collect(Collectors.toList());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public AccountDTO getAccountById(UUID accountId) throws GeneralException {
        log.info("Find account by id: {}", accountId);
        return repository.findById(accountId)
                .map(Mapper::toAccountDTO)
                .orElseThrow(Errors::notFoundAccount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        log.info("Create account");
        Account account = Optional.ofNullable(accountDTO.getAccountId())
                .flatMap(repository::findById)
                .map(acc -> setUpdateAccount(acc, accountDTO))
                .orElseGet(() -> Mapper.toAccount(accountDTO)
                        .withAccountNumber(RANDOM_ACCOUNT_NUMBER)
                        .withInitialBalance(INITIAL_BALANCE));
        return Mapper.toAccountDTO(repository.save(account));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAccount(UUID accountId) throws GeneralException {
        log.info("Delete account by id: {}", accountId);
        repository.findById(accountId)
                .map(account -> {
                    account.setStatus(Boolean.FALSE);
                    return repository.save(account);
                }).orElseThrow(Errors::notFoundAccount);
    }

    /**
     * Set update account
     * @param account
     * @param accountDTO
     * @return
     */
    private Account setUpdateAccount(Account account, AccountDTO accountDTO) {
        return account.withAccountNumber(accountDTO.getAccountNumber())
                .withInitialBalance(accountDTO.getInitialBalance())
                .withTypeAccount(accountDTO.getTypeAccount())
                .withPersonId(accountDTO.getPersonId());
    }
}
