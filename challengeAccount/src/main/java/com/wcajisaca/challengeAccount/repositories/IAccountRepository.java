package com.wcajisaca.challengeAccount.repositories;

import com.wcajisaca.challengeAccount.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Serializable> {
    List<Account> findByPersonId(UUID personId);
}