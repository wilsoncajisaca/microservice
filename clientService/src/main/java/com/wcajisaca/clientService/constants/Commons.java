package com.wcajisaca.clientService.constants;

import com.wcajisaca.clientService.dto.error.ApiErrorList;
import com.wcajisaca.clientService.exception.RequestValidationException;
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
