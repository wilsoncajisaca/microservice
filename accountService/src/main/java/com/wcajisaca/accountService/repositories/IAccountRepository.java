package com.wcajisaca.accountService.repositories;

import com.wcajisaca.accountService.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Interface for account repository
 * @author wcajisaca
 */
@Repository
public interface IAccountRepository extends JpaRepository<Account, Serializable> {
    /**
     * Find account by person id
     * @param personId
     * @return
     */
    List<Account> findByPersonId(UUID personId);
}