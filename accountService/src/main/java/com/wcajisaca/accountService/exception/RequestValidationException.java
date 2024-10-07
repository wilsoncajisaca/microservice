package com.wcajisaca.accountService.exception;

import com.wcajisaca.accountService.dtos.error.ApiErrorList;
import lombok.Getter;

@Getter
public class RequestValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private ApiErrorList apiErrorList;

    public RequestValidationException(ApiErrorList listError){
        this.apiErrorList = listError;
    }
}
