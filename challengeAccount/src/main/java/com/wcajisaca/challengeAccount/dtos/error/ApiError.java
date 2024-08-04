package com.wcajisaca.challengeAccount.dtos.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Create message api error for response resource request
 * @author wcajisaca
 */
@Getter
@Setter
public class ApiError implements Serializable {
    private String error;
    public ApiError(String error) {
        this.error = error;
    }
}