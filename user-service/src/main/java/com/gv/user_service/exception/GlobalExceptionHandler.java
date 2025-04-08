package com.gv.user_service.exception;

import com.gv.user_service.dto.response.APIResponse;
import com.gv.user_service.util.Constants;
import com.gv.user_service.util.ResponseUtils;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private String getRequestPath() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getRequestURI();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<APIResponse> handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException) {
        log.error("[GlobalExceptionHandler] >> [handleValidationException]: {}", methodArgumentNotValidException.getMessage());
        List<String> errors = methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> "Invalid value: '" + error.getRejectedValue() + "' for field: '" + error.getField() + "'. " + error.getDefaultMessage())
                .toList();
        APIResponse apiResponse = ResponseUtils.createApiResponse(false, Constants.STATUS_CODE_FAILURE, String.join(", ", errors),
                null, null);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<APIResponse> handleCustomerNotFoundException(CustomerNotFoundException customerNotFoundException) {
        log.error("[GlobalExceptionHandler] >> [handleCustomerNotFoundException]: {}", customerNotFoundException.getMessage());
        APIResponse apiResponse = ResponseUtils.createApiResponse(false, Constants.STATUS_CODE_FAILURE, customerNotFoundException.getMessage(),
                null, null);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<APIResponse> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        log.error("[GlobalExceptionHandler] >> [handleResourceNotFoundException]: {}", resourceNotFoundException.getMessage());
        APIResponse apiResponse = ResponseUtils.createApiResponse(false, Constants.STATUS_CODE_FAILURE, resourceNotFoundException.getMessage(),
                null, null);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<APIResponse> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        log.error("[GlobalExceptionHandler] >> [handleIllegalArgumentException]: {}", illegalArgumentException.getMessage());
        APIResponse apiResponse = ResponseUtils.createApiResponse(false, Constants.STATUS_CODE_FAILURE, illegalArgumentException.getMessage(),
                null, null);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<APIResponse> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {
        log.error("[GlobalExceptionHandler] >> [handleMethodNotAllowedException]: {}", httpRequestMethodNotSupportedException.getMessage());
        APIResponse apiResponse = ResponseUtils.createApiResponse(false, Constants.STATUS_CODE_FAILURE, httpRequestMethodNotSupportedException.getMessage(),
                null, null);
        return new ResponseEntity<>(apiResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<APIResponse> handleInvalidJsonException(HttpMessageNotReadableException httpMessageNotReadableException) {
        log.error("[GlobalExceptionHandler] >> [handleInvalidJsonException]: {}", httpMessageNotReadableException.getMessage());
        APIResponse apiResponse = ResponseUtils.createApiResponse(false, Constants.STATUS_CODE_FAILURE, httpMessageNotReadableException.getMessage(),
                null, null);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<APIResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
        log.error("[GlobalExceptionHandler] >> [handleTypeMismatchException]: {}", methodArgumentTypeMismatchException.getMessage());
        APIResponse apiResponse = ResponseUtils.createApiResponse(false, Constants.STATUS_CODE_FAILURE, methodArgumentTypeMismatchException.getMessage(),
                null, null);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles AuthenticationException.
     *
     * @param ex
     * @param request
     * @return ResponseEntity<Object> with detailed information related to the error
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        log.error("[GlobalExceptionHandler] >> [handleAuthenticationException]: {}", ex.getMessage());
        APIResponse apiResponse = ResponseUtils.createApiResponse(false, Constants.STATUS_CODE_FAILURE, ex.getMessage(),
                null, null);
        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles custom InsufficientFundsException.
     *
     * @param ex
     * @param request
     * @return ResponseEntity<Object> with detailed information related to the error
     */
    @ExceptionHandler(InsufficientFundsException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ResponseEntity<Object> handleInsufficientFundsException(InsufficientFundsException ex, WebRequest request) {
        log.error("[GlobalExceptionHandler] >> [handleInsufficientFundsException]: {}", ex.getMessage());
        APIResponse apiResponse = ResponseUtils.createApiResponse(false, Constants.STATUS_CODE_FAILURE, ex.getMessage(),
                null, null);
        return new ResponseEntity<>(apiResponse, HttpStatus.PRECONDITION_FAILED);
    }

    /**
     * Handles ConstraintViolationException during field validation.
     *
     * @param ex
     * @param request
     * @return ResponseEntity<Object> with detailed information related to the error
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintValidationException(ConstraintViolationException ex, WebRequest request) {
        log.warn("[GlobalExceptionHandler] >> [handleConstraintValidationException]: {}", ex.getMessage());
        APIResponse apiResponse = ResponseUtils.createApiResponse(false, Constants.STATUS_CODE_FAILURE, ex.getMessage(),
                null, null);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse> handleGlobalException(Exception exception) {
        log.error("[GlobalExceptionHandler] >> [handleGlobalException]: {}", exception.getMessage());
        APIResponse apiResponse = ResponseUtils.createApiResponse(false, Constants.STATUS_CODE_FAILURE, exception.getMessage(),
                null, null);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
