package com.chb.coses.framework.transfer;

/**
 * Base interface for all events in the EPlaton Framework
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
    long getEventTimestamp();

    /**
     * Set the event timestamp
     * 
     * @param eventTimestamp event timestamp
     */
    void setEventTimestamp(long eventTimestamp);

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
}