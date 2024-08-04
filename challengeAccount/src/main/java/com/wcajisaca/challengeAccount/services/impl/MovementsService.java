package com.wcajisaca.challengeAccount.services.impl;

import com.wcajisaca.challengeAccount.constants.Errors;
import com.wcajisaca.challengeAccount.dtos.AccountStatementReportDTO;
import com.wcajisaca.challengeAccount.dtos.MovementsDTO;
import com.wcajisaca.challengeAccount.entities.Account;
import com.wcajisaca.challengeAccount.entities.Movements;
import com.wcajisaca.challengeAccount.enums.TypeMovement;
import com.wcajisaca.challengeAccount.exception.GeneralException;
import com.wcajisaca.challengeAccount.mapper.Mapper;
import com.wcajisaca.challengeAccount.repositories.IAccountRepository;
import com.wcajisaca.challengeAccount.repositories.IMovementRepository;
import com.wcajisaca.challengeAccount.services.IMovementsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovementsService implements IMovementsService {
    private final IMovementRepository movRepository;
    private final IAccountRepository accRepository;

    @Override
    public List<MovementsDTO> findAll() {
        return movRepository.findAll().stream()
                .map(Mapper::toMovementsDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MovementsDTO getMovementById(UUID movementId) throws GeneralException {
        return movRepository.findById(movementId)
                .map(Mapper::toMovementsDTO)
                .orElseThrow(Errors::notFoundTransaction);
    }

    @Override
    @Transactional
    public MovementsDTO createMovement(MovementsDTO movementsDTO) throws GeneralException {
        Account account = accRepository.findById(movementsDTO.getAccountId())
                .orElseThrow(Errors::notFoundAccount);

        if(movementsDTO.getTypeMovement().equals(TypeMovement.RET)){
            if (account.getInitialBalance() < movementsDTO.getValue()) {
                throw new GeneralException(Errors.INSUFFICIENT_FUNDS);
            }
        }

        Double newBalance = movementsDTO.getTypeMovement().equals(TypeMovement.DEP) ?
                account.getInitialBalance() + movementsDTO.getValue() :
                account.getInitialBalance() - movementsDTO.getValue();

        Movements movement = Mapper.toMovements(movementsDTO);
        movement = movRepository.save(movement.withBalance(newBalance));

        accRepository.save(account.withInitialBalance(newBalance));
        return Mapper.toMovementsDTO(movement);
    }

    private Movements getLastedMovement(UUID accountId) {
        return movRepository.findTopByAccountIdOrderByMovementDateDesc(accountId)
                .orElse(null);
    }

    @Override
    public void deleteMovement(UUID movementId) {
        movRepository.deleteById(movementId);
    }

    @Override
    public AccountStatementReportDTO generateAccountStatement(UUID clientId, LocalDate startDate, LocalDate endDate) {
        List<Account> accounts = accRepository.findByPersonId(clientId);

        AccountStatementReportDTO report = new AccountStatementReportDTO();
        report.setClientId(clientId.toString());
        List<AccountStatementReportDTO.AccountDetailDTO> accountDetails = new ArrayList<>();

        for (Account account : accounts) {
            AccountStatementReportDTO.AccountDetailDTO accountDetail = new AccountStatementReportDTO.AccountDetailDTO();
            accountDetail.setAccountId(account.getAccountId());
            accountDetail.setTypeAccount(account.getTypeAccount());
            accountDetail.setCurrentBalance(account.getInitialBalance());

            List<Movements> movements = movRepository.findByAccountIdAndMovementDateBetween(
                    account.getAccountId(), startDate, endDate);

            List<AccountStatementReportDTO.MovementDetailDTO> movementDetails = new ArrayList<>();
            for (Movements movement : movements) {
                AccountStatementReportDTO.MovementDetailDTO movementDetail = new AccountStatementReportDTO.MovementDetailDTO();
                movementDetail.setMovementDate(movement.getMovementDate());
                movementDetail.setTypeMovement(movement.getTypeMovement());
                movementDetail.setValue(movement.getValue());
                movementDetail.setBalance(movement.getBalance());
                movementDetails.add(movementDetail);
            }

            accountDetail.setMovements(movementDetails);
            accountDetails.add(accountDetail);
        }

        report.setAccounts(accountDetails);
        return report;
    }
}
