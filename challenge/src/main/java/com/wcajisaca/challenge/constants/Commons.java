package com.wcajisaca.challenge.constants;

import com.wcajisaca.challenge.dto.error.ApiErrorList;
import com.wcajisaca.challenge.exception.RequestValidationException;
import org.springframework.validation.Errors;

import java.util.stream.Collectors;

public class Commons {
    public static void validateFieldRequest(Errors errors) throws RequestValidationException {
        if (errors.hasErrors()) {
            throw new RequestValidationException(new ApiErrorList(errors.getAllErrors()
                    .stream()
                    .map(m -> m.getDefaultMessage())
                    .collect(Collectors.toList())));
        }
    }
}
