package com.wcajisaca.accountService.controllers;

import com.wcajisaca.accountService.dtos.AccountDTO;
import com.wcajisaca.accountService.dtos.response.BaseResponse;
import com.wcajisaca.accountService.exception.AccountException;
import com.wcajisaca.accountService.exception.AccountRuntimeException;
import com.wcajisaca.accountService.services.IAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class AccountController {
    private final IAccountService accountService;

    @GetMapping("/simulateError")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> simulateError() throws AccountRuntimeException {
        return ResponseEntity.ok("Hola");
        //return ResponseEntity.internalServerError().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccount(@Valid @RequestBody AccountDTO accountDTO) throws AccountRuntimeException {
        accountService.createAccount(accountDTO);
    }

    @GetMapping
    public ResponseEntity<?> getAllAccount() {
        return ResponseEntity
                .ok(BaseResponse.builder().data(accountService.findAll()).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccount(@PathVariable("id") UUID accountId) throws AccountException {
        return ResponseEntity
                .ok(BaseResponse.builder().data(accountService.getAccountById(accountId)).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@Valid @PathVariable("id") UUID id, @RequestBody AccountDTO accountDto)
            throws AccountRuntimeException {
        AccountDTO account = accountDto.withAccountId(id);
        return ResponseEntity
                .ok(BaseResponse.builder().data(accountService.createAccount(account)).build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccount(@PathVariable("id") UUID accountId) throws AccountException {
        accountService.deleteAccount(accountId);
    }
}