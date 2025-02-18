package com.wcajisaca.clientService.services.impl;

import com.wcajisaca.clientService.constants.Errors;
import com.wcajisaca.clientService.dto.request.ClientDTO;
import com.wcajisaca.clientService.entities.Client;
import com.wcajisaca.clientService.mapper.ClientMapper;
import com.wcajisaca.clientService.repositories.IClienteRepository;
import com.wcajisaca.clientService.services.IClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.wcajisaca.clientService.constants.ChallengeConstants.CREATE_ACCOUNT_TOPIC_NAME;
import static com.wcajisaca.clientService.util.ClientUtils.encryptPassword;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService implements IClientService {
    private final IClienteRepository repository;
    private final ClientMapper mapper;
    private final KafkaTemplate<String, Serializable> kafkaTemplate;

    @Override
    public void testKafka() {
        kafkaTemplate.send(CREATE_ACCOUNT_TOPIC_NAME, UUID.randomUUID());
    }

    @Override
    public List<ClientDTO> findAll() {
        log.info("Find all clients");
        return repository.findAllByStatus(Boolean.TRUE).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDTO findById(UUID id) {
        log.info("Find client by id: {}", id);
        return repository.findById(id).map(mapper::toDTO)
                .orElseThrow(Errors::notFoundClient);
    }

    @Override
    public ClientDTO save(ClientDTO clientDto) {
        log.info("Create client");
        verifyExistClient(clientDto);
        Client client = toClientWithEncryptedPassword(clientDto);
        Client savedClient = repository.save(client);
        // Envio el mensaje Kafka solo si es un cliente nuevo
        sendAccountCreatedByNewClient(clientDto, savedClient);
        return mapper.toDTO(savedClient);
    }

    @Override
    public void deleteLogicById(UUID id) {
        log.info("Delete client by id: {}", id);
        Client client = this.repository.findById(id)
                .orElseThrow(Errors::notFoundClient);
        client.setStatus(Boolean.FALSE);
        repository.save(client);
    }

    @Override
    public void deleteBydId(UUID id) {
        log.info("Delete client by id: {}", id);
        this.repository.deleteById(id);
    }

    /**
     * Verifico si ya existe un cliente con la identificacion
     * @param clientDto
     */
    private void verifyExistClient(ClientDTO clientDto) {
        Optional.ofNullable(clientDto.identification())
                .filter(id -> repository.existsByIdentificationOrClientId(id, clientDto.clientId()))
                .ifPresent(id -> { throw Errors.duplicatedIdentification(); });
    }

    /**
     * Encriptacion de la contraseña
     * @param clientDto
     * @return
     */
    private Client toClientWithEncryptedPassword(ClientDTO clientDto) {
        ClientDTO clientWithEncryptedPassword = clientDto.withPassword(encryptPassword(clientDto.password()));
        return mapper.toEntity(clientWithEncryptedPassword);
    }

    /**
     * Envio el mensaje Kafka solo si es un cliente nuevo
     * @param clientDto
     * @param savedClient
     */
    private void sendAccountCreatedByNewClient(ClientDTO clientDto, Client savedClient) {
        if (BooleanUtils.isTrue(clientDto.isNewClient())) {
            sendKafkaCreateAccount(savedClient);
        }
    }

    /**
     * Envio el mensaje Kafka
     * @param savedClient
     */
    private void sendKafkaCreateAccount(Client savedClient){
        log.info("Send message to kafka");
        try{
            kafkaTemplate.send(CREATE_ACCOUNT_TOPIC_NAME, savedClient.getPersonId());
        }catch (Exception e){
            log.error("Error sending message: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
