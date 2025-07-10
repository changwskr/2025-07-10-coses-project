package com.banking.coses.framework.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

/**
 * Base event class for the COSES Framework
 * 
 * Implements the IEvent interface and provides common functionality
 * for event objects used in the framework.
 */
public class CosesEvent implements IEvent {

    @JsonProperty("eventId")
    @NotBlank(message = "Event ID is required")
    private String eventId;

    @JsonProperty("eventType")
    @NotBlank(message = "Event type is required")
    private String eventType;

    @JsonProperty("eventStatus")
    private String eventStatus = "PENDING";

    @JsonProperty("eventTimestamp")
    private long eventTimestamp;

    @JsonProperty("eventDateTime")
    private LocalDateTime eventDateTime;

    @JsonProperty("action")
    @NotBlank(message = "Action is required")
    private String action;

    @JsonProperty("request")
    private IDTO request;

    @JsonProperty("response")
    private IDTO response;

    @JsonProperty("sourceSystem")
    private String sourceSystem;

    @JsonProperty("targetSystem")
    private String targetSystem;

    @JsonProperty("priority")
    private String priority = "NORMAL";

    @JsonProperty("description")
    private String description;

    @JsonProperty("errorCode")
    private String errorCode;

    @JsonProperty("errorMessage")
    private String errorMessage;

    public CosesEvent() {
        this.eventId = UUID.randomUUID().toString();
        this.eventTimestamp = System.currentTimeMillis();
        this.eventDateTime = LocalDateTime.now(ZoneId.systemDefault());
    }

    public CosesEvent(String eventType, String action) {
        this();
        this.eventType = eventType;
        this.action = action;
    }

    public CosesEvent(String eventId, String eventType, String action) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.action = action;
        this.eventTimestamp = System.currentTimeMillis();
        this.eventDateTime = LocalDateTime.now(ZoneId.systemDefault());
    }

    @Override
    public String getEventId() {
        return eventId;
    }

    @Override
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public String getEventType() {
        return eventType;
    }

    @Override
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public String getEventStatus() {
        return eventStatus;
    }

    @Override
    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    @Override
    public long getEventTimestamp() {
        return eventTimestamp;
    }

    @Override
    public void setEventTimestamp(long eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
        this.eventDateTime = LocalDateTime.ofInstant(
                java.time.Instant.ofEpochMilli(eventTimestamp),
                ZoneId.systemDefault());
    }

    @Override
    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }

    @Override
    public void setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
        this.eventTimestamp = eventDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public IDTO getRequest() {
        return request;
    }

    @Override
    public void setRequest(IDTO request) {
        this.request = request;
    }

    @Override
    public IDTO getResponse() {
        return response;
    }

    @Override
    public void setResponse(IDTO response) {
        this.response = response;
    }

    @Override
    public String getSourceSystem() {
        return sourceSystem;
    }

    @Override
    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    @Override
    public String getTargetSystem() {
        return targetSystem;
    }

    @Override
    public void setTargetSystem(String targetSystem) {
        this.targetSystem = targetSystem;
    }

    @Override
    public String getPriority() {
        return priority;
    }

    @Override
    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public boolean isValid() {
        return eventId != null && !eventId.trim().isEmpty() &&
                eventType != null && !eventType.trim().isEmpty() &&
                action != null && !action.trim().isEmpty();
    }

    @Override
    public void validate() {
        if (!isValid()) {
            throw new IllegalArgumentException("Event is not valid: Event ID, Event Type, and Action are required");
        }
    }

    /**
     * Mark event as successful
     */
    public void markSuccess() {
        this.eventStatus = "SUCCESS";
        this.errorCode = null;
        this.errorMessage = null;
    }

    /**
     * Mark event as failed
     */
    public void markFailed(String errorCode, String errorMessage) {
        this.eventStatus = "FAILED";
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * Mark event as processing
     */
    public void markProcessing() {
        this.eventStatus = "PROCESSING";
    }

    /**
     * Check if event is successful
     */
    @JsonIgnore
    public boolean isSuccess() {
        return "SUCCESS".equals(eventStatus);
    }

    /**
     * Check if event is failed
     */
    @JsonIgnore
    public boolean isFailed() {
        return "FAILED".equals(eventStatus);
    }

    /**
     * Check if event is pending
     */
    @JsonIgnore
    public boolean isPending() {
        return "PENDING".equals(eventStatus);
    }

    /**
     * Check if event is processing
     */
    @JsonIgnore
    public boolean isProcessing() {
        return "PROCESSING".equals(eventStatus);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        CosesEvent that = (CosesEvent) obj;
        return eventId != null ? eventId.equals(that.eventId) : that.eventId == null;
    }

    @Override
    public int hashCode() {
        return eventId != null ? eventId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CosesEvent{" +
                "eventId='" + eventId + '\'' +
                ", eventType='" + eventType + '\'' +
                ", eventStatus='" + eventStatus + '\'' +
                ", action='" + action + '\'' +
                ", eventDateTime=" + eventDateTime +
                ", sourceSystem='" + sourceSystem + '\'' +
                ", targetSystem='" + targetSystem + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}