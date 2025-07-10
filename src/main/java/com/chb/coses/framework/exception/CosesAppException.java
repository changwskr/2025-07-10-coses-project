package com.chb.coses.framework.exception;

/**
 * Base exception class for the EPlaton Framework
 */
public class CosesAppException extends Exception {

    private static final long serialVersionUID = 1L;

    private String errorCode;
    private String errorMessage;

    public CosesAppException() {
        super();
    }

    public CosesAppException(String message) {
        super(message);
        this.errorMessage = message;
    }

    public CosesAppException(String message, Throwable cause) {
        super(message, cause);
        this.errorMessage = message;
    }

    public CosesAppException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    public CosesAppException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    public CosesAppException(Throwable cause) {
        super(cause);
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
}