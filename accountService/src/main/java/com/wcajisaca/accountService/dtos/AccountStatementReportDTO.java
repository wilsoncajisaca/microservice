package com.wcajisaca.accountService.dtos;

import com.wcajisaca.accountService.enums.TypeAccount;
import com.wcajisaca.accountService.enums.TypeMovement;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AccountStatementReportDTO {
    private String clientId;
    private List<AccountDetailDTO> accounts;
    @Data
    public static class AccountDetailDTO {
        private UUID accountId;
        private TypeAccount typeAccount;
        private Double currentBalance;
        private List<MovementDetailDTO> movements;
    }

    @Data
    public static class MovementDetailDTO {
        private LocalDate movementDate;
        private TypeMovement typeMovement;
        private Double value;
        private Double balance;
    }
}