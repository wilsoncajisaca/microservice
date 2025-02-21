package com.wcajisaca.clientService.repositories;

import com.wcajisaca.clientService.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface IClientRepository extends JpaRepository<Client, Serializable> {
    Boolean existsByIdentificationOrClientId(String ident, String clientId);
    List<Client> findAllByStatus(Boolean status);
}