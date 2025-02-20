package com.wcajisaca.accountService.constants;

import com.wcajisaca.accountService.dtos.error.ApiError;
import com.wcajisaca.accountService.dtos.error.ApiErrorList;
import com.wcajisaca.accountService.exception.AccountException;
import com.wcajisaca.accountService.exception.AccountRuntimeException;

public class Errors {
    public static final String INSUFFICIENT_FUNDS = "Fondos insuficientes";

    public static final String INVALID_VALUE = "Valor digitado no valido.";

    /**
     * Generate error
     *
     * @return new Exception
     */
    public static AccountException notFoundAccount() {
        return new AccountException(new ApiError("Cuenta no encontrada"));
    }

    /**
     * Generate error
     *
     * @return new Exception
     */
    public static AccountException notFoundTransaction() {
        return new AccountException(new ApiError("Movimiento no encontrado"));
    }

    public static AccountRuntimeException errorRegisteringTransaction() {
        return new AccountRuntimeException("No se pudo registrar el movimiento");
    }

}