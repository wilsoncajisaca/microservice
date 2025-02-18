package com.wcajisaca.accountService.controllers;

import com.wcajisaca.accountService.constants.Commons;
import com.wcajisaca.accountService.dtos.AccountDTO;
import com.wcajisaca.accountService.exception.GeneralException;
import com.wcajisaca.accountService.exception.RequestValidationException;
import com.wcajisaca.accountService.services.IAccountService;
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

    @GetMapping
    public ResponseEntity<?> getAllAccount() throws GeneralException {
        return ResponseEntity.ok()
                .body(accountService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccount(@PathVariable("id") UUID accountId) throws GeneralException {
        return ResponseEntity.ok()
                .body(accountService.getAccountById(accountId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@Valid @PathVariable("id") UUID id, @RequestBody AccountDTO accountDto,
                                          Errors errors) throws RequestValidationException {
        Commons.validateFieldRequest(errors);
        return ResponseEntity
                .ok(accountService.createAccount(accountDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccount(@PathVariable("id") UUID accountId) throws GeneralException {
        accountService.deleteAccount(accountId);
    }
}