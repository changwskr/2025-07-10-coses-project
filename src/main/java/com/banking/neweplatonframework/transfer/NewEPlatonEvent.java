package com.banking.neweplatonframework.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * New EPlaton Event for framework operations
 * 
 * Represents an event in the New EPlaton framework with common data and
 * event-specific data.
 * This replaces the legacy EPlatonEvent with Spring Boot features.
 */
public class NewEPlatonEvent {

    @JsonProperty("eventId")
    private String eventId;

    @JsonProperty("action")
    private String action;

    @JsonProperty("common")
    private NewEPlatonCommonDTO common;

    @JsonProperty("tpsvcInfo")
    private TPSVCINFODTO tpsvcInfo;

    @JsonProperty("request")
    private Object request;

    @JsonProperty("response")
    private Object response;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("source")
    private String source;

    @JsonProperty("target")
    private String target;

    @JsonProperty("priority")
    private String priority;

    public NewEPlatonEvent() {
        this.eventId = UUID.randomUUID().toString();
        this.timestamp = LocalDateTime.now();
        this.common = new NewEPlatonCommonDTO();
        this.tpsvcInfo = new TPSVCINFODTO();
    }

    public NewEPlatonEvent(String action, Object request) {
        this();
        this.action = action;
        this.request = request;
    }

    public NewEPlatonEvent(String eventId, String action, Object request) {
        this.eventId = eventId;
        this.action = action;
        this.request = request;
        this.timestamp = LocalDateTime.now();
        this.common = new NewEPlatonCommonDTO();
        this.tpsvcInfo = new TPSVCINFODTO();
    }

    // Getters and Setters
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public NewEPlatonCommonDTO getCommon() {
        return common;
    }

    public void setCommon(NewEPlatonCommonDTO common) {
        this.common = common;
    }

    public TPSVCINFODTO getTpsvcInfo() {
        return tpsvcInfo;
    }

    public void setTpsvcInfo(TPSVCINFODTO tpsvcInfo) {
        this.tpsvcInfo = tpsvcInfo;
    }

    public Object getRequest() {
        return request;
    }

    public void setRequest(Object request) {
        this.request = request;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
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
        return tpsvcInfo != null &&
                tpsvcInfo.getErrorCode() != null &&
                !tpsvcInfo.getErrorCode().trim().isEmpty() &&
                !tpsvcInfo.getErrorCode().startsWith("I");
    }

    /**
     * Set error information
     */
    public void setError(String errorCode, String errorMessage) {
        if (tpsvcInfo == null) {
            tpsvcInfo = new TPSVCINFODTO();
        }
        tpsvcInfo.setErrorCode(errorCode);
        tpsvcInfo.setErrorMessage(errorMessage);
        tpsvcInfo.setStatus("ERROR");
    }

    /**
     * Set success information
     */
    public void setSuccess() {
        if (tpsvcInfo == null) {
            tpsvcInfo = new TPSVCINFODTO();
        }
        tpsvcInfo.setErrorCode("IZZ000");
        tpsvcInfo.setErrorMessage("Success");
        tpsvcInfo.setStatus("SUCCESS");
    }

    /**
     * Get transaction ID
     */
    public String getTransactionId() {
        return common != null ? common.getTransactionNo() : null;
    }

    /**
     * Set transaction ID
     */
    public void setTransactionId(String transactionId) {
        if (common == null) {
            common = new NewEPlatonCommonDTO();
        }
        common.setTransactionNo(transactionId);
    }

    @Override
    public String toString() {
        return "NewEPlatonEvent{" +
                "eventId='" + eventId + '\'' +
                ", action='" + action + '\'' +
                ", timestamp=" + timestamp +
                ", source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}