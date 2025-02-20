package com.wcajisaca.accountService.exception;

import com.wcajisaca.accountService.dtos.error.ApiError;
import lombok.Getter;

@Getter
public class AccountRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private ApiError apiError;

    public AccountRuntimeException(ApiError error){
        this.apiError = error;
    }

    public AccountRuntimeException(String error){
        this.apiError = new ApiError(error);
    }
}
