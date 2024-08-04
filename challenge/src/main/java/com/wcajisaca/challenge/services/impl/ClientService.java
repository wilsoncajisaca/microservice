package com.wcajisaca.challenge.services.impl;

import com.wcajisaca.challenge.constants.Errors;
import com.wcajisaca.challenge.dto.request.ClientDTO;
import com.wcajisaca.challenge.entities.Client;
import com.wcajisaca.challenge.mapper.Mapper;
import com.wcajisaca.challenge.repositories.IClienteRepository;
import com.wcajisaca.challenge.services.IClientService;
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

import static com.wcajisaca.challenge.constants.ChallengeConstants.CREATE_ACCOUNT_TOPIC_NAME;

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
        return repository.findAll().stream()
                .map(Mapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDTO findById(UUID id) {
        return repository.findById(id).map(Mapper::convertToDTO)
                .orElseThrow(Errors::notFoundClient);
    }

    @Override
    public ClientDTO save(ClientDTO clientDto) {
        this.verifyExistClient(clientDto);
        Client client = Mapper
                .convertToClient(clientDto.withPassword(encryptPassword(clientDto.getPassword())));
        Client savedClient = repository.save(client);
        sendKafkaCreateAccount(savedClient);
        return Mapper.convertToDTO(savedClient);
    }

    @Override
    public void deleteById(UUID id) {
        this.repository.findById(id)
                .orElseThrow(Errors::notFoundClient);
        repository.deleteById(id);
    }

    private void verifyExistClient(ClientDTO clientDto) {
        Optional.of(clientDto.getIdentification())
                .filter(id -> repository.existsByIdentificationOrClientId(clientDto.getIdentification(), clientDto.getClientId()))
                .ifPresent(id -> {
                    throw Errors.duplicatedIdentification();
                });
    }

    private void sendKafkaCreateAccount(Client savedClient){
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
