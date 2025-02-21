package com.wcajisaca.accountService.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wcajisaca.accountService.enums.TypeMovement;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MovementsDTO (
        @With
        UUID movementId,
        @NotNull(message = "La fecha del movimiento es requerida")
        LocalDate movementDate,
        @NotNull(message = "El tipo del movimiento es requerido")
        TypeMovement typeMovement,
        Double balance,
        @NotNull(message = "El valor del movimiento es requerido")
        @Positive(message = "El valor debe ser positivo")
        Double value,
        @NotNull(message = "La cuenta asociada es requerida")
        UUID accountId
) {}