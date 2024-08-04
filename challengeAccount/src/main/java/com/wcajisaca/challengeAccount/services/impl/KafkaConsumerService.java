package com.wcajisaca.challengeAccount.services.impl;

import com.wcajisaca.challengeAccount.dtos.AccountDTO;
import com.wcajisaca.challengeAccount.enums.TypeAccount;
import com.wcajisaca.challengeAccount.services.IAccountService;
import com.wcajisaca.challengeAccount.services.IKafkaConsumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static com.wcajisaca.challengeAccount.constants.ChallengeAccountConstant.*;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService implements IKafkaConsumerService {
    private final IAccountService accountService;
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
