package com.gv.user_service.exception;

/**
 * Custom exception class thrown when transfer amount is higher than account balance of sender.
 */
public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException() {
        super();
    }

    public InsufficientFundsException(String message) {
        super(message);
    }

    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }
}
