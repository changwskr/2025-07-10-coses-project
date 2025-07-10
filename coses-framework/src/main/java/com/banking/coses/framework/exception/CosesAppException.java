package com.banking.coses.framework.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Base exception class for the COSES Framework
 * 
 * Provides common exception handling with error codes, messages,
 * and additional context information.
 */
public class CosesAppException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    @JsonProperty("errorCode")
    private String errorCode;

    @JsonProperty("errorMessage")
    private String errorMessage;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("source")
    private String source;

    @JsonProperty("details")
    private String details;

    public CosesAppException() {
        super();
        this.timestamp = LocalDateTime.now();
    }

    public CosesAppException(String message) {
        super(message);
        this.errorMessage = message;
        this.timestamp = LocalDateTime.now();
    }

    public CosesAppException(String message, Throwable cause) {
        super(message, cause);
        this.errorMessage = message;
        this.timestamp = LocalDateTime.now();
    }

    public CosesAppException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.timestamp = LocalDateTime.now();
    }

    public CosesAppException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.timestamp = LocalDateTime.now();
    }

    public CosesAppException(String errorCode, String message, String source) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.source = source;
        this.timestamp = LocalDateTime.now();
    }

    public CosesAppException(String errorCode, String message, String source, String details) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.source = source;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }

    public CosesAppException(Throwable cause) {
        super(cause);
        this.timestamp = LocalDateTime.now();
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * Get formatted timestamp string
     */
    public String getFormattedTimestamp() {
        if (timestamp != null) {
            return timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
        return null;
    }

    /**
     * Get full error information
     */
    public String getFullErrorInfo() {
        StringBuilder sb = new StringBuilder();
        if (errorCode != null) {
            sb.append("Error Code: ").append(errorCode).append(", ");
        }
        if (errorMessage != null) {
            sb.append("Message: ").append(errorMessage).append(", ");
        }
        if (source != null) {
            sb.append("Source: ").append(source).append(", ");
        }
        if (timestamp != null) {
            sb.append("Timestamp: ").append(getFormattedTimestamp());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "CosesAppException{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", timestamp=" + timestamp +
                ", source='" + source + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}