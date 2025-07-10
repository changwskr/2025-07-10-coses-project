package com.banking.coses.framework.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

/**
 * Base interface for all events in the COSES Framework
 * 
 * Defines the contract for event objects that are used for communication
 * between different layers of the application.
 */
public interface IEvent {

    /**
     * Get the event ID
     * 
     * @return event ID
     */
    String getEventId();

    /**
     * Set the event ID
     * 
     * @param eventId event ID
     */
    void setEventId(String eventId);

    /**
     * Get the event type
     * 
     * @return event type
     */
    String getEventType();

    /**
     * Set the event type
     * 
     * @param eventType event type
     */
    void setEventType(String eventType);

    /**
     * Get the event status
     * 
     * @return event status
     */
    String getEventStatus();

    /**
     * Set the event status
     * 
     * @param eventStatus event status
     */
    void setEventStatus(String eventStatus);

    /**
     * Get the event timestamp
     * 
     * @return event timestamp
     */
    @JsonIgnore
    long getEventTimestamp();

    /**
     * Set the event timestamp
     * 
     * @param eventTimestamp event timestamp
     */
    void setEventTimestamp(long eventTimestamp);

    /**
     * Get the event date time
     * 
     * @return event date time
     */
    LocalDateTime getEventDateTime();

    /**
     * Set the event date time
     * 
     * @param eventDateTime event date time
     */
    void setEventDateTime(LocalDateTime eventDateTime);

    /**
     * Get the action
     * 
     * @return action
     */
    String getAction();

    /**
     * Set the action
     * 
     * @param action action
     */
    void setAction(String action);

    /**
     * Get the request
     * 
     * @return request
     */
    IDTO getRequest();

    /**
     * Set the request
     * 
     * @param request request
     */
    void setRequest(IDTO request);

    /**
     * Get the response
     * 
     * @return response
     */
    IDTO getResponse();

    /**
     * Set the response
     * 
     * @param response response
     */
    void setResponse(IDTO response);

    /**
     * Get the source system
     * 
     * @return source system
     */
    String getSourceSystem();

    /**
     * Set the source system
     * 
     * @param sourceSystem source system
     */
    void setSourceSystem(String sourceSystem);

    /**
     * Get the target system
     * 
     * @return target system
     */
    String getTargetSystem();

    /**
     * Set the target system
     * 
     * @param targetSystem target system
     */
    void setTargetSystem(String targetSystem);

    /**
     * Get the priority
     * 
     * @return priority
     */
    String getPriority();

    /**
     * Set the priority
     * 
     * @param priority priority
     */
    void setPriority(String priority);

    /**
     * Check if event is valid
     * 
     * @return true if valid, false otherwise
     */
    @JsonIgnore
    boolean isValid();

    /**
     * Validate event
     * 
     * @throws IllegalArgumentException if event is invalid
     */
    @JsonIgnore
    void validate();
}