package com.wcajisaca.accountService.dtos;

import com.wcajisaca.accountService.enums.TypeAccount;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AccountDTO {
    @With
    private UUID accountId;
    private Integer accountNumber;
    @NotNull(message = "El tipo de la cuenta es requerido")
    private TypeAccount typeAccount;
    private Double initialBalance;
    private Boolean status;
    @NotNull(message = "La persona asociada es requerida")
    private UUID personId;
}