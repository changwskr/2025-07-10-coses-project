package com.banking.framework.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Base exception class for the Coses Framework (Spring Boot Version)
 * 
 * Provides standardized exception handling with error codes and HTTP status
 * mapping.
 */
public class CosesAppException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String errorCode;
    private String errorMessage;
    private HttpStatus httpStatus;
    private LocalDateTime timestamp;
    private String details;
    private String source;

    // Constructors
    public CosesAppException() {
        super();
        this.timestamp = LocalDateTime.now();
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public CosesAppException(String message) {
        super(message);
        this.errorMessage = message;
        this.timestamp = LocalDateTime.now();
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public CosesAppException(String message, Throwable cause) {
        super(message, cause);
        this.errorMessage = message;
        this.timestamp = LocalDateTime.now();
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public CosesAppException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.timestamp = LocalDateTime.now();
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public CosesAppException(String errorCode, String message, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.httpStatus = httpStatus;
        this.timestamp = LocalDateTime.now();
    }

    public CosesAppException(String errorCode, String message, String details) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.details = details;
        this.timestamp = LocalDateTime.now();
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public CosesAppException(String errorCode, String message, String details, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.details = details;
        this.httpStatus = httpStatus;
        this.timestamp = LocalDateTime.now();
    }

    public CosesAppException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.timestamp = LocalDateTime.now();
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public CosesAppException(Throwable cause) {
        super(cause);
        this.timestamp = LocalDateTime.now();
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    // Helper methods
    public boolean hasErrorCode() {
        return errorCode != null && !errorCode.trim().isEmpty();
    }

    public boolean hasDetails() {
        return details != null && !details.trim().isEmpty();
    }

    public boolean isClientError() {
        return httpStatus != null && httpStatus.is4xxClientError();
    }

    public boolean isServerError() {
        return httpStatus != null && httpStatus.is5xxServerError();
    }

    @Override
    public String toString() {
        return "CosesAppException{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", httpStatus=" + httpStatus +
                ", timestamp=" + timestamp +
                ", details='" + details + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}