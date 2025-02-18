package com.wcajisaca.accountService.services.impl;

import com.wcajisaca.accountService.constants.Errors;
import com.wcajisaca.accountService.dtos.AccountDTO;
import com.wcajisaca.accountService.entities.Account;
import com.wcajisaca.accountService.exception.GeneralException;
import com.wcajisaca.accountService.mapper.AccountMapper;
import com.wcajisaca.accountService.repositories.IAccountRepository;
import com.wcajisaca.accountService.services.IAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public AccountDTO getAccountById(UUID accountId) throws GeneralException {
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
                .map(acc -> setUpdateAccount(acc, accountDTO))
                .orElseGet(() -> accountWithBalance(accountDTO));
        return mapper.toAccountDTO(repository.save(account));
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
    private com.wcajisaca.accountService.entities.Account setUpdateAccount(com.wcajisaca.accountService.entities.Account account, AccountDTO accountDTO) {
        return account.withAccountNumber(accountDTO.accountNumber())
                .withInitialBalance(accountDTO.initialBalance())
                .withTypeAccount(accountDTO.typeAccount())
                .withPersonId(accountDTO.personId());
    }

    /**
     * Account with balance
     * @param accountDTO
     * @return
     */
    private com.wcajisaca.accountService.entities.Account accountWithBalance(AccountDTO accountDTO) {
        return mapper.toAccount(accountDTO)
                .withAccountNumber(ThreadLocalRandom.current().nextInt(100000, 999999 + 1))
                .withInitialBalance(INITIAL_BALANCE);
    }
}
