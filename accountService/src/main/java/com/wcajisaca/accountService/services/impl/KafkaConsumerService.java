package com.wcajisaca.accountService.services.impl;

import com.wcajisaca.accountService.dtos.AccountDTO;
import com.wcajisaca.accountService.enums.TypeAccount;
import com.wcajisaca.accountService.exception.AccountException;
import com.wcajisaca.accountService.services.IAccountService;
import com.wcajisaca.accountService.services.IKafkaConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import static com.wcajisaca.accountService.constants.ChallengeAccountConstant.*;
import static com.wcajisaca.accountService.dtos.AccountDTO.accountWithPerson;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService implements IKafkaConsumerService {
    private final IAccountService accountService;
    private final ExecutorService executorService;
    private final KafkaTemplate<String, Serializable> kafkaTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    @KafkaListener(topics = CREATE_ACCOUNT_TOPIC_NAME,
            groupId = CREATE_ACCOUNT_GROUP_KAFKA,
            containerFactory = KAFKA_CONTAINER_FACTORY)
    public void consumerNewAccount(UUID personId) {
        executorService.execute(() -> {
            try {
                AccountDTO accountDTO = accountWithPerson(TypeAccount.AHO, personId);
                accountService.createAccount(accountDTO);
            } catch (Exception e) {
                log.error("Error al procesar el mensaje para personId: {}", personId, e);
                sendErrorNotification(personId);
            }
        });
    }

    @Override
    @KafkaListener(topics = DELETE_CLIENT_TOPIC_NAME,
            groupId = DELETE_CLIENT_GROUP_NAME,
            containerFactory = KAFKA_CONTAINER_FACTORY)
    public void consumerDeleteAccount(UUID personId) {
        executorService.execute(() -> {
            try {
                accountService.deleteAccountByClient(personId);
            } catch (Exception e) {
                log.error("Error al procesar el mensaje para personId: {}", personId, e);
                sendErrorNotification(personId);
            }
        });
    }

    @Retryable(value = KafkaException.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public void sendErrorNotification(UUID personId){
        log.info("Enviando notificacion de error");
        try {
            kafkaTemplate.send(ERROR_ACCOUNT_TOPIC_NAME, personId);
        } catch (Exception e) {
            log.error("Error enviando notificacion: {}", e.getMessage());
            throw new KafkaException("Fallo al enviar notificacion", e);
        }
    }
}