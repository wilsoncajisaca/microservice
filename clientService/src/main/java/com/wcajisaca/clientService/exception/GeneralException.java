package com.wcajisaca.clientService.exception;

import com.wcajisaca.clientService.dto.error.ApiError;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private ApiError apiError;

    public GeneralException(ApiError apiError){
        this.apiError = apiError;
    }

    public GeneralException(String error){
        this.apiError = new ApiError(error);
    }
}
