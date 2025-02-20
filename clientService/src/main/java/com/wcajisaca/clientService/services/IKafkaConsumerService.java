package com.wcajisaca.clientService.services;

import com.wcajisaca.clientService.exception.ClientRuntimeException;

import java.util.UUID;

public interface IKafkaConsumerService {
    /**
     * Consume message
     * @param personId
     */
    void consumeMessageError(UUID personId) throws ClientRuntimeException;
}
