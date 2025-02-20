package com.wcajisaca.accountService.controllers;

import com.wcajisaca.accountService.dtos.MovementsDTO;
import com.wcajisaca.accountService.dtos.response.BaseResponse;
import com.wcajisaca.accountService.exception.AccountException;
import com.wcajisaca.accountService.exception.AccountRuntimeException;
import com.wcajisaca.accountService.services.IMovementsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovementsController {
    private final IMovementsService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createMovement(@Valid @RequestBody MovementsDTO movementsDTO) throws AccountException, AccountRuntimeException {
        service.createMovement(movementsDTO);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<?> getMovement(@PathVariable("accountId") UUID accountId) throws AccountException {
        return ResponseEntity
                .ok(BaseResponse.builder().data(service.getMovementByAccountId(accountId)).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @Valid @RequestBody MovementsDTO movementDTO) throws AccountException, AccountRuntimeException {
        MovementsDTO movementUpdate = movementDTO.withMovementId(service.getMovementById(id).movementId());
        return ResponseEntity
                .ok(BaseResponse.builder().data(service.createMovement(movementUpdate)).build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMovement(@PathVariable("id") UUID movementId) {
        service.deleteMovement(movementId);
    }
}