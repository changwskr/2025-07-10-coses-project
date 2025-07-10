package com.chb.coses.cosesFramework.exception;

/**
 * Exception detail information for the Coses Framework
 */
public class CosesExceptionDetail {

    private String errorCode;
    private String errorMessage;
    private String errorLocation;
    private String stackTrace;
    private long timestamp;

    public CosesExceptionDetail() {
        this.timestamp = System.currentTimeMillis();
    }

    public CosesExceptionDetail(String errorCode, String errorMessage) {
        this();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public CosesExceptionDetail(String errorCode, String errorMessage, String errorLocation) {
        this(errorCode, errorMessage);
        this.errorLocation = errorLocation;
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

    public String getErrorLocation() {
        return errorLocation;
    }

    public void setErrorLocation(String errorLocation) {
        this.errorLocation = errorLocation;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "CosesExceptionDetail{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", errorLocation='" + errorLocation + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}