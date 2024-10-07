package com.wcajisaca.clientService.services.impl;

import com.wcajisaca.clientService.constants.Errors;
import com.wcajisaca.clientService.dto.request.ClientDTO;
import com.wcajisaca.clientService.entities.Client;
import com.wcajisaca.clientService.mapper.Mapper;
import com.wcajisaca.clientService.repositories.IClienteRepository;
import com.wcajisaca.clientService.services.IClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.wcajisaca.clientService.constants.ChallengeConstants.CREATE_ACCOUNT_TOPIC_NAME;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService implements IClientService {
    private final IClienteRepository repository;
    private final KafkaTemplate<String, Serializable> kafkaTemplate;

    @Override
    public void testKafka() {
        kafkaTemplate.send(CREATE_ACCOUNT_TOPIC_NAME, UUID.randomUUID());
    }

    @Override
    public List<ClientDTO> findAll() {
        log.info("Find all clients");
        return repository.findAllByStatus(Boolean.TRUE).stream()
                .map(Mapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDTO findById(UUID id) {
        log.info("Find client by id: {}", id);
        return repository.findById(id).map(Mapper::convertToDTO)
                .orElseThrow(Errors::notFoundClient);
    }

    @Override
    public ClientDTO save(ClientDTO clientDto) {
        log.info("Create client");
        this.verifyExistClient(clientDto);
        Client client = Mapper
                .convertToClient(clientDto.withPassword(encryptPassword(clientDto.getPassword())));
        Client savedClient = repository.save(client);
        if(clientDto.getIsNewClient()){
            sendKafkaCreateAccount(savedClient);
        }
        return Mapper.convertToDTO(savedClient);
    }

    @Override
    public void deleteById(UUID id) {
        log.info("Delete client by id: {}", id);
         Client client = this.repository.findById(id)
                .orElseThrow(Errors::notFoundClient);
        client.setStatus(false);
        repository.save(client);
    }

    private void verifyExistClient(ClientDTO clientDto) {
        Optional.of(clientDto.getIdentification())
                .filter(id -> repository.existsByIdentificationOrClientId(clientDto.getIdentification(), clientDto.getClientId()))
                .ifPresent(id -> {
                    throw Errors.duplicatedIdentification();
                });
    }

    /**
     * Send message to kafka
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

    public static String encryptPassword(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }
}
