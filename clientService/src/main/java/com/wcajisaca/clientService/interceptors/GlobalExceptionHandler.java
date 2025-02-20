package com.wcajisaca.clientService.interceptors;

import com.wcajisaca.clientService.dto.error.ApiError;
import com.wcajisaca.clientService.dto.error.ApiErrorList;
import com.wcajisaca.clientService.dto.response.BaseResponse;
import com.wcajisaca.clientService.exception.ClientRuntimeException;
import com.wcajisaca.clientService.exception.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

/**
 * Global exception handler to log exceptions
 * @author wcajisaca
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * @param exc Throw Error
     * @return Final custom exception
     */
    @ExceptionHandler(ClientException.class)
    public ResponseEntity<Object> handleClientException(ClientException exc){
        log.debug("Exception for return ApiFieldError: {}", exc);
        String message = exc.getApiErrorList().getErrors()
                .stream()
                .collect(Collectors.joining(", "));
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    /**
     * @param exc Throw Error
     * @return Final custom exception
     */
    @ExceptionHandler(ClientRuntimeException.class)
    public ResponseEntity<Object> handleGeneralException(ClientRuntimeException exc){
        log.debug("Exception for return handleGeneralException: {}", exc);
        String message = exc.getApiError().getError();
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    /**
     * @param exc Throw Error
     * @return Final custom exception
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(ClientRuntimeException exc){
        log.debug("Exception for return handleGeneralException: {}", exc);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exc.getMessage());
    }

    /**
     * @param exc Throw Error
     * @return Final custom exception
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception exc){
        log.error("Exception for return handleGlobalException: {}", exc);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exc.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exc, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        String message = exc.getBindingResult().getAllErrors().stream()
                .map(e -> e.getDefaultMessage().concat(". "))
                .collect(Collectors.joining());
        log.error(message);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, message);
    }

    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, String message) {
        BaseResponse response = BaseResponse.builder()
                .code(status.value())
                .message(message)
                .build();
        return new ResponseEntity<>(response, status);
    }
}
