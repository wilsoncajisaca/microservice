package com.wcajisaca.clientService.exception;

import com.wcajisaca.clientService.dto.error.ApiError;
import lombok.Getter;

@Getter
public class ClientRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private ApiError apiError;

    public ClientRuntimeException(String error){
        this.apiError = new ApiError(error);
    }
}