package com.skcc.oversea.eplatonframework.business.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.skcc.oversea.eplatonframework.transfer.EPlatonEvent;
import com.skcc.oversea.eplatonframework.transfer.TPSVCINFODTO;
import com.skcc.oversea.foundation.logej.LOGEJ;
import com.skcc.oversea.foundation.constant.Constants;

/**
 * Base Service Implementation for SKCC Oversea
 * 
 * Provides common functionality for all business services.
 */
public abstract class BaseServiceImpl implements BaseService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Validate event data
     */
    @Override
    public boolean validateEvent(EPlatonEvent event) {
        if (event == null) {
            logger.warn("Event is null");
            return false;
        }

        TPSVCINFODTO tpsvcinfo = event.getTPSVCINFODTO();
        if (tpsvcinfo == null) {
            logger.warn("TPSVCINFODTO is null");
            return false;
        }

        String reqName = tpsvcinfo.getReqName();
        if (reqName == null || reqName.trim().isEmpty()) {
            logger.warn("Request name is null or empty");
            return false;
        }

        return true;
    }

    /**
     * Set error information
     */
    @Override
    public void setErrorInfo(EPlatonEvent event, String errorCode, String errorMessage) {
        TPSVCINFODTO tpsvcinfo = event.getTPSVCINFODTO();
        String currentErrorCode = tpsvcinfo.getErrorcode();

        if (currentErrorCode != null && currentErrorCode.startsWith("I")) {
            tpsvcinfo.setErrorcode(errorCode);
            tpsvcinfo.setError_message(errorMessage);
        } else if (currentErrorCode != null && currentErrorCode.startsWith("E")) {
            String combinedErrorCode = errorCode + "|" + currentErrorCode;
            tpsvcinfo.setErrorcode(combinedErrorCode);
            tpsvcinfo.setError_message(errorMessage);
        } else {
            tpsvcinfo.setErrorcode(errorCode);
            tpsvcinfo.setError_message(errorMessage);
        }

        logger.error("Error set: {} - {}", errorCode, errorMessage);
    }

    /**
     * Set success response
     */
    @Override
    public void setSuccessResponse(EPlatonEvent event, String message) {
        event.setResponse(message);
        event.getTPSVCINFODTO().setErrorcode("I0000");
        event.getTPSVCINFODTO().setError_message(message);

        logger.info("Success response set: {}", message);
    }

    /**
     * Log business operation
     */
    @Override
    public void logBusinessOperation(String operation, EPlatonEvent event) {
        String reqName = event.getTPSVCINFODTO().getReqName();
        logger.info("Business operation: {} - Request: {}", operation, reqName);

        // Log to LOGEJ if needed
        LOGEJ.logBusinessEvent(operation, reqName, "Service: " + getClass().getSimpleName());
    }

    /**
     * Get service name
     */
    protected String getServiceName() {
        return getClass().getSimpleName();
    }

    /**
     * Validate request data
     */
    protected boolean isValidRequestData(Object requestData) {
        return requestData != null;
    }

    /**
     * Extract request data safely
     */
    protected Object extractRequestData(EPlatonEvent event) {
        return event.getRequest();
    }

    /**
     * Handle service exception
     */
    protected EPlatonEvent handleServiceException(EPlatonEvent event, Exception e, String errorCode) {
        logger.error("Service exception in {}: {}", getServiceName(), e.getMessage(), e);
        setErrorInfo(event, errorCode, "Service operation failed: " + e.getMessage());
        return event;
    }
}