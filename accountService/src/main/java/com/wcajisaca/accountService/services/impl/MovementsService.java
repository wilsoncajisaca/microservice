package com.wcajisaca.accountService.services.impl;

import com.wcajisaca.accountService.constants.Errors;
import com.wcajisaca.accountService.dtos.AccountStatementReportDTO;
import com.wcajisaca.accountService.dtos.MovementsDTO;
import com.wcajisaca.accountService.entities.Account;
import com.wcajisaca.accountService.entities.Movements;
import com.wcajisaca.accountService.enums.TypeMovement;
import com.wcajisaca.accountService.exception.GeneralException;
import com.wcajisaca.accountService.exception.GeneralRunException;
import com.wcajisaca.accountService.mapper.MovementMapper;
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
    private final IMovementRepository moveRepository;
    private final IAccountRepository accountRepository;
    private final MovementMapper mapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MovementsDTO> findAll() {
        log.info("Find all movements");
        return moveRepository.findAll().stream()
                .map(mapper::toMovementsDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MovementsDTO> getMovementByAccountId(UUID accountId) {
        log.info("Find movements by account id: {}", accountId);
        return moveRepository.findByAccountId(accountId).stream()
                .map(mapper::toMovementsDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MovementsDTO getMovementById(UUID movementId) throws GeneralException {
        log.info("Find movement by id: {}", movementId);
        return moveRepository.findById(movementId)
                .map(mapper::toMovementsDTO)
                .orElseThrow(Errors::notFoundTransaction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public MovementsDTO createMovement(MovementsDTO movementsDTO) throws GeneralException {
        log.info("Create movement");
        Account account = Optional
                .ofNullable(movementsDTO.accountId())
                .flatMap(accountRepository::findById)
                .orElseThrow((Errors::notFoundAccount));

        Double newBalance = Optional.of(movementsDTO)
                .filter(dto -> dto.typeMovement().equals(TypeMovement.RET))
                .map(dto -> calculateBalanceForRet(dto, account))
                .orElseGet(() -> calculateBalanceForDep(movementsDTO, account));

        Movements movement = Optional.of(movementsDTO)
                .map(mapper::toMovements)
                .map(mov -> mov.withBalance(newBalance))
                .map(moveRepository::save)
                .orElseThrow(Errors::errorRegisteringTransaction);

        accountRepository.save(account.withInitialBalance(newBalance));
        return mapper.toMovementsDTO(movement);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteMovement(UUID movementId) {
        log.info("Delete movement by id: {}", movementId);
        moveRepository.findById(movementId)
                .ifPresent(moveRepository::delete);
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

        accountRepository.findByPersonId(clientId).forEach(account -> {
            AccountStatementReportDTO.AccountDetailDTO accountDetail = new AccountStatementReportDTO.AccountDetailDTO();
            accountDetail.setAccountId(account.getAccountId());
            accountDetail.setTypeAccount(account.getTypeAccount());
            accountDetail.setCurrentBalance(account.getInitialBalance());

            List<AccountStatementReportDTO.MovementDetailDTO> movementDetails = new ArrayList<>();
            moveRepository.findByAccountIdAndMovementDateBetween(
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

    /**
     * Calcula el nuevo balance.
     * @param movement
     * @param account
     * @return
     */
    private Double calculateBalanceForRet(MovementsDTO movement, Account account) {
        validatedInputBalance(movement);
        if (account.getInitialBalance() < movement.value()) {
            throw new GeneralRunException(Errors.INSUFFICIENT_FUNDS);
        }
        return account.getInitialBalance() - movement.value();
    }

    private double calculateBalanceForDep(MovementsDTO movement, Account account) {
        validatedInputBalance(movement);
        return account.getInitialBalance() + movement.value();
    }

    private void validatedInputBalance(MovementsDTO movement){
        if(movement.value() <= 0 ) {
            throw new GeneralRunException(Errors.INVALID_VALUE);
        }
    }
}
