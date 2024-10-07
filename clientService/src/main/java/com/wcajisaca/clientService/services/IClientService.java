package com.wcajisaca.clientService.services;

import com.wcajisaca.clientService.dto.request.ClientDTO;

import java.util.List;
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
