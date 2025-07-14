package com.skcc.oversea.eplatonframework.business.dto;

import java.time.LocalDateTime;

/**
 * Service Response DTO for SKCC Oversea
 * 
 * Standardized response format for all business services.
 */
public class ServiceResponse<T> {

    private String status;
    private String errorCode;
    private String errorMessage;
    private T data;
    private LocalDateTime timestamp;
    private String serviceName;

    /**
     * Default constructor
     */
    public ServiceResponse() {
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Success response constructor
     */
    public static <T> ServiceResponse<T> success(T data, String serviceName) {
        ServiceResponse<T> response = new ServiceResponse<>();
        response.setStatus("SUCCESS");
        response.setErrorCode("I0000");
        response.setErrorMessage("Operation completed successfully");
        response.setData(data);
        response.setServiceName(serviceName);
        return response;
    }

    /**
     * Error response constructor
     */
    public static <T> ServiceResponse<T> error(String errorCode, String errorMessage, String serviceName) {
        ServiceResponse<T> response = new ServiceResponse<>();
        response.setStatus("ERROR");
        response.setErrorCode(errorCode);
        response.setErrorMessage(errorMessage);
        response.setServiceName(serviceName);
        return response;
    }

    // Getters and Setters
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * Check if response is successful
     */
    public boolean isSuccess() {
        return "SUCCESS".equals(status);
    }

    /**
     * Check if response has error
     */
    public boolean hasError() {
        return "ERROR".equals(status);
    }

    /**
     * Set success status
     */
    public void setSuccess(boolean success) {
        this.status = success ? "SUCCESS" : "ERROR";
    }

    /**
     * Set message (alias for setErrorMessage)
     */
    public void setMessage(String message) {
        this.errorMessage = message;
    }

    /**
     * Get message (alias for getErrorMessage)
     */
    public String getMessage() {
        return this.errorMessage;
    }
}