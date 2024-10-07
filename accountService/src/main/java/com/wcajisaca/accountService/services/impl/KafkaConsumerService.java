package com.wcajisaca.accountService.services.impl;

import com.wcajisaca.accountService.dtos.AccountDTO;
import com.wcajisaca.accountService.enums.TypeAccount;
import com.wcajisaca.accountService.exception.GeneralException;
import com.wcajisaca.accountService.services.IAccountService;
import com.wcajisaca.accountService.services.IKafkaConsumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.wcajisaca.accountService.constants.ChallengeAccountConstant.*;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService implements IKafkaConsumerService {
    private final IAccountService accountService;
    /**
     * {@inheritDoc}
     */
    @Override
    @KafkaListener(topics = CREATE_ACCOUNT_TOPIC_NAME, groupId = CREATE_ACCOUNT_GROUP_KAFKA, containerFactory = KAFKA_CONTAINER_FACTORY)
    public void consumeMessage(UUID personId) {
        AccountDTO accountDTO = AccountDTO
                .builder()
                .typeAccount(TypeAccount.AHO)
                .personId(personId)
                .build();
        accountService.createAccount(accountDTO);
    }
}