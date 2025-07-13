package com.skcc.oversea.eplatonframework.business.event;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Business Event for SKCC Oversea
 * 
 * Represents business events that can be published and consumed by the system.
 */
public abstract class BusinessEvent {

    private final String eventId;
    private final String eventType;
    private final LocalDateTime timestamp;
    private final String source;
    private final String userId;
    private final String transactionId;

    /**
     * Constructor
     */
    protected BusinessEvent(String eventType, String source, String userId, String transactionId) {
        this.eventId = UUID.randomUUID().toString();
        this.eventType = eventType;
        this.timestamp = LocalDateTime.now();
        this.source = source;
        this.userId = userId;
        this.transactionId = transactionId;
    }

    /**
     * Get event ID
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * Get event type
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Get timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Get source
     */
    public String getSource() {
        return source;
    }

    /**
     * Get user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Get transaction ID
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Get event data
     */
    public abstract Object getEventData();

    /**
     * Validate event
     */
    public boolean isValid() {
        return eventType != null && !eventType.trim().isEmpty() &&
                source != null && !source.trim().isEmpty() &&
                userId != null && !userId.trim().isEmpty() &&
                transactionId != null && !transactionId.trim().isEmpty();
    }

    @Override
    public String toString() {
        return String.format(
                "BusinessEvent{eventId='%s', eventType='%s', timestamp=%s, source='%s', userId='%s', transactionId='%s'}",
                eventId, eventType, timestamp, source, userId, transactionId);
    }
}