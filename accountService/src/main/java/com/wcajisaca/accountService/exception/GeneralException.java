package com.wcajisaca.accountService.exception;

import com.wcajisaca.accountService.dtos.error.ApiError;
import lombok.Getter;

@Getter
public class GeneralException extends Exception {
    private static final long serialVersionUID = 1L;
    private ApiError apiError;

    public GeneralException(ApiError apiError){
        this.apiError = apiError;
    }

    public GeneralException(String error){
        this.apiError = new ApiError(error);
    }
}