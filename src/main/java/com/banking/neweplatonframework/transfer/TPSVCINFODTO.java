package com.banking.neweplatonframework.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/**
 * TPSVCINFO DTO for New EPlaton Framework
 * 
 * Contains TPSVC (Transaction Processing Service) information.
 * This replaces the legacy TPSVCINFODTO with Spring Boot features.
 */
public class TPSVCINFODTO {

    @JsonProperty("systemName")
    private String systemName;

    @JsonProperty("actionName")
    private String actionName;

    @JsonProperty("operationName")
    private String operationName;

    @JsonProperty("errorCode")
    private String errorCode;

    @JsonProperty("errorMessage")
    private String errorMessage;

    @JsonProperty("status")
    private String status;

    @JsonProperty("requestTime")
    private LocalDateTime requestTime;

    @JsonProperty("responseTime")
    private LocalDateTime responseTime;

    @JsonProperty("transactionId")
    private String transactionId;

    @JsonProperty("sessionId")
    private String sessionId;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("channelType")
    private String channelType;

    @JsonProperty("terminalId")
    private String terminalId;

    @JsonProperty("bankCode")
    private String bankCode;

    @JsonProperty("branchCode")
    private String branchCode;

    @JsonProperty("priority")
    private String priority;

    @JsonProperty("timeout")
    private int timeout;

    @JsonProperty("retryCount")
    private int retryCount;

    @JsonProperty("maxRetries")
    private int maxRetries;

    public TPSVCINFODTO() {
        this.requestTime = LocalDateTime.now();
        this.status = "INIT";
        this.priority = "NORMAL";
        this.timeout = 30000; // 30 seconds
        this.maxRetries = 3;
    }

    // Getters and Setters
    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    /**
     * Set error information
     */
    public void setError(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.status = "ERROR";
        this.responseTime = LocalDateTime.now();
    }

    /**
     * Set success information
     */
    public void setSuccess() {
        this.errorCode = "IZZ000";
        this.errorMessage = "Success";
        this.status = "SUCCESS";
        this.responseTime = LocalDateTime.now();
    }

    /**
     * Check if operation was successful
     */
    public boolean isSuccess() {
        return "SUCCESS".equals(status) ||
                (errorCode != null && errorCode.startsWith("I"));
    }

    /**
     * Check if operation has error
     */
    public boolean hasError() {
        return "ERROR".equals(status) ||
                (errorCode != null && !errorCode.startsWith("I"));
    }

    /**
     * Increment retry count
     */
    public void incrementRetryCount() {
        this.retryCount++;
    }

    /**
     * Check if max retries exceeded
     */
    public boolean isMaxRetriesExceeded() {
        return retryCount >= maxRetries;
    }

    /**
     * Get processing duration in milliseconds
     */
    public long getProcessingDuration() {
        if (requestTime != null && responseTime != null) {
            return java.time.Duration.between(requestTime, responseTime).toMillis();
        }
        return 0;
    }

    @Override
    public String toString() {
        return "TPSVCINFODTO{" +
                "systemName='" + systemName + '\'' +
                ", actionName='" + actionName + '\'' +
                ", operationName='" + operationName + '\'' +
                ", status='" + status + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", transactionId='" + transactionId + '\'' +
                '}';
    }
}