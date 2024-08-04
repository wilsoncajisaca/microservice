package com.wcajisaca.challenge.dto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

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
