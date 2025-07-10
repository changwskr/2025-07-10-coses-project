package com.banking.eplaton.transfer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

/**
 * EPlaton Common DTO
 * 
 * Contains common information for all EPlaton events.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EPlatonCommonDTO {

    private String bankCode;
    private String branchCode;
    private String channelType;
    private String eventNo;
    private String systemDate;
    private String userId;
    private String reqName;
    private String ipAddress;
    private String sessionId;
    private String transactionId;
    private LocalDateTime requestTime;
    private LocalDateTime responseTime;
    private String status;
    private String errorCode;
    private String errorMessage;

    public EPlatonCommonDTO() {
        this.requestTime = LocalDateTime.now();
        this.status = "PENDING";
    }

    // Getters and Setters
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

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getEventNo() {
        return eventNo;
    }

    public void setEventNo(String eventNo) {
        this.eventNo = eventNo;
    }

    public String getSystemDate() {
        return systemDate;
    }

    public void setSystemDate(String systemDate) {
        this.systemDate = systemDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReqName() {
        return reqName;
    }

    public void setReqName(String reqName) {
        this.reqName = reqName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
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

    // Helper methods
    public void markSuccess() {
        this.status = "SUCCESS";
        this.responseTime = LocalDateTime.now();
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

    public boolean hasError() {
        return "ERROR".equals(this.status);
    }

    @Override
    public String toString() {
        return "EPlatonCommonDTO{" +
                "bankCode='" + bankCode + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", channelType='" + channelType + '\'' +
                ", userId='" + userId + '\'' +
                ", status='" + status + '\'' +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}