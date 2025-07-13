package com.skcc.oversea.framework.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.skcc.oversea.foundation.constant.Constants;

import java.time.LocalDateTime;

/**
 * Base DTO class for SKCC Oversea Framework
 * 
 * Provides common fields and functionality for all DTOs.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseDTO {

    private String transactionId;
    private String transactionType;

    @JsonFormat(pattern = Constants.DATETIME_FORMAT_YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime timestamp;

    private String status;
    private String errorCode;
    private String errorMessage;

    // Default constructor
    public BaseDTO() {
        this.timestamp = LocalDateTime.now();
        this.status = Constants.TXN_STATUS_INITIATED;
    }

    // Constructor with transaction info
    public BaseDTO(String transactionId, String transactionType) {
        this();
        this.transactionId = transactionId;
        this.transactionType = transactionType;
    }

    // Getters and Setters
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    /**
     * Check if transaction was successful
     */
    public boolean isSuccess() {
        return Constants.ERROR_CODE_SUCCESS.equals(errorCode) ||
                Constants.TXN_STATUS_SUCCESS.equals(status);
    }

    /**
     * Set success status
     */
    public void setSuccess() {
        this.status = Constants.TXN_STATUS_SUCCESS;
        this.errorCode = Constants.ERROR_CODE_SUCCESS;
        this.errorMessage = "Transaction completed successfully";
    }

    /**
     * Set error status
     */
    public void setError(String errorCode, String errorMessage) {
        this.status = Constants.TXN_STATUS_FAILED;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "BaseDTO{" +
                "transactionId='" + transactionId + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", timestamp=" + timestamp +
                ", status='" + status + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}