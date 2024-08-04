package com.wcajisaca.challenge.constants;

import com.wcajisaca.challenge.dto.error.ApiError;
import com.wcajisaca.challenge.exception.GeneralException;

public class Errors {

    /**
     * Generate error
     *
     * @return new Exception
     */
    public static GeneralException notFoundClient() {
        return new GeneralException("Cliente no encontrada");
    }

    /**
     * Generate error
     *
     * @return new Exception
     */
    public static GeneralException notFoundTransaction() {
        return new GeneralException("Movimiento no encontrado");
    }
    /**
     * Generate error for identification
     *
     * @return new Exception
     */
    public static GeneralException duplicatedIdentification() {
        return new GeneralException("Ya existe un cliente con esa identificación o clienteId");
    }
}