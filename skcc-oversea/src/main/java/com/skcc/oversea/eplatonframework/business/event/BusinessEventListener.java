package com.skcc.oversea.eplatonframework.business.event;

/**
 * Business Event Listener for SKCC Oversea
 * 
 * Interface for handling business events.
 */
public interface BusinessEventListener<T extends BusinessEvent> {

    /**
     * Handle business event
     */
    void handleEvent(T event);

    /**
     * Get supported event type
     */
    String getSupportedEventType();

    /**
     * Check if listener can handle the event
     */
    default boolean canHandle(BusinessEvent event) {
        return getSupportedEventType().equals(event.getEventType());
    }

    /**
     * Get listener priority (lower number = higher priority)
     */
    default int getPriority() {
        return 0;
    }
}