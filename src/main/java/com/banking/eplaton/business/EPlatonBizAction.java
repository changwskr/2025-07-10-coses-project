package com.banking.eplaton.business;

import com.banking.eplaton.transfer.EPlatonEvent;
import com.banking.eplaton.transfer.EPlatonCommonDTO;
import com.banking.eplaton.transfer.TPSVCINFODTO;
import com.banking.framework.business.CosesBizAction;
import com.banking.framework.exception.CosesAppException;
import com.banking.framework.transfer.CosesCommonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * EPlaton Business Action Base Class
 * 
 * Base class for all EPlaton business actions.
 * This replaces the legacy EPlatonBizAction with Spring Boot features.
 */
@Component
public abstract class EPlatonBizAction {

    private static final Logger logger = LoggerFactory.getLogger(EPlatonBizAction.class);

    protected String actionName;
    protected String systemName;
    protected String operationName;
    protected boolean requiresTransaction;
    protected int timeoutSeconds;

    public EPlatonBizAction() {
        this.requiresTransaction = true;
        this.timeoutSeconds = 60;
    }

    public EPlatonBizAction(String actionName, String systemName) {
        this();
        this.actionName = actionName;
        this.systemName = systemName;
    }

    /**
     * Execute the business action
     */
    public EPlatonEvent act(EPlatonEvent event) throws CosesAppException {
        LocalDateTime startTime = LocalDateTime.now();

        try {
            logger.info("Starting EPlaton action: {} for system: {}", actionName, systemName);

            // Validate event
            validateEvent(event);

            // Pre-process
            preProcess(event);

            // Execute business logic
            EPlatonEvent result = executeBusinessLogic(event);

            // Post-process
            postProcess(result);

            // Mark success
            markSuccess(result);

            LocalDateTime endTime = LocalDateTime.now();
            logger.info("Completed EPlaton action: {} in {} ms", actionName,
                    java.time.Duration.between(startTime, endTime).toMillis());

            return result;

        } catch (Exception e) {
            logger.error("Error in EPlaton action: {}", actionName, e);
            markError(event, e);
            throw new CosesAppException("EPLATON_ACTION_ERROR",
                    "Error executing EPlaton action: " + e.getMessage(), e);
        }
    }

    /**
     * Execute the business logic
     */
    protected abstract EPlatonEvent executeBusinessLogic(EPlatonEvent event) throws CosesAppException;

    /**
     * Validate the event
     */
    protected void validateEvent(EPlatonEvent event) throws CosesAppException {
        if (event == null) {
            throw new CosesAppException("INVALID_EVENT", "Event cannot be null");
        }

        if (event.getCommon() == null) {
            throw new CosesAppException("INVALID_EVENT", "Event common data cannot be null");
        }

        if (event.getTpsvcInfo() == null) {
            throw new CosesAppException("INVALID_EVENT", "Event TPSVC info cannot be null");
        }
    }

    /**
     * Pre-process the event
     */
    protected void preProcess(EPlatonEvent event) throws CosesAppException {
        // Set common information
        EPlatonCommonDTO common = event.getCommon();
        common.setBankCode(event.getBankCode());
        common.setBranchCode(event.getBranchCode());
        common.setChannelType(event.getChannelType());
        common.setUserId(event.getUserId());
        common.setIpAddress(event.getIpAddress());
        common.setSessionId(event.getSessionId());
        common.setTransactionId(event.getTransactionId());

        // Set TPSVC information
        TPSVCINFODTO tpsvc = event.getTpsvcInfo();
        tpsvc.setSystemName(systemName);
        tpsvc.setActionName(actionName);
        tpsvc.setOperationName(operationName);

        logger.debug("Pre-processed event: {}", event.getEventId());
    }

    /**
     * Post-process the event
     */
    protected void postProcess(EPlatonEvent event) throws CosesAppException {
        // Add any post-processing logic here
        logger.debug("Post-processed event: {}", event.getEventId());
    }

    /**
     * Mark the event as successful
     */
    protected void markSuccess(EPlatonEvent event) {
        EPlatonCommonDTO common = event.getCommon();
        common.markSuccess();

        TPSVCINFODTO tpsvc = event.getTpsvcInfo();
        tpsvc.clearError();

        event.clearError();
    }

    /**
     * Mark the event as error
     */
    protected void markError(EPlatonEvent event, Exception e) {
        String errorCode = "EPLATON_ERROR";
        String errorMessage = e.getMessage();

        EPlatonCommonDTO common = event.getCommon();
        common.markError(errorCode, errorMessage);

        TPSVCINFODTO tpsvc = event.getTpsvcInfo();
        tpsvc.setError(errorCode, errorMessage);

        event.setError(errorCode, errorMessage);
    }

    /**
     * Get event data as map
     */
    protected Map<String, Object> getEventData(EPlatonEvent event) {
        return event.getEventData();
    }

    /**
     * Set event data
     */
    protected void setEventData(EPlatonEvent event, Map<String, Object> data) {
        event.setEventData(data);
    }

    // Getters and Setters
    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public boolean isRequiresTransaction() {
        return requiresTransaction;
    }

    public void setRequiresTransaction(boolean requiresTransaction) {
        this.requiresTransaction = requiresTransaction;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(int timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }
}