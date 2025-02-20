package com.wcajisaca.accountService.interceptors;

import com.wcajisaca.accountService.dtos.response.BaseResponse;
import com.wcajisaca.accountService.exception.AccountException;
import com.wcajisaca.accountService.exception.AccountRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
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
    @ExceptionHandler(AccountException.class)
    public ResponseEntity<Object> handleAccountException(AccountException exc){
        log.debug("Exception for return handleGeneralException: {}", exc);
        String error = exc.getApiError().getError();
        return buildErrorResponse(HttpStatus.BAD_REQUEST, error);
    }

    /**
     * @param exc Throw Error
     * @return Final custom exception
     */
    @ExceptionHandler(AccountRuntimeException.class)
    public ResponseEntity<Object> handleAccountRuntimeException(AccountRuntimeException exc){
        log.debug("Exception for return ApiFieldError: {}", exc);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, exc.getApiError().getError());
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
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        String message = ex.getBindingResult().getAllErrors().stream()
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