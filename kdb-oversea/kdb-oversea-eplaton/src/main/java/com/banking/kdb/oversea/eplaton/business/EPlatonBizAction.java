package com.banking.kdb.oversea.eplaton.business;

import com.banking.coses.framework.transfer.IEvent;
import com.banking.coses.framework.exception.BizActionException;
import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.eplaton.transfer.EPlatonEvent;
import com.banking.kdb.oversea.eplaton.transfer.EPlatonCommonDTO;
import com.banking.kdb.oversea.eplaton.transfer.TPSVCINFODTO;
import com.banking.kdb.oversea.foundation.config.KdbConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Abstract base class for EPlaton Business Actions
 * 
 * Provides common functionality for all business actions
 * in the KDB Oversea EPlaton framework.
 */
@Transactional
public abstract class EPlatonBizAction {

    protected final FoundationLogger logger = FoundationLogger.getLogger(getClass());

    @Autowired
    protected KdbConfig kdbConfig;

    /**
     * Execute business action
     * 
     * @param event The EPlaton event containing request data
     * @return IEvent The response event
     * @throws BizActionException if action execution fails
     */
    public abstract IEvent act(IEvent event) throws BizActionException;

    /**
     * Validate input data
     * 
     * @param event The event to validate
     * @throws BizActionException if validation fails
     */
    protected void validateInput(IEvent event) throws BizActionException {
        if (event == null) {
            throw new BizActionException("INPUT_VALIDATION_ERROR", "Event cannot be null");
        }

        if (!(event instanceof EPlatonEvent)) {
            throw new BizActionException("INPUT_VALIDATION_ERROR", "Event must be EPlatonEvent");
        }

        EPlatonEvent eplEvent = (EPlatonEvent) event;
        EPlatonCommonDTO request = eplEvent.getRequest();

        if (request == null) {
            throw new BizActionException("INPUT_VALIDATION_ERROR", "Request data cannot be null");
        }
    }

    /**
     * Log action execution
     * 
     * @param event  The event being processed
     * @param action The action being performed
     */
    protected void logActionExecution(IEvent event, String action) {
        EPlatonEvent eplEvent = (EPlatonEvent) event;
        TPSVCINFODTO tpsvcInfo = eplEvent.getTPSVCINFODTO();

        logger.info("Executing action - Action: {}, User: {}, Request: {}, Timestamp: {}",
                action, tpsvcInfo.getUser_id(), tpsvcInfo.getRequest_name(), LocalDateTime.now());
    }

    /**
     * Log action completion
     * 
     * @param event   The event that was processed
     * @param action  The action that was performed
     * @param success Whether the action was successful
     */
    protected void logActionCompletion(IEvent event, String action, boolean success) {
        EPlatonEvent eplEvent = (EPlatonEvent) event;
        TPSVCINFODTO tpsvcInfo = eplEvent.getTPSVCINFODTO();

        if (success) {
            logger.info("Action completed successfully - Action: {}, User: {}, Response: {}",
                    action, tpsvcInfo.getUser_id(), tpsvcInfo.getResponse_name());
        } else {
            logger.error("Action failed - Action: {}, User: {}, ErrorCode: {}, ErrorMessage: {}",
                    action, tpsvcInfo.getUser_id(), tpsvcInfo.getErrorcode(), tpsvcInfo.getError_message());
        }
    }

    /**
     * Set success response
     * 
     * @param event    The event to update
     * @param response The response data
     */
    protected void setSuccessResponse(IEvent event, EPlatonCommonDTO response) {
        EPlatonEvent eplEvent = (EPlatonEvent) event;
        TPSVCINFODTO tpsvcInfo = eplEvent.getTPSVCINFODTO();

        tpsvcInfo.setErrorcode("I0000");
        tpsvcInfo.setError_message("SUCCESS");
        eplEvent.setResponse(response);

        logger.debug("Success response set - ErrorCode: {}, Response: {}",
                tpsvcInfo.getErrorcode(), response != null ? response.getClass().getSimpleName() : "null");
    }

    /**
     * Set error response
     * 
     * @param event        The event to update
     * @param errorCode    The error code
     * @param errorMessage The error message
     */
    protected void setErrorResponse(IEvent event, String errorCode, String errorMessage) {
        EPlatonEvent eplEvent = (EPlatonEvent) event;
        TPSVCINFODTO tpsvcInfo = eplEvent.getTPSVCINFODTO();

        tpsvcInfo.setErrorcode(errorCode);
        tpsvcInfo.setError_message(errorMessage);

        logger.error("Error response set - ErrorCode: {}, ErrorMessage: {}", errorCode, errorMessage);
    }

    /**
     * Get request data from event
     * 
     * @param event The event containing request data
     * @return EPlatonCommonDTO The request data
     */
    protected EPlatonCommonDTO getRequest(IEvent event) {
        EPlatonEvent eplEvent = (EPlatonEvent) event;
        return eplEvent.getRequest();
    }

    /**
     * Get TPSVCINFODTO from event
     * 
     * @param event The event containing TPSVCINFODTO
     * @return TPSVCINFODTO The service info
     */
    protected TPSVCINFODTO getTPSVCINFO(IEvent event) {
        EPlatonEvent eplEvent = (EPlatonEvent) event;
        return eplEvent.getTPSVCINFODTO();
    }

    /**
     * Check if running in development mode
     * 
     * @return boolean True if in development mode
     */
    protected boolean isDevelopmentMode() {
        return kdbConfig.isDevelopmentMode();
    }

    /**
     * Get configuration value
     * 
     * @param key The configuration key
     * @return String The configuration value
     */
    protected String getConfigValue(String key) {
        return kdbConfig.getValue(key);
    }

    /**
     * Get configuration value with default
     * 
     * @param key          The configuration key
     * @param defaultValue The default value
     * @return String The configuration value
     */
    protected String getConfigValue(String key, String defaultValue) {
        return kdbConfig.getValue(key, defaultValue);
    }

    /**
     * Get service configuration value
     * 
     * @param serviceName The service name
     * @param elementName The element name
     * @return String The configuration value
     */
    protected String getServiceConfigValue(String serviceName, String elementName) {
        return kdbConfig.getServiceValue(serviceName, elementName);
    }

    /**
     * Get service configuration value with default
     * 
     * @param serviceName  The service name
     * @param elementName  The element name
     * @param defaultValue The default value
     * @return String The configuration value
     */
    protected String getServiceConfigValue(String serviceName, String elementName, String defaultValue) {
        return kdbConfig.getServiceValue(serviceName, elementName, defaultValue);
    }
}