package com.banking.framework.transfer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * Common DTO for the Coses Framework (Spring Boot Version)
 * 
 * Provides standardized data transfer objects for all framework operations.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CosesCommonDTO {

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("sessionId")
    private String sessionId;

    @JsonProperty("transactionId")
    private String transactionId;

    @JsonProperty("requestTime")
    private LocalDateTime requestTime;

    @JsonProperty("responseTime")
    private LocalDateTime responseTime;

    @JsonProperty("status")
    private String status;

    @JsonProperty("errorCode")
    private String errorCode;

    @JsonProperty("errorMessage")
    private String errorMessage;

    @JsonProperty("language")
    private String language;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("version")
    private String version;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("details")
    private String details;

    @JsonProperty("source")
    private String source;

    // Constructors
    public CosesCommonDTO() {
        this.timestamp = LocalDateTime.now();
        this.requestTime = LocalDateTime.now();
        this.status = "PENDING";
        this.language = "ko";
        this.timezone = "Asia/Seoul";
        this.version = "2.0.0";
    }

    public CosesCommonDTO(String userId, String sessionId, String transactionId) {
        this();
        this.userId = userId;
        this.sessionId = sessionId;
        this.transactionId = transactionId;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }

    public LocalDateTime getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(LocalDateTime responseTime) {
        this.responseTime = responseTime;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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
    public void markSuccess() {
        this.status = "SUCCESS";
        this.responseTime = LocalDateTime.now();
    }

    public void addEventData(String key, Object value) {
        // This method is for compatibility with the framework
        // In a real implementation, you might want to store this in a separate map
        // For now, we'll just ignore the data
    }

    public void markError(String errorCode, String errorMessage) {
        this.status = "ERROR";
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.responseTime = LocalDateTime.now();
    }

    public boolean isSuccess() {
        return "SUCCESS".equals(this.status);
    }

    public boolean isError() {
        return "ERROR".equals(this.status);
    }

    @Override
    public String toString() {
        return "CosesCommonDTO{" +
                "userId='" + userId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", status='" + status + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}