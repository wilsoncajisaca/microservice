package com.wcajisaca.accountService.services.impl;

import com.wcajisaca.accountService.constants.Errors;
import com.wcajisaca.accountService.dtos.AccountDTO;
import com.wcajisaca.accountService.entities.Account;
import com.wcajisaca.accountService.exception.AccountException;
import com.wcajisaca.accountService.mapper.AccountMapper;
import com.wcajisaca.accountService.repositories.IAccountRepository;
import com.wcajisaca.accountService.services.IAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.wcajisaca.accountService.constants.ChallengeAccountConstant.INITIAL_BALANCE;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService implements IAccountService {
    private final IAccountRepository repository;
    private final AccountMapper mapper;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<AccountDTO> findAll() {
        log.info("Find all accounts");
        return repository.findAll().stream()
                .map(mapper::toAccountDTO)
                .collect(Collectors.toList());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public AccountDTO getAccountById(UUID accountId) throws AccountException {
        log.info("Find account by id: {}", accountId);
        return repository.findById(accountId)
                .map(mapper::toAccountDTO)
                .orElseThrow(Errors::notFoundAccount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public AccountDTO createAccount(AccountDTO accountDTO) {
        log.info("Create account");
        Account account = Optional.ofNullable(accountDTO.accountId())
                .flatMap(repository::findById)
                .map(acc -> updateAccount(acc, accountDTO))
                .orElseGet(() -> accountWithBalance(accountDTO));
        return mapper.toAccountDTO(repository.save(account));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteAccount(UUID accountId) throws AccountException {
        log.info("Delete account by id: {}", accountId);
        Account account = repository.findById(accountId)
                .orElseThrow(Errors::notFoundAccount);
        account.setStatus(Boolean.FALSE);
        repository.save(account);
    }

    /**
     * Set update account
     * @param account
     * @param accountDTO
     * @return
     */
    private Account updateAccount(Account account, AccountDTO accountDTO) {
        return mapper.updateAccountFromDTO(accountDTO, account);
    }

    /**
     * Account with balance
     * @param accountDTO
     * @return
     */
    private Account accountWithBalance(AccountDTO accountDTO) {
        return mapper.toAccount(accountDTO)
                .withAccountNumber(ThreadLocalRandom.current().nextInt(100000, 999999 + 1))
                .withInitialBalance(INITIAL_BALANCE);
    }
}
