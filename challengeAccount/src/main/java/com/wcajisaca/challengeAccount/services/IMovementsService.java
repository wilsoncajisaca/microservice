package com.wcajisaca.challengeAccount.services;

import com.wcajisaca.challengeAccount.dtos.AccountStatementReportDTO;
import com.wcajisaca.challengeAccount.dtos.MovementsDTO;
import com.wcajisaca.challengeAccount.exception.GeneralException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface for movements service
 * @author wilson
 */
public interface IMovementsService {
    List<MovementsDTO> findAll();
    MovementsDTO createMovement(MovementsDTO movementsDTO) throws GeneralException;
    MovementsDTO getMovementById(UUID movementId) throws GeneralException;
    void deleteMovement(UUID movementId);
    AccountStatementReportDTO generateAccountStatement(UUID clientId, LocalDate startDate, LocalDate endDate);
}
