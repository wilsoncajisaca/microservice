package com.wcajisaca.clientService.constants;

import com.wcajisaca.clientService.exception.ClientRuntimeException;

public class Errors {

    /**
     * Generate error
     *
     * @return new Exception
     */
    public static ClientRuntimeException notFoundClient() {
        return new ClientRuntimeException("Cliente no encontrada");
    }

    /**
     * Generate error
     *
     * @return new Exception
     */
    public static ClientRuntimeException notFoundTransaction() {
        return new ClientRuntimeException("Movimiento no encontrado");
    }
    /**
     * Generate error for identification
     *
     * @return new Exception
     */
    public static ClientRuntimeException duplicatedIdentification() {
        return new ClientRuntimeException("Ya existe un cliente con esa identificación o clienteId");
    }
}