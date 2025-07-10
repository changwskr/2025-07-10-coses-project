package com.banking.coses.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/**
 * Common DTO for the COSES Framework
 * 
 * Contains common data shared across all COSES operations.
 */
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

    @JsonProperty("bankCode")
    private String bankCode;

    @JsonProperty("branchCode")
    private String branchCode;

    @JsonProperty("terminalId")
    private String terminalId;

    @JsonProperty("operatorId")
    private String operatorId;

    public CosesCommonDTO() {
        this.requestTime = LocalDateTime.now();
        this.status = "INIT";
        this.language = "ko";
        this.timezone = "Asia/Seoul";
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

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
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

    @Override
    public String toString() {
        return "CosesCommonDTO{" +
                "userId='" + userId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", status='" + status + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", branchCode='" + branchCode + '\'' +
                '}';
    }
}