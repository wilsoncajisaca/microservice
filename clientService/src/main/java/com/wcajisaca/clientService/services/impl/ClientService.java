package com.wcajisaca.clientService.services.impl;

import com.wcajisaca.clientService.constants.Errors;
import com.wcajisaca.clientService.dto.request.ClientDTO;
import com.wcajisaca.clientService.entities.Client;
import com.wcajisaca.clientService.helper.ClientHelper;
import com.wcajisaca.clientService.mapper.ClientMapper;
import com.wcajisaca.clientService.repositories.IClientRepository;
import com.wcajisaca.clientService.services.IClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService implements IClientService {
    private final IClientRepository repository;
    private final ClientMapper mapper;
    private final ClientHelper helper;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<ClientDTO> findAll() {
        log.info("Find all clients");
        return repository.findAllByStatus(Boolean.TRUE).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public ClientDTO findById(UUID id) {
        log.info("Find client by id: {}", id);
        return repository.findById(id).map(mapper::toDTO)
                .orElseThrow(Errors::notFoundClient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ClientDTO save(ClientDTO clientDto) {
        log.info("Create client");
        helper.verifyExistClient(clientDto);
        Client client = helper.clientWhitPassEncrypt(clientDto);
        Client savedClient = repository.save(client);
        helper.sendAccountCreatedByNewClient(clientDto, savedClient);
        return mapper.toDTO(savedClient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteLogicById(UUID id) {
        log.info("Delete client by id: {}", id);
        Client client = this.repository.findById(id)
                .orElseThrow(Errors::notFoundClient);
        client.setStatus(Boolean.FALSE);
        repository.save(client);
        helper.sendKafkaDeleteClient(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteBydId(UUID id) {
        log.info("Delete client by id: {}", id);
        repository.deleteById(id);
    }
}
