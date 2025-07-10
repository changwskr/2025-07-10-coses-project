package com.banking.neweplatonframework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * New EPlaton Exception
 * 
 * Base exception class for New EPlaton Framework operations.
 * This replaces the legacy exceptions with Spring Boot features.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class NewEPlatonException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String errorCode;
    private String errorMessage;
    private HttpStatus httpStatus;

    public NewEPlatonException() {
        super();
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public NewEPlatonException(String message) {
        super(message);
        this.errorMessage = message;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public NewEPlatonException(String message, Throwable cause) {
        super(message, cause);
        this.errorMessage = message;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public NewEPlatonException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public NewEPlatonException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public NewEPlatonException(String errorCode, String message, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.httpStatus = httpStatus;
    }

    public NewEPlatonException(String errorCode, String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.httpStatus = httpStatus;
    }

    public NewEPlatonException(Throwable cause) {
        super(cause);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    // Static factory methods for common exceptions
    public static NewEPlatonException invalidInput(String message) {
        return new NewEPlatonException("EINV001", message, HttpStatus.BAD_REQUEST);
    }

    public static NewEPlatonException invalidEvent(String message) {
        return new NewEPlatonException("EEVT001", message, HttpStatus.BAD_REQUEST);
    }

    public static NewEPlatonException notFound(String message) {
        return new NewEPlatonException("ENOT001", message, HttpStatus.NOT_FOUND);
    }

    public static NewEPlatonException unauthorized(String message) {
        return new NewEPlatonException("EAUTH001", message, HttpStatus.UNAUTHORIZED);
    }

    public static NewEPlatonException forbidden(String message) {
        return new NewEPlatonException("EFORB001", message, HttpStatus.FORBIDDEN);
    }

    public static NewEPlatonException validationError(String message) {
        return new NewEPlatonException("EVAL001", message, HttpStatus.BAD_REQUEST);
    }

    public static NewEPlatonException businessError(String errorCode, String message) {
        return new NewEPlatonException(errorCode, message, HttpStatus.BAD_REQUEST);
    }

    public static NewEPlatonException systemError(String message) {
        return new NewEPlatonException("ESYS001", message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static NewEPlatonException systemError(String message, Throwable cause) {
        return new NewEPlatonException("ESYS001", message, HttpStatus.INTERNAL_SERVER_ERROR, cause);
    }

    public static NewEPlatonException timeoutError(String message) {
        return new NewEPlatonException("ETIM001", message, HttpStatus.REQUEST_TIMEOUT);
    }

    public static NewEPlatonException serviceUnavailable(String message) {
        return new NewEPlatonException("ESVC001", message, HttpStatus.SERVICE_UNAVAILABLE);
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
        return "NewEPlatonException{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", httpStatus=" + httpStatus +
                '}';
    }
}