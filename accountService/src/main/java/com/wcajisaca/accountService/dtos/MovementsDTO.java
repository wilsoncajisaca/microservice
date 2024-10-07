package com.wcajisaca.accountService.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wcajisaca.accountService.enums.TypeMovement;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovementsDTO {
    private UUID movementId;
    @NotNull(message = "La fecha del movimiento es requerida")
    private LocalDate movementDate;
    @NotNull(message = "El tipo del movimiento es requerido")
    private TypeMovement typeMovement;
    private Double balance;
    @NotNull(message = "El valor del movimiento es requerido")
    private Double value;
    @NotNull(message = "La cuenta asociada es requerida")
    private UUID accountId;
}