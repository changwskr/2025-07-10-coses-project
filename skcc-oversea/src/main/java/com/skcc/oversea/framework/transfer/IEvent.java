package com.skcc.oversea.framework.transfer;

import java.io.Serializable;

/**
 * Interface for Event objects
 * Replaces com.chb.coses.framework.transfer.IEvent
 */
public interface IEvent extends Serializable {

    /**
     * Get event ID
     */
    String getEventId();

    /**
     * Set event ID
     */
    void setEventId(String eventId);

    /**
     * Get event type
     */
    String getEventType();

    /**
     * Set event type
     */
    void setEventType(String eventType);

    /**
     * Get event data
     */
    Object getEventData();

    /**
     * Set event data
     */
    void setEventData(Object eventData);

    /**
     * Get timestamp
     */
    long getTimestamp();

    /**
     * Set timestamp
     */
    void setTimestamp(long timestamp);

    /**
     * Get source
     */
    String getSource();

    /**
     * Set source
     */
    void setSource(String source);
}