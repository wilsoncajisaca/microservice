package com.wcajisaca.accountService.dtos;

import com.wcajisaca.accountService.enums.TypeAccount;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

public record AccountDTO (
        UUID accountId,
        Integer accountNumber,
        @NotNull(message = "El tipo de la cuenta es requerido")
        TypeAccount typeAccount,
        Double initialBalance,
        Boolean status,
        @NotNull(message = "La persona asociada es requerida")
        UUID personId
) {
    public static AccountDTO accountWithPerson(TypeAccount typeAccount, UUID personId) {
        return new AccountDTO(null, null, typeAccount, null, Boolean.TRUE, personId);
    }
}