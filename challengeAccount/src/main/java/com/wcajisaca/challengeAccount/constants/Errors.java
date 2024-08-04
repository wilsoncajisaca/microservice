package com.wcajisaca.challengeAccount.constants;

import com.wcajisaca.challengeAccount.dtos.error.ApiError;
import com.wcajisaca.challengeAccount.exception.GeneralException;

public class Errors {
    public static final String INSUFFICIENT_FUNDS = "Fondos insuficientes";

    /**
     * Generate error
     *
     * @return new Exception
     */
    public static GeneralException notFoundAccount() {
        return new GeneralException(new ApiError("Cuenta no encontrada"));
    }

    /**
     * Generate error
     *
     * @return new Exception
     */
    public static GeneralException notFoundTransaction() {
        return new GeneralException(new ApiError("Movimiento no encontrado"));
    }
}