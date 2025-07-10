package com.banking.eplaton.transfer;

import com.banking.framework.transfer.CosesEvent;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * EPlaton Event
 * 
 * Represents an event in the EPlaton framework.
 * This replaces the legacy EPlatonEvent with modern Spring Boot features.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EPlatonEvent extends CosesEvent {

    private String eventId;
    private LocalDateTime eventTime;
    private String actionName;
    private String operationName;
    private String systemName;
    private String channelType;
    private String bankCode;
    private String branchCode;
    private String userId;
    private String ipAddress;
    private String sessionId;
    private String transactionId;
    private String errorCode;
    private String errorMessage;
    private Map<String, Object> eventData;
    private EPlatonCommonDTO common;
    private TPSVCINFODTO tpsvcInfo;

    public EPlatonEvent() {
        this.eventId = UUID.randomUUID().toString();
        this.eventTime = LocalDateTime.now();
        this.common = new EPlatonCommonDTO();
        this.tpsvcInfo = new TPSVCINFODTO();
    }

    public EPlatonEvent(String actionName, String systemName) {
        this();
        this.actionName = actionName;
        this.systemName = systemName;
    }

    // Getters and Setters
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
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

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Map<String, Object> getEventData() {
        return eventData;
    }

    public void setEventData(Map<String, Object> eventData) {
        this.eventData = eventData;
    }

    public EPlatonCommonDTO getCommon() {
        return common;
    }

    public void setCommon(EPlatonCommonDTO common) {
        this.common = common;
    }

    public TPSVCINFODTO getTpsvcInfo() {
        return tpsvcInfo;
    }

    public void setTpsvcInfo(TPSVCINFODTO tpsvcInfo) {
        this.tpsvcInfo = tpsvcInfo;
    }

    // Helper methods
    public boolean hasError() {
        return errorCode != null && !errorCode.isEmpty();
    }

    public void setError(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public void clearError() {
        this.errorCode = null;
        this.errorMessage = null;
    }

    @Override
    public String toString() {
        return "EPlatonEvent{" +
                "eventId='" + eventId + '\'' +
                ", actionName='" + actionName + '\'' +
                ", systemName='" + systemName + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", userId='" + userId + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}