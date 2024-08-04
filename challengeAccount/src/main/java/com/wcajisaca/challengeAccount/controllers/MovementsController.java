package com.wcajisaca.challengeAccount.controllers;

import com.wcajisaca.challengeAccount.constants.Commons;
import com.wcajisaca.challengeAccount.dtos.MovementsDTO;
import com.wcajisaca.challengeAccount.exception.GeneralException;
import com.wcajisaca.challengeAccount.exception.RequestValidationException;
import com.wcajisaca.challengeAccount.services.IMovementsService;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovement(@PathVariable UUID movementId) throws GeneralException {
        return ResponseEntity.ok()
                .body(service.getMovementById(movementId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody MovementsDTO movementDTO,
                                    Errors errors) throws GeneralException, RequestValidationException {
        Commons.validateFieldRequest(errors);
        MovementsDTO movement = service.getMovementById(id);
        movementDTO.setMovementId(movement.getMovementId());
        return ResponseEntity.ok(service.createMovement(movementDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMovement(@PathVariable UUID movementId) {
        service.deleteMovement(movementId);
    }
}