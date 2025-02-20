package com.wcajisaca.accountService.services;

import com.wcajisaca.accountService.dtos.AccountStatementReportDTO;
import com.wcajisaca.accountService.dtos.MovementsDTO;
import com.wcajisaca.accountService.exception.AccountException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Interface for movements service
 * @author wcajisaca
 */
public interface IMovementsService {
    /**
     * Find all movements
     * @return
     */
    List<MovementsDTO> findAll();

    /**
     * Create movement
     * @param movementsDTO
     * @return
     * @throws AccountException
     */
    MovementsDTO createMovement(MovementsDTO movementsDTO) throws AccountException;

    /**
     * Get movements by account
     * @param accountId
     * @return
     * @throws AccountException
     */
    List<MovementsDTO> getMovementByAccountId(UUID accountId) throws AccountException;

    /**
     * Get movement by id
     * @param movementId
     * @return
     * @throws AccountException
     */
    MovementsDTO getMovementById(UUID movementId) throws AccountException;

    /**
     * Delete movement
     * @param movementId
     */
    void deleteMovement(UUID movementId);

    /**
     * Generate account statement
     * @param clientId
     * @param startDate
     * @param endDate
     * @return
     */
    AccountStatementReportDTO generateAccountStatement(UUID clientId, LocalDate startDate, LocalDate endDate);
}
