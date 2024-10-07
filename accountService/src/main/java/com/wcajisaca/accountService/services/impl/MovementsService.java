package com.wcajisaca.accountService.services.impl;

import com.wcajisaca.accountService.constants.Errors;
import com.wcajisaca.accountService.dtos.AccountStatementReportDTO;
import com.wcajisaca.accountService.dtos.MovementsDTO;
import com.wcajisaca.accountService.entities.Account;
import com.wcajisaca.accountService.entities.Movements;
import com.wcajisaca.accountService.enums.TypeMovement;
import com.wcajisaca.accountService.exception.GeneralException;
import com.wcajisaca.accountService.exception.GeneralRunException;
import com.wcajisaca.accountService.mapper.Mapper;
import com.wcajisaca.accountService.repositories.IAccountRepository;
import com.wcajisaca.accountService.repositories.IMovementRepository;
import com.wcajisaca.accountService.services.IMovementsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovementsService implements IMovementsService {
    private final IMovementRepository movRepository;
    private final IAccountRepository accRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MovementsDTO> findAll() {
        log.info("Find all movements");
        return movRepository.findAll().stream()
                .map(Mapper::toMovementsDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MovementsDTO> getMovementByAccountId(UUID accountId) {
        log.info("Find movements by account id: {}", accountId);
        return movRepository.findByAccountId(accountId).stream()
                .map(Mapper::toMovementsDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MovementsDTO getMovementById(UUID movementId) throws GeneralException {
        log.info("Find movement by id: {}", movementId);
        return movRepository.findById(movementId)
                .map(Mapper::toMovementsDTO)
                .orElseThrow(Errors::notFoundTransaction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public MovementsDTO createMovement(MovementsDTO movementsDTO) throws GeneralException {
        log.info("Create movement");
        Account account = Optional.ofNullable(movementsDTO.getAccountId())
                .flatMap(accRepository::findById)
                .orElseThrow((Errors::notFoundAccount));

        Double newBalance = Optional.of(movementsDTO)
                .filter(dto -> dto.getTypeMovement().equals(TypeMovement.RET))
                .map(dto -> {
                    if (account.getInitialBalance() < dto.getValue()) {
                        throw new GeneralRunException(Errors.INSUFFICIENT_FUNDS);
                    }
                    return account.getInitialBalance() - dto.getValue();
                })
                .orElseGet(() -> account.getInitialBalance() + movementsDTO.getValue());

        Movements movement = Optional.of(movementsDTO)
                .map(Mapper::toMovements)
                .map(mov -> mov.withBalance(newBalance))
                .map(movRepository::save)
                .orElseThrow(Errors::errorRegisteringTransaction);

        accRepository.save(account.withInitialBalance(newBalance));
        return Mapper.toMovementsDTO(movement);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteMovement(UUID movementId) {
        log.info("Delete movement by id: {}", movementId);
        movRepository.findById(movementId)
                .ifPresent(movRepository::delete);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountStatementReportDTO generateAccountStatement(UUID clientId, LocalDate startDate, LocalDate endDate) {
        log.info("Generate account statement for client id: {} between {} and {}", clientId, startDate, endDate);
        AccountStatementReportDTO report = new AccountStatementReportDTO();
        report.setClientId(clientId.toString());
        List<AccountStatementReportDTO.AccountDetailDTO> accountDetails = new ArrayList<>();

        accRepository.findByPersonId(clientId).forEach(account -> {
            AccountStatementReportDTO.AccountDetailDTO accountDetail = new AccountStatementReportDTO.AccountDetailDTO();
            accountDetail.setAccountId(account.getAccountId());
            accountDetail.setTypeAccount(account.getTypeAccount());
            accountDetail.setCurrentBalance(account.getInitialBalance());

            List<AccountStatementReportDTO.MovementDetailDTO> movementDetails = new ArrayList<>();
            movRepository.findByAccountIdAndMovementDateBetween(
                            account.getAccountId(), startDate, endDate)
                    .parallelStream()
                    .forEach(movement -> {
                        AccountStatementReportDTO.MovementDetailDTO movementDetail = new AccountStatementReportDTO.MovementDetailDTO();
                        movementDetail.setMovementDate(movement.getMovementDate());
                        movementDetail.setTypeMovement(movement.getTypeMovement());
                        movementDetail.setValue(movement.getValue());
                        movementDetail.setBalance(movement.getBalance());
                        movementDetails.add(movementDetail);
                    });

            accountDetail.setMovements(movementDetails);
            accountDetails.add(accountDetail);
        });

        report.setAccounts(accountDetails);
        return report;
    }
}
