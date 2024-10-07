package com.wcajisaca.accountService.dtos.error;

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
public class ApiErrorList implements Serializable {
    private List<String> errors;

    public ApiErrorList(List<String> messages) {
        this.errors = messages;
    }
}
