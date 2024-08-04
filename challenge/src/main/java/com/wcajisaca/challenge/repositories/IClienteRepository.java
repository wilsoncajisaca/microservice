package com.wcajisaca.challenge.repositories;

import com.wcajisaca.challenge.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface IClienteRepository extends JpaRepository<Client, Serializable> {
    Boolean existsByIdentificationOrClientId(String ident, String clientId);
}