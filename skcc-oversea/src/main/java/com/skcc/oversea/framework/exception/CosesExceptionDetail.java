package com.skcc.oversea.framework.exception;

import java.io.Serializable;

/**
 * CosesExceptionDetail - Exception detail for SKCC Oversea
 * 
 * Detailed exception information class for handling
 * application-specific error details in the Spring Boot migration.
 */
public class CosesExceptionDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private String errorCode;
    private String errorMessage;
    private String errorDetail;
    private String stackTrace;

    public CosesExceptionDetail() {
    }

    public CosesExceptionDetail(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public CosesExceptionDetail(String errorCode, String errorMessage, String errorDetail) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorDetail = errorDetail;
    }

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

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    // Additional methods for compatibility
    public void addMessage(String key, String value) {
        if (errorDetail == null) {
            errorDetail = "";
        }
        errorDetail += key + "=" + value + ";";
    }

    public void addArgument(String argument) {
        if (errorDetail == null) {
            errorDetail = "";
        }
        errorDetail += "arg=" + argument + ";";
    }

    @Override
    public String toString() {
        return "CosesExceptionDetail{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", errorDetail='" + errorDetail + '\'' +
                '}';
    }
}