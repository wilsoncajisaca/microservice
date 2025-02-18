package com.wcajisaca.accountService.interceptors;

import com.wcajisaca.accountService.dtos.error.ApiError;
import com.wcajisaca.accountService.dtos.error.ApiErrorList;
import com.wcajisaca.accountService.exception.GeneralException;
import com.wcajisaca.accountService.exception.GeneralRunException;
import com.wcajisaca.accountService.exception.RequestValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global exception handler to log exceptions
 * @author wcajisaca
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * @param requestValidationException Throw Error
     * @param webRequest   Request
     * @return Final custom exception
     */
    @ExceptionHandler(RequestValidationException.class)
    public ResponseEntity<Object> handleRequestValidationException(
            RequestValidationException requestValidationException, WebRequest webRequest){
        log.debug("Exception for return ApiFieldError: {}", requestValidationException);
        ApiErrorList error = requestValidationException.getApiErrorList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * @param requestValidationException Throw Error
     * @param webRequest   Request
     * @return Final custom exception
     */
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<Object> handleGeneralException(GeneralException requestValidationException, WebRequest webRequest){
        log.debug("Exception for return handleGeneralException: {}", requestValidationException);
        ApiError error = requestValidationException.getApiError();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    /**
     * @param requestValidationException Throw Error
     * @param webRequest   Request
     * @return Final custom exception
     */
    @ExceptionHandler(GeneralRunException.class)
    public ResponseEntity<Object> handleGeneralException(GeneralRunException requestValidationException, WebRequest webRequest){
        log.debug("Exception for return handleGeneralException: {}", requestValidationException);
        ApiError error = requestValidationException.getApiError();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * @param exception Throw Error
     * @param webRequest   Request
     * @return Final custom exception
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception exception, WebRequest webRequest){
        ApiError error = new ApiError();
        error.setError(exception.getMessage());
        log.error("Exception for return handleGlobalException: {}", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}