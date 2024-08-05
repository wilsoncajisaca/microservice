package com.wcajisaca.challengeAccount.repositories;

import com.wcajisaca.challengeAccount.entities.Movements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    Optional<Movements> findTopByAccountIdOrderByMovementDateDesc(UUID accountId);
    List<Movements> findByAccountIdAndMovementDateBetween(UUID accountId, LocalDate startDate, LocalDate endDate);
    List<Movements> findByAccountId(UUID accountId);
}
