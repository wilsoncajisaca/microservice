package com.wcajisaca.accountService.services;

import com.wcajisaca.accountService.exception.AccountException;

import java.util.UUID;

/**
 * Interface for consuming messages from KafkaConsumerService
 * @author wilson
 */
public interface IKafkaConsumerService {
    /**
     * Consume message by create account
     * @param personId
     */
    void consumerNewAccount(UUID personId) throws AccountException;
    void consumerDeleteAccount(UUID personId) throws AccountException;
}
