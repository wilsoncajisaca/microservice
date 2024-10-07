package com.wcajisaca.accountService.mapper;

import com.wcajisaca.accountService.dtos.AccountDTO;
import com.wcajisaca.accountService.dtos.MovementsDTO;
import com.wcajisaca.accountService.entities.Account;
import com.wcajisaca.accountService.entities.Movements;

/**
 * Mapper class
 * @author wilson
 */
public class Mapper {

    public static MovementsDTO toMovementsDTO(Movements movements) {
        return MovementsDTO.builder()
                .movementId(movements.getMovementId())
                .movementDate(movements.getMovementDate())
                .typeMovement(movements.getTypeMovement())
                .balance(movements.getBalance())
                .value(movements.getValue())
                .accountId(movements.getAccountId())
                .build();
    }

    public static Movements toMovements(MovementsDTO movementsDTO) {
        return Movements.builder()
                .movementId(movementsDTO.getMovementId())
                .movementDate(movementsDTO.getMovementDate())
                .typeMovement(movementsDTO.getTypeMovement())
                .balance(movementsDTO.getBalance())
                .value(movementsDTO.getValue())
                .accountId(movementsDTO.getAccountId())
                .build();
    }

    public static AccountDTO toAccountDTO(Account account) {
        return AccountDTO.builder()
                .accountId(account.getAccountId())
                .accountNumber(account.getAccountNumber())
                .typeAccount(account.getTypeAccount())
                .initialBalance(account.getInitialBalance())
                .status(account.getStatus())
                .personId(account.getPersonId())
                .build();
    }

    public static Account toAccount(AccountDTO accountDTO) {
        return Account.builder()
                .accountId(accountDTO.getAccountId())
                .accountNumber(accountDTO.getAccountNumber())
                .typeAccount(accountDTO.getTypeAccount())
                .initialBalance(accountDTO.getInitialBalance())
                .personId(accountDTO.getPersonId())
                .build();
    }
}