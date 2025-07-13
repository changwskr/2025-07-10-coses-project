package com.skcc.oversea.eplatonframework.business.exception;

/**
 * Business Exception for SKCC Oversea
 * 
 * Represents business logic related exceptions.
 */
public class BusinessException extends RuntimeException {

    private final String errorCode;
    private final String errorMessage;

    /**
     * Constructor with error code and message
     */
    public BusinessException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * Constructor with error code, message and cause
     */
    public BusinessException(String errorCode, String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * Get error code
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Get error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}