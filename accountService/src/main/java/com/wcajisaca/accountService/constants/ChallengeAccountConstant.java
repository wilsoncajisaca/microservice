package com.wcajisaca.accountService.constants;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Class for constants
 * @author wilson
 */
public class ChallengeAccountConstant {
    public static final String CREATE_ACCOUNT_TOPIC_NAME = "topic_create_account";
    public static final String CREATE_ACCOUNT_GROUP_KAFKA = "group_account_movements";
    public static final String KAFKA_CONTAINER_FACTORY = "jsonContainerFactory";
    public static final Double INITIAL_BALANCE = 0.0;
    public static final Integer RANDOM_ACCOUNT_NUMBER = ThreadLocalRandom.current().nextInt(100000, 999999 + 1);
}