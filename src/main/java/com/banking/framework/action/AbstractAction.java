package com.banking.framework.action;

import com.banking.framework.exception.CosesAppException;
import com.banking.framework.transfer.CosesCommonDTO;
import com.banking.framework.transfer.CosesEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Abstract base class for Coses Framework actions (Spring Boot Version)
 * 
 * Provides common functionality for all framework actions including validation,
 * pre-processing, execution, and post-processing.
 */
@Component
public abstract class AbstractAction {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractAction.class);

    protected String actionName;
    protected String actionType;
    protected String description;
    protected LocalDateTime createdAt;
    protected boolean enabled;

    // Constructors
    public AbstractAction() {
        this.actionName = this.getClass().getSimpleName();
        this.actionType = "DEFAULT";
        this.description = "Default action description";
        this.createdAt = LocalDateTime.now();
        this.enabled = true;
    }

    public AbstractAction(String actionName) {
        this();
        this.actionName = actionName;
    }

    public AbstractAction(String actionName, String actionType, String description) {
        this(actionName);
        this.actionType = actionType;
        this.description = description;
    }

    /**
     * Execute the action with full lifecycle management
     * 
     * @param event the event to process
     * @return execution result
     * @throws CosesAppException if execution fails
     */
    public CosesCommonDTO execute(CosesEvent event) throws CosesAppException {
        if (!enabled) {
            throw new CosesAppException("ACTION_DISABLED",
                    "Action is disabled: " + actionName,
                    org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE);
        }

        CosesCommonDTO result = new CosesCommonDTO();
        result.setTransactionId(event.getEventId());

        try {
            logger.debug("Starting action execution: {}", actionName);

            // Pre-process
            preProcess(event);

            // Validate parameters
            if (!validateParameters(event)) {
                throw new CosesAppException("INVALID_PARAMETERS",
                        "Invalid parameters for action: " + actionName,
                        org.springframework.http.HttpStatus.BAD_REQUEST);
            }

            // Execute main logic
            result = doExecute(event);

            // Post-process
            postProcess(event, result);

            // Mark success
            result.markSuccess();

            logger.debug("Action execution completed successfully: {}", actionName);

        } catch (CosesAppException e) {
            logger.error("Action execution failed with CosesAppException: {}", actionName, e);
            result.markError(e.getErrorCode(), e.getErrorMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Action execution failed with unexpected error: {}", actionName, e);
            result.markError("ACTION_EXECUTION_ERROR",
                    "Unexpected error during action execution: " + e.getMessage());
            throw new CosesAppException("ACTION_EXECUTION_ERROR",
                    "Unexpected error during action execution", e);
        }

        return result;
    }

    /**
     * Execute the main action logic - to be implemented by subclasses
     * 
     * @param event the event to process
     * @return execution result
     * @throws CosesAppException if execution fails
     */
    protected abstract CosesCommonDTO doExecute(CosesEvent event) throws CosesAppException;

    /**
     * Validate action parameters
     * 
     * @param event the event to validate
     * @return true if valid, false otherwise
     */
    protected boolean validateParameters(CosesEvent event) {
        return event != null && event.getEventData() != null;
    }

    /**
     * Pre-process action - override if needed
     * 
     * @param event the event to pre-process
     * @throws CosesAppException if pre-processing fails
     */
    protected void preProcess(CosesEvent event) throws CosesAppException {
        logger.debug("Pre-processing action: {}", actionName);
        // Default implementation - override if needed
    }

    /**
     * Post-process action - override if needed
     * 
     * @param event  the original event
     * @param result the execution result
     * @throws CosesAppException if post-processing fails
     */
    protected void postProcess(CosesEvent event, CosesCommonDTO result) throws CosesAppException {
        logger.debug("Post-processing action: {}", actionName);
        // Default implementation - override if needed
    }

    /**
     * Enable the action
     */
    public void enable() {
        this.enabled = true;
        logger.info("Action enabled: {}", actionName);
    }

    /**
     * Disable the action
     */
    public void disable() {
        this.enabled = false;
        logger.info("Action disabled: {}", actionName);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "AbstractAction{" +
                "actionName='" + actionName + '\'' +
                ", actionType='" + actionType + '\'' +
                ", description='" + description + '\'' +
                ", enabled=" + enabled +
                ", createdAt=" + createdAt +
                '}';
    }
}