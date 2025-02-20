package com.wcajisaca.accountService.services.impl;

import com.wcajisaca.accountService.dtos.AccountDTO;
import com.wcajisaca.accountService.enums.TypeAccount;
import com.wcajisaca.accountService.services.IAccountService;
import com.wcajisaca.accountService.services.IKafkaConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
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
    @KafkaListener(topics = CREATE_ACCOUNT_TOPIC_NAME, groupId = CREATE_ACCOUNT_GROUP_KAFKA, containerFactory = KAFKA_CONTAINER_FACTORY)
    public void consumeMessage(UUID personId) {
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

    private void sendErrorNotification(UUID personId) {
        log.info("Send message to kafka with error");
        try{
            kafkaTemplate.send(ERROR_ACCOUNT_TOPIC_NAME, personId);
        }catch (Exception e){
            log.error("Error sending message: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}