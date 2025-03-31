package com.gv.user_service.exception;

import com.gv.user_service.dto.response.APIResponse;
import com.gv.user_service.util.ResponseUtils;
import com.gv.user_service.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<APIResponse> handleCustomerNotFoundException(CustomerNotFoundException customerNotFoundException){
        log.info("[GlobalExceptionHandler] >> [handleCustomerNotFoundException]: {}",customerNotFoundException.getMessage());
        APIResponse apiResponse = ResponseUtils.createApiResponse(false, Constants.STATUS_CODE_FAILURE, customerNotFoundException.getMessage(),
                null,null);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<APIResponse> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException){
        log.info("[GlobalExceptionHandler] >> [handleResourceNotFoundException]: {}",resourceNotFoundException.getMessage());
        APIResponse apiResponse = ResponseUtils.createApiResponse(false, Constants.STATUS_CODE_FAILURE, resourceNotFoundException.getMessage(),
                null,null);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<APIResponse> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException){
        log.info("[GlobalExceptionHandler] >> [handleIllegalArgumentException]: {}",illegalArgumentException.getMessage());
        APIResponse apiResponse = ResponseUtils.createApiResponse(false, Constants.STATUS_CODE_FAILURE, illegalArgumentException.getMessage(),
                null,null);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse> handleGlobalException(Exception exception){
        log.info("[GlobalExceptionHandler] >> [handleGlobalException]: {}",exception.getMessage());
        APIResponse apiResponse = ResponseUtils.createApiResponse(false, Constants.STATUS_CODE_FAILURE, exception.getMessage(),
                null,null);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
