package com.wcajisaca.accountService.constants;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Class for constants
 * @author wilson
 */
public class ChallengeAccountConstant {
    //CONSTANTS FOR KAFKA NEW ACCOUNT
    public static final String CREATE_ACCOUNT_TOPIC_NAME = "topic_create_account";
    public static final String CREATE_ACCOUNT_GROUP_KAFKA = "group_create_account";
    public static final String KAFKA_CONTAINER_FACTORY = "jsonContainerFactory";
    //CONSTANTS FOR KAFKA NEW ACCOUNT WITH ERROR
    public static final String ERROR_ACCOUNT_TOPIC_NAME = "topic_error_account";
    //CONSTANTS FOR KAFKA DELETE CLIENT
    public static final String DELETE_CLIENT_TOPIC_NAME = "topic_delete_client";
    public static final String DELETE_CLIENT_GROUP_NAME = "group_delete_client";

    public static final Double INITIAL_BALANCE = 0.0;
}