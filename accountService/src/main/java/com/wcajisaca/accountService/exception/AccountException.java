package com.wcajisaca.accountService.exception;

import com.wcajisaca.accountService.dtos.error.ApiError;
import lombok.Getter;

@Getter
public class AccountException extends Exception {
    private static final long serialVersionUID = 1L;
    private ApiError apiError;

    public AccountException(ApiError apiError){
        this.apiError = apiError;
    }
}