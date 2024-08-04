package com.wcajisaca.challenge.services;

import com.wcajisaca.challenge.dto.request.ClientDTO;
import com.wcajisaca.challenge.entities.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for clients
 * @author wcajisaca
 */
public interface IClientService {
    void testKafka();
    List<ClientDTO> findAll();
    ClientDTO findById(UUID id);
    ClientDTO save(ClientDTO client);
    void deleteById(UUID id);
}
