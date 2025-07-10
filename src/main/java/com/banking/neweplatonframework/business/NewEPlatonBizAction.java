package com.banking.neweplatonframework.business;

import com.banking.neweplatonframework.transfer.NewEPlatonEvent;
import com.banking.neweplatonframework.exception.NewEPlatonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * New EPlaton Business Action Base Class
 * 
 * Base class for all New EPlaton business actions.
 * This replaces the legacy EPlatonBizAction with Spring Boot features.
 */
@Component
public abstract class NewEPlatonBizAction {

    protected static final Logger logger = LoggerFactory.getLogger(NewEPlatonBizAction.class);

    protected String actionName;
    protected String actionType;
    protected String actionVersion;

    public NewEPlatonBizAction() {
        this.actionName = this.getClass().getSimpleName();
        this.actionType = "BUSINESS";
        this.actionVersion = "1.0";
    }

    public NewEPlatonBizAction(String actionName) {
        this.actionName = actionName;
        this.actionType = "BUSINESS";
        this.actionVersion = "1.0";
    }

    public NewEPlatonBizAction(String actionName, String actionType) {
        this.actionName = actionName;
        this.actionType = actionType;
        this.actionVersion = "1.0";
    }

    /**
     * Execute the action with full lifecycle management
     */
    public NewEPlatonEvent act(NewEPlatonEvent event) throws NewEPlatonException {
        try {
            logger.info("Executing New EPlaton Business Action: {} for event: {}", actionName, event.getEventId());

            // Validate event
            if (!validateEvent(event)) {
                throw NewEPlatonException.invalidEvent("Invalid event for action: " + actionName);
            }

            // Pre-process
            preProcess(event);

            // Execute business logic
            NewEPlatonEvent result = executeAction(event);

            // Post-process
            postProcess(result);

            // Set success
            result.setSuccess();

            logger.info("New EPlaton Business Action {} completed successfully for event: {}", actionName,
                    event.getEventId());

            return result;

        } catch (Exception e) {
            logger.error("New EPlaton Business Action {} failed for event: {}", actionName, event.getEventId(), e);

            // Set error in event
            event.setError("EACT001", "Action execution failed: " + e.getMessage());

            if (e instanceof NewEPlatonException) {
                throw (NewEPlatonException) e;
            } else {
                throw NewEPlatonException.systemError("Action execution error: " + e.getMessage(), e);
            }
        }
    }

    /**
     * Execute the action - to be implemented by subclasses
     */
    protected abstract NewEPlatonEvent executeAction(NewEPlatonEvent event) throws NewEPlatonException;

    /**
     * Validate event
     */
    protected boolean validateEvent(NewEPlatonEvent event) {
        if (event == null) {
            logger.error("Event is null");
            return false;
        }

        if (event.getTpsvcInfo() == null) {
            logger.error("TPSVC info is null");
            return false;
        }

        if (event.getCommon() == null) {
            logger.error("Common data is null");
            return false;
        }

        return true;
    }

    /**
     * Pre-process action
     */
    protected void preProcess(NewEPlatonEvent event) throws NewEPlatonException {
        logger.debug("Pre-processing action: {} for event: {}", actionName, event.getEventId());

        // Set request time if not set
        if (event.getTpsvcInfo().getRequestTime() == null) {
            event.getTpsvcInfo().setRequestTime(LocalDateTime.now());
        }

        // Set system in time if not set
        if (event.getCommon().getSystemInTime() == null) {
            event.getCommon().setSystemInTime(LocalDateTime.now());
        }

        // Additional pre-processing logic can be added here
    }

    /**
     * Post-process action
     */
    protected void postProcess(NewEPlatonEvent event) throws NewEPlatonException {
        logger.debug("Post-processing action: {} for event: {}", actionName, event.getEventId());

        // Set response time
        event.getTpsvcInfo().setResponseTime(LocalDateTime.now());

        // Set system out time
        event.getCommon().setSystemOutTime(LocalDateTime.now());

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
        return "NewEPlatonBizAction{" +
                "actionName='" + actionName + '\'' +
                ", actionType='" + actionType + '\'' +
                ", actionVersion='" + actionVersion + '\'' +
                '}';
    }
}