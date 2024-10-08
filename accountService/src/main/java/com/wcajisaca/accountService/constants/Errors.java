package com.wcajisaca.accountService.constants;

import com.wcajisaca.accountService.dtos.error.ApiError;
import com.wcajisaca.accountService.exception.GeneralException;
import com.wcajisaca.accountService.exception.GeneralRunException;

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

    public static GeneralRunException errorRegisteringTransaction() {
        return new GeneralRunException(new ApiError("No se pudo registrar el movimiento"));
    }

}