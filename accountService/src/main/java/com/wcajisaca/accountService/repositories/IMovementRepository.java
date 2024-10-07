package com.wcajisaca.accountService.repositories;

import com.wcajisaca.accountService.entities.Movements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface for movements repository
 */
@Repository
public interface IMovementRepository extends JpaRepository<Movements, Serializable> {
    /**
     * Find movements by account id and movement date between
     * @param accountId
     * @param startDate
     * @param endDate
     * @return
     */
    List<Movements> findByAccountIdAndMovementDateBetween(UUID accountId, LocalDate startDate, LocalDate endDate);

    /**
     * Find movements by account id
     * @param accountId
     * @return
     */
    List<Movements> findByAccountId(UUID accountId);
}
