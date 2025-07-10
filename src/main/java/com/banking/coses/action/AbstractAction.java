package com.banking.coses.action;

import com.banking.coses.transfer.CosesCommonDTO;
import com.banking.coses.transfer.CosesEvent;
import com.banking.coses.exception.CosesAppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Abstract base class for COSES Framework actions
 * 
 * Provides common functionality for all COSES actions.
 */
@Component
public abstract class AbstractAction {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractAction.class);

    protected String actionName;
    protected String actionType;
    protected String actionVersion;

    public AbstractAction() {
        this.actionName = this.getClass().getSimpleName();
        this.actionType = "DEFAULT";
        this.actionVersion = "1.0";
    }

    public AbstractAction(String actionName) {
        this.actionName = actionName;
        this.actionType = "DEFAULT";
        this.actionVersion = "1.0";
    }

    public AbstractAction(String actionName, String actionType) {
        this.actionName = actionName;
        this.actionType = actionType;
        this.actionVersion = "1.0";
    }

    /**
     * Execute the action with full lifecycle management
     */
    public CosesCommonDTO execute(CosesEvent event) throws Exception {
        logger.info("Executing action: {} for event: {}", actionName, event.getEventId());

        try {
            // Validate parameters
            if (!validateParameters(event)) {
                throw CosesAppException.invalidInput("Invalid parameters for action: " + actionName);
            }

            // Pre-process
            preProcess(event);

            // Execute business logic
            CosesCommonDTO result = executeAction(event);

            // Post-process
            postProcess(event, result);

            // Set success
            if (result != null) {
                result.setSuccess();
            }

            logger.info("Action {} completed successfully for event: {}", actionName, event.getEventId());

            return result;

        } catch (Exception e) {
            logger.error("Action {} failed for event: {}", actionName, event.getEventId(), e);

            // Create error result
            CosesCommonDTO errorResult = new CosesCommonDTO();
            if (e instanceof CosesAppException) {
                CosesAppException cae = (CosesAppException) e;
                errorResult.setError(cae.getErrorCode(), cae.getErrorMessage());
            } else {
                errorResult.setError("EACT001", "Action execution failed: " + e.getMessage());
            }

            // Set error in event
            event.setError(errorResult.getErrorCode(), errorResult.getErrorMessage());

            throw e;
        }
    }

    /**
     * Execute the action - to be implemented by subclasses
     */
    protected abstract CosesCommonDTO executeAction(CosesEvent event) throws Exception;

    /**
     * Validate action parameters
     */
    protected boolean validateParameters(CosesEvent event) {
        if (event == null) {
            logger.error("Event is null");
            return false;
        }

        if (event.getEventData() == null) {
            logger.error("Event data is null");
            return false;
        }

        if (event.getCommon() == null) {
            logger.error("Event common data is null");
            return false;
        }

        return true;
    }

    /**
     * Pre-process action
     */
    protected void preProcess(CosesEvent event) throws Exception {
        logger.debug("Pre-processing action: {} for event: {}", actionName, event.getEventId());

        // Set request time if not set
        if (event.getCommon().getRequestTime() == null) {
            event.getCommon().setRequestTime(LocalDateTime.now());
        }

        // Additional pre-processing logic can be added here
    }

    /**
     * Post-process action
     */
    protected void postProcess(CosesEvent event, CosesCommonDTO result) throws Exception {
        logger.debug("Post-processing action: {} for event: {}", actionName, event.getEventId());

        // Set response time
        if (result != null) {
            result.setResponseTime(LocalDateTime.now());
        }

        // Additional post-processing logic can be added here
    }

    /**
     * Get action information
     */
    public String getActionInfo() {
        return String.format("Action: %s, Type: %s, Version: %s", actionName, actionType, actionVersion);
    }

    /**
     * Check if action is ready
     */
    public boolean isReady() {
        return actionName != null && !actionName.trim().isEmpty();
    }

    // Getters and Setters
    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionVersion() {
        return actionVersion;
    }

    public void setActionVersion(String actionVersion) {
        this.actionVersion = actionVersion;
    }

    @Override
    public String toString() {
        return "AbstractAction{" +
                "actionName='" + actionName + '\'' +
                ", actionType='" + actionType + '\'' +
                ", actionVersion='" + actionVersion + '\'' +
                '}';
    }
}