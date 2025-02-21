package com.wcajisaca.clientService.helper;

import com.wcajisaca.clientService.constants.Errors;
import com.wcajisaca.clientService.dto.request.ClientDTO;
import com.wcajisaca.clientService.entities.Client;
import com.wcajisaca.clientService.mapper.ClientMapper;
import com.wcajisaca.clientService.repositories.IClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

import static com.wcajisaca.clientService.constants.ClientConstants.CREATE_ACCOUNT_TOPIC_NAME;
import static com.wcajisaca.clientService.constants.ClientConstants.DELETE_CLIENT_TOPIC_NAME;
import static com.wcajisaca.clientService.util.ClientUtils.encryptPassword;

@Component
@Slf4j
@RequiredArgsConstructor
public class ClientHelper {
    private final IClientRepository repository;
    private final ClientMapper mapper;
    private final KafkaTemplate<String, Serializable> kafkaTemplate;

    /**
     * Verifico si ya existe un cliente con la identificacion
     * @param clientDto
     */
    public void verifyExistClient(ClientDTO clientDto) {
        Optional.ofNullable(clientDto.identification())
                .filter(id -> repository.existsByIdentificationOrClientId(id, clientDto.clientId()))
                .ifPresent(id -> { throw Errors.duplicatedIdentification(); });
    }

    /**
     * Encriptacion de la contraseña
     * @param clientDto
     * @return
     */
    public Client clientWhitPassEncrypt(ClientDTO clientDto) {
        ClientDTO clientWithEncryptedPassword = clientDto.withPassword(encryptPassword(clientDto.password()));
        return mapper.toEntity(clientWithEncryptedPassword);
    }

    /**
     * Envio el mensaje Kafka solo si es un cliente nuevo
     * @param clientDto
     * @param savedClient
     */
    public void sendAccountCreatedByNewClient(ClientDTO clientDto, Client savedClient) {
        if (BooleanUtils.isTrue(clientDto.isNewClient())) {
            sendKafkaCreateAccount(savedClient);
        }
    }

    @Retryable(value = KafkaException.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public void sendKafkaCreateAccount(Client savedClient){
        log.info("Send message to Kafka");
        try {
            kafkaTemplate.send(CREATE_ACCOUNT_TOPIC_NAME, savedClient.getPersonId());
        } catch (Exception e) {
            log.error("Error sending message: {}", e.getMessage());
            throw new KafkaException("Failed to send Kafka message", e);
        }
    }

    @Retryable(value = KafkaException.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public void sendKafkaDeleteClient(UUID personId){
        log.info("Enviando notificacion de error");
        try {
            kafkaTemplate.send(DELETE_CLIENT_TOPIC_NAME, personId);
        } catch (Exception e) {
            log.error("Error enviando notificacion: {}", e.getMessage());
            throw new KafkaException("Fallo al enviar notificacion", e);
        }
    }
}
