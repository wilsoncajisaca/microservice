package com.wcajisaca.accountService.controllers;

import com.wcajisaca.accountService.constants.Commons;
import com.wcajisaca.accountService.dtos.MovementsDTO;
import com.wcajisaca.accountService.exception.GeneralException;
import com.wcajisaca.accountService.exception.RequestValidationException;
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
    public void createMovement(@Valid @RequestBody MovementsDTO movementsDTO,
                               Errors errors) throws GeneralException, RequestValidationException {
        Commons.validateFieldRequest(errors);
        service.createMovement(movementsDTO);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<?> getMovement(@PathVariable("accountId") UUID accountId) throws GeneralException {
        return ResponseEntity.ok()
                .body(service.getMovementByAccountId(accountId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @Valid @RequestBody MovementsDTO movementDTO,
                                    Errors errors) throws GeneralException, RequestValidationException {
        Commons.validateFieldRequest(errors);
        MovementsDTO movement = service.getMovementById(id);
        movementDTO.setMovementId(movement.getMovementId());
        return ResponseEntity.ok(service.createMovement(movementDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMovement(@PathVariable("id") UUID movementId) {
        service.deleteMovement(movementId);
    }
}