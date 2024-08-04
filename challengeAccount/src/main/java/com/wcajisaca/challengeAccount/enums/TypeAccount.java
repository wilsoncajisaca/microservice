package com.wcajisaca.challengeAccount.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum for type account
 * @author wilson
 */
@Getter
@RequiredArgsConstructor
public enum TypeAccount {
    AHO("AHORRO"),
    CORR("CORRIENTE");

    private final String displayName;
}