package com.banking.framework.transfer;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Coses Event for Spring Boot Framework
 * 
 * Represents an event in the Coses framework with event data and metadata.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CosesEvent {

    private String eventId;
    private String eventType;
    private String eventName;
    private LocalDateTime eventTime;
    private String source;
    private String target;
    private Map<String, Object> eventData;
    private CosesCommonDTO commonData;
    private Map<String, Object> metadata;

    // Constructors
    public CosesEvent() {
        this.eventTime = LocalDateTime.now();
        this.eventData = new HashMap<>();
        this.metadata = new HashMap<>();
        this.commonData = new CosesCommonDTO();
    }

    public CosesEvent(String eventType, String eventName) {
        this();
        this.eventType = eventType;
        this.eventName = eventName;
        this.eventId = generateEventId();
    }

    public CosesEvent(String eventType, String eventName, Map<String, Object> eventData) {
        this(eventType, eventName);
        this.eventData = eventData != null ? eventData : new HashMap<>();
    }

    // Getters and Setters
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
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

    public Map<String, Object> getEventData() {
        return eventData;
    }

    public void setEventData(Map<String, Object> eventData) {
        this.eventData = eventData;
    }

    public CosesCommonDTO getCommonData() {
        return commonData;
    }

    public void setCommonData(CosesCommonDTO commonData) {
        this.commonData = commonData;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    // Helper methods
    public void addEventData(String key, Object value) {
        if (this.eventData == null) {
            this.eventData = new HashMap<>();
        }
        this.eventData.put(key, value);
    }

    public Object getEventData(String key) {
        return this.eventData != null ? this.eventData.get(key) : null;
    }

    public void addMetadata(String key, Object value) {
        if (this.metadata == null) {
            this.metadata = new HashMap<>();
        }
        this.metadata.put(key, value);
    }

    public Object getMetadata(String key) {
        return this.metadata != null ? this.metadata.get(key) : null;
    }

    public boolean hasEventData(String key) {
        return this.eventData != null && this.eventData.containsKey(key);
    }

    public boolean hasMetadata(String key) {
        return this.metadata != null && this.metadata.containsKey(key);
    }

    private String generateEventId() {
        return "EVT" + System.currentTimeMillis() + "_" +
                Thread.currentThread().getId() + "_" +
                (int) (Math.random() * 1000);
    }

    @Override
    public String toString() {
        return "CosesEvent{" +
                "eventId='" + eventId + '\'' +
                ", eventType='" + eventType + '\'' +
                ", eventName='" + eventName + '\'' +
                ", eventTime=" + eventTime +
                ", source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", eventDataSize=" + (eventData != null ? eventData.size() : 0) +
                '}';
    }
}