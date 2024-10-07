package com.wcajisaca.accountService.dtos.error;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Create message api error for response resource request
 * @author wcajisaca
 */
@Getter
@Setter
@NoArgsConstructor
public class ApiError implements Serializable {
    private String error;
    public ApiError(String error) {
        this.error = error;
    }
}