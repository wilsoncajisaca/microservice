package com.wcajisaca.accountService.constants;

import com.wcajisaca.accountService.dtos.error.ApiErrorList;
import com.wcajisaca.accountService.exception.RequestValidationException;
import org.springframework.validation.Errors;

import java.util.stream.Collectors;

public class Commons {
    public static void validateFieldRequest(Errors errors) throws RequestValidationException {
        if (errors.hasErrors()) {
            throw new RequestValidationException(new ApiErrorList(
                    errors.getAllErrors().stream()
                    .map(m -> m.getDefaultMessage())
                    .collect(Collectors.toList())));
        }
    }
}
