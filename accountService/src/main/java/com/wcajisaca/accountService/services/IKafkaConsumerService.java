package com.wcajisaca.accountService.services;

import com.wcajisaca.accountService.exception.AccountException;

import java.util.UUID;

/**
 * Interface for consuming messages from KafkaConsumerService
 * @author wilson
 */
public interface IKafkaConsumerService {
    /**
     * Consume message
     * @param personId
     */
    void consumeMessage(UUID personId) throws AccountException;
}
