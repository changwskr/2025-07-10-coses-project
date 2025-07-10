package com.chb.coses.framework.transfer;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * COSES Event Data Transfer Object
 */
public class CosesEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private String eventId;
    private String eventType;
    private String eventName;
    private Date eventTime;
    private String userId;
    private String sessionId;
    private Object eventData;
    private Map<String, Object> parameters;
    private String source;
    private String destination;

    public CosesEvent() {
        this.eventTime = new Date();
        this.parameters = new HashMap<>();
    }

    public CosesEvent(String eventType, String eventName) {
        this();
        this.eventType = eventType;
        this.eventName = eventName;
    }

    public CosesEvent(String eventType, String eventName, Object eventData) {
        this(eventType, eventName);
        this.eventData = eventData;
    }

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

    public Object getEventData() {
        return eventData;
    }

    public void setEventData(Object eventData) {
        this.eventData = eventData;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Add parameter to event
     */
    public void addParameter(String key, Object value) {
        if (parameters == null) {
            parameters = new HashMap<>();
        }
        parameters.put(key, value);
    }

    /**
     * Get parameter from event
     */
    public Object getParameter(String key) {
        return parameters != null ? parameters.get(key) : null;
    }

    /**
     * Remove parameter from event
     */
    public Object removeParameter(String key) {
        return parameters != null ? parameters.remove(key) : null;
    }

    /**
     * Check if event has parameter
     */
    public boolean hasParameter(String key) {
        return parameters != null && parameters.containsKey(key);
    }

    /**
     * Clear all parameters
     */
    public void clearParameters() {
        if (parameters != null) {
            parameters.clear();
        }
    }

    @Override
    public String toString() {
        return "CosesEvent{" +
                "eventId='" + eventId + '\'' +
                ", eventType='" + eventType + '\'' +
                ", eventName='" + eventName + '\'' +
                ", eventTime=" + eventTime +
                ", userId='" + userId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}