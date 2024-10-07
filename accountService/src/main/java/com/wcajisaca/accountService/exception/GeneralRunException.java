package com.wcajisaca.accountService.exception;

import com.wcajisaca.accountService.dtos.error.ApiError;
import lombok.Getter;

@Getter
public class GeneralRunException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private ApiError apiError;

    public GeneralRunException(ApiError apiError){
        this.apiError = apiError;
    }

    public GeneralRunException(String error){
        this.apiError = new ApiError(error);
    }
}
