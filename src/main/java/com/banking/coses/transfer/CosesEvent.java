package com.banking.coses.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * COSES Event for framework operations
 * 
 * Represents an event in the COSES framework with common data and
 * event-specific data.
 */
public class CosesEvent {

    @JsonProperty("eventId")
    private String eventId;

    @JsonProperty("transactionId")
    private String transactionId;

    @JsonProperty("common")
    private CosesCommonDTO common;

    @JsonProperty("eventData")
    private Object eventData;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("source")
    private String source;

    @JsonProperty("target")
    private String target;

    @JsonProperty("priority")
    private String priority;

    public CosesEvent() {
        this.eventId = UUID.randomUUID().toString();
        this.transactionId = UUID.randomUUID().toString();
        this.timestamp = LocalDateTime.now();
        this.common = new CosesCommonDTO();
    }

    public CosesEvent(Object eventData) {
        this();
        this.eventData = eventData;
    }

    public CosesEvent(String eventId, Object eventData) {
        this.eventId = eventId;
        this.transactionId = UUID.randomUUID().toString();
        this.eventData = eventData;
        this.timestamp = LocalDateTime.now();
        this.common = new CosesCommonDTO();
    }

    // Getters and Setters
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public CosesCommonDTO getCommon() {
        return common;
    }

    public void setCommon(CosesCommonDTO common) {
        this.common = common;
    }

    public Object getEventData() {
        return eventData;
    }

    public void setEventData(Object eventData) {
        this.eventData = eventData;
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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * Check if event has error
     */
    public boolean hasError() {
        return common != null &&
                common.getErrorCode() != null &&
                !common.getErrorCode().trim().isEmpty() &&
                !common.getErrorCode().startsWith("I");
    }

    /**
     * Set error information
     */
    public void setError(String errorCode, String errorMessage) {
        if (common == null) {
            common = new CosesCommonDTO();
        }
        common.setErrorCode(errorCode);
        common.setErrorMessage(errorMessage);
        common.setStatus("ERROR");
    }

    /**
     * Set success information
     */
    public void setSuccess() {
        if (common == null) {
            common = new CosesCommonDTO();
        }
        common.setErrorCode("IZZ000");
        common.setErrorMessage("Success");
        common.setStatus("SUCCESS");
    }

    @Override
    public String toString() {
        return "CosesEvent{" +
                "eventId='" + eventId + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", timestamp=" + timestamp +
                ", source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}