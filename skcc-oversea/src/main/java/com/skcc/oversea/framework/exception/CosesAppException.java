package com.skcc.oversea.framework.exception;

/**
 * CosesAppException - Application exception for SKCC Oversea
 * 
 * Custom exception class for handling application-specific errors
 * in the Spring Boot migration.
 */
public class CosesAppException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String errorCode;
    private String errorMessage;

    public CosesAppException(String message) {
        super(message);
        this.errorMessage = message;
    }

    public CosesAppException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    public CosesAppException(String message, Throwable cause) {
        super(message, cause);
        this.errorMessage = message;
    }

    public CosesAppException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}