package com.wcajisaca.clientService.services;

import com.wcajisaca.clientService.dto.request.ClientDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service for clients
 * @author wcajisaca
 */
public interface IClientService {
    /**
     * Find all clients
     * @return
     */
    List<ClientDTO> findAll();

    /**
     * Find client by id
     * @param id
     * @return
     */
    ClientDTO findById(UUID id);

    /**
     * Save client
     * @param client
     * @return
     */
    ClientDTO save(ClientDTO client);

    /**
     * Delete Logic client
     * @param id
     */
    void deleteLogicById(UUID id);

    /**
     * Delete client
     * @param id
     */
    void deleteBydId(UUID id);
}
