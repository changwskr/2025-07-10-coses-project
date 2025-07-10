package com.ims.oversea.framework.transaction.model;

import java.util.Date;

/**
 * Transaction Log Data Transfer Object
 */
public class TransactionLogDDTO {

    private String transactionId;
    private String eventType;
    private Date eventTime;
    private String userId;
    private String sessionId;
    private String additionalInfo;
    private String status;
    private long processingTime;

    public TransactionLogDDTO() {
        this.eventTime = new Date();
    }

    public TransactionLogDDTO(String transactionId, String eventType) {
        this();
        this.transactionId = transactionId;
        this.eventType = eventType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

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

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(long processingTime) {
        this.processingTime = processingTime;
    }

    @Override
    public String toString() {
        return "TransactionLogDDTO{" +
                "transactionId='" + transactionId + '\'' +
                ", eventType='" + eventType + '\'' +
                ", eventTime=" + eventTime +
                ", userId='" + userId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", status='" + status + '\'' +
                ", processingTime=" + processingTime +
                '}';
    }
}
