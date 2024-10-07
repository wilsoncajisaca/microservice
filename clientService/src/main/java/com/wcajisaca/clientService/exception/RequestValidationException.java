package com.wcajisaca.clientService.exception;

import com.wcajisaca.clientService.dto.error.ApiErrorList;
import lombok.Getter;

@Getter
public class RequestValidationException extends Exception {
    private static final long serialVersionUID = 1L;
    private ApiErrorList apiErrorList;

    public RequestValidationException(ApiErrorList listError){
        this.apiErrorList = listError;
    }
}
