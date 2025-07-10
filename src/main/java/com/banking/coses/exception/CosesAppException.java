package com.banking.coses.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Base exception class for the COSES Framework
 * 
 * Provides standardized exception handling for COSES operations.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CosesAppException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String errorCode;
    private String errorMessage;
    private HttpStatus httpStatus;

    public CosesAppException() {
        super();
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public CosesAppException(String message) {
        super(message);
        this.errorMessage = message;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public CosesAppException(String message, Throwable cause) {
        super(message, cause);
        this.errorMessage = message;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public CosesAppException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public CosesAppException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public CosesAppException(String errorCode, String message, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.httpStatus = httpStatus;
    }

    public CosesAppException(String errorCode, String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.httpStatus = httpStatus;
    }

    public CosesAppException(Throwable cause) {
        super(cause);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    // Static factory methods for common exceptions
    public static CosesAppException invalidInput(String message) {
        return new CosesAppException("EINV001", message, HttpStatus.BAD_REQUEST);
    }

    public static CosesAppException notFound(String message) {
        return new CosesAppException("ENOT001", message, HttpStatus.NOT_FOUND);
    }

    public static CosesAppException unauthorized(String message) {
        return new CosesAppException("EAUTH001", message, HttpStatus.UNAUTHORIZED);
    }

    public static CosesAppException forbidden(String message) {
        return new CosesAppException("EFORB001", message, HttpStatus.FORBIDDEN);
    }

    public static CosesAppException validationError(String message) {
        return new CosesAppException("EVAL001", message, HttpStatus.BAD_REQUEST);
    }

    public static CosesAppException businessError(String errorCode, String message) {
        return new CosesAppException(errorCode, message, HttpStatus.BAD_REQUEST);
    }

    public static CosesAppException systemError(String message) {
        return new CosesAppException("ESYS001", message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static CosesAppException systemError(String message, Throwable cause) {
        return new CosesAppException("ESYS001", message, HttpStatus.INTERNAL_SERVER_ERROR, cause);
    }

    // Getters and Setters
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return "CosesAppException{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", httpStatus=" + httpStatus +
                '}';
    }
}