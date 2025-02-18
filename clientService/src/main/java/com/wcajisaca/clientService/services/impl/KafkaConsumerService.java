package com.wcajisaca.clientService.services.impl;

import com.wcajisaca.clientService.exception.GeneralException;
import com.wcajisaca.clientService.services.IClientService;
import com.wcajisaca.clientService.services.IKafkaConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutorService;

import static com.wcajisaca.clientService.constants.ChallengeConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService implements IKafkaConsumerService {
    private final ExecutorService executorService;
    private final IClientService clientService;
    /**
     * {@inheritDoc}
     */
    @Override
    @KafkaListener(topics = ERROR_ACCOUNT_TOPIC_NAME, groupId = ERROR_ACCOUNT_GROUP_KAFKA, containerFactory = KAFKA_CONTAINER_FACTORY)
    public void consumeMessageError(UUID personId) {
        executorService.execute(() -> {
            try {
                clientService.deleteBydId(personId);
                log.error("La cuenta para la persona con id: {} no pudo ser creada", personId);
            } catch (Exception e) {
                log.error("Error al procesar el mensaje para personId: {}", personId, e);
            }
        });
    }
}
