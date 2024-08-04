package com.wcajisaca.challengeAccount.controllers;

import com.wcajisaca.challengeAccount.constants.Commons;
import com.wcajisaca.challengeAccount.dtos.AccountDTO;
import com.wcajisaca.challengeAccount.exception.GeneralException;
import com.wcajisaca.challengeAccount.exception.RequestValidationException;
import com.wcajisaca.challengeAccount.services.IAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class AccountController {
    private final IAccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccount(@Valid @RequestBody AccountDTO accountDTO,
                              Errors errors) throws RequestValidationException {
        Commons.validateFieldRequest(errors);
        accountService.createAccount(accountDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccount(@PathVariable UUID accountId) throws GeneralException {
        return ResponseEntity.ok()
                .body(accountService.getAccountById(accountId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@Valid @PathVariable UUID id, @RequestBody AccountDTO accountDto,
                                          Errors errors) throws GeneralException, RequestValidationException {
        Commons.validateFieldRequest(errors);
        AccountDTO account = accountService.getAccountById(id);
        accountDto.setAccountId(account.getAccountId());
        return ResponseEntity.ok(accountService.createAccount(accountDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccount(@PathVariable UUID accountId) throws GeneralException {
        accountService.deleteAccount(accountId);
    }
}