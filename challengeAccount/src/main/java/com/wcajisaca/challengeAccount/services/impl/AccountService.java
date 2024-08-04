package com.wcajisaca.challengeAccount.services.impl;

import com.wcajisaca.challengeAccount.constants.Errors;
import com.wcajisaca.challengeAccount.dtos.AccountDTO;
import com.wcajisaca.challengeAccount.entities.Account;
import com.wcajisaca.challengeAccount.exception.GeneralException;
import com.wcajisaca.challengeAccount.mapper.Mapper;
import com.wcajisaca.challengeAccount.repositories.IAccountRepository;
import com.wcajisaca.challengeAccount.services.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {
    private final IAccountRepository repository;

    @Override
    public List<AccountDTO> findAll() {
        return repository.findAll().stream()
                .map(Mapper::toAccountDTO)
                .collect(Collectors.toList());
    }

    public AccountDTO getAccountById(UUID accountId) throws GeneralException {
        return repository.findById(accountId)
                .map(Mapper::toAccountDTO)
                .orElseThrow(Errors::notFoundAccount);
    }

    public AccountDTO createAccount(AccountDTO accountDTO) {
        Account account = Mapper.toAccount(accountDTO);
        account.setAccountNumber(ThreadLocalRandom.current().nextInt(100000, 999999 + 1));
        account.setInitialBalance(0.0);
        account.setStatus(Boolean.TRUE);
        account = repository.save(account);
        return Mapper.toAccountDTO(account);
    }

    public void deleteAccount(UUID accountId) throws GeneralException {
        this.repository.findById(accountId).orElseThrow(Errors::notFoundAccount);
        repository.deleteById(accountId);
    }
}
