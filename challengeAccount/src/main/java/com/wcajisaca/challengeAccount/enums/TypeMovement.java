package com.wcajisaca.challengeAccount.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum for type movement
 * @author wilson
 */
@Getter
@RequiredArgsConstructor
public enum TypeMovement {
    DEP("DEPOSITO"),
    RET("RETIRO");

    private final String displayName;
}
