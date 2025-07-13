package com.skcc.oversea.eplatonframework.business.service;

import com.skcc.oversea.eplatonframework.transfer.EPlatonEvent;

/**
 * Base Service Interface for SKCC Oversea
 * 
 * Defines common methods that all business services should implement.
 */
public interface BaseService {

    /**
     * Validate event data
     */
    boolean validateEvent(EPlatonEvent event);

    /**
     * Set error information
     */
    void setErrorInfo(EPlatonEvent event, String errorCode, String errorMessage);

    /**
     * Set success response
     */
    void setSuccessResponse(EPlatonEvent event, String message);

    /**
     * Log business operation
     */
    void logBusinessOperation(String operation, EPlatonEvent event);
}