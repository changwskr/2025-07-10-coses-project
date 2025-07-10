package com.banking.neweplatonframework.othersystem.operation;

import com.banking.neweplatonframework.transfer.NewEPlatonEvent;
import com.banking.neweplatonframework.exception.NewEPlatonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

/**
 * New DED0021000 Operation
 * 
 * Sample other system operation for New EPlaton Framework.
 * This replaces the legacy DED0021000 with Spring Boot features.
 */
@Component
public class NewDED0021000 {

    private static final Logger logger = LoggerFactory.getLogger(NewDED0021000.class);

    private String operationName = "DED0021000";
    private String operationType = "OTHER_SYSTEM";
    private String operationVersion = "1.0";

    public NewDED0021000() {
    }

    /**
     * Execute DED0021000 operation
     */
    public NewEPlatonEvent execute(NewEPlatonEvent event) throws NewEPlatonException {
        try {
            logger.info("Executing New DED0021000 operation for event: {}", event.getEventId());

            // Validate event
            if (!validateEvent(event)) {
                throw NewEPlatonException.invalidEvent("Invalid event for DED0021000 operation");
            }

            // Pre-process
            preProcess(event);

            // Execute business logic
            NewEPlatonEvent result = executeBusinessLogic(event);

            // Post-process
            postProcess(result);

            // Set success
            result.setSuccess();

            logger.info("New DED0021000 operation completed successfully for event: {}", event.getEventId());

            return result;

        } catch (Exception e) {
            logger.error("New DED0021000 operation failed for event: {}", event.getEventId(), e);

            // Set error in event
            event.setError("EDED001", "DED0021000 operation failed: " + e.getMessage());

            if (e instanceof NewEPlatonException) {
                throw (NewEPlatonException) e;
            } else {
                throw NewEPlatonException.systemError("DED0021000 operation error: " + e.getMessage(), e);
            }
        }
    }

    /**
     * Execute business logic
     */
    private NewEPlatonEvent executeBusinessLogic(NewEPlatonEvent event) throws NewEPlatonException {
        logger.debug("Executing DED0021000 business logic for event: {}", event.getEventId());

        // Get request data
        Object requestData = event.getRequest();

        if (requestData instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> data = (Map<String, Object>) requestData;

            // Process DED0021000 specific logic
            String operation = (String) data.get("operation");
            if (operation == null) {
                throw NewEPlatonException.invalidInput("Operation is required");
            }

            // Execute based on operation
            switch (operation.toLowerCase()) {
                case "process":
                    return processDED0021000(data, event);
                case "validate":
                    return validateDED0021000(data, event);
                case "report":
                    return reportDED0021000(data, event);
                default:
                    throw NewEPlatonException.invalidInput("Unknown operation: " + operation);
            }
        }

        throw NewEPlatonException.invalidInput("Request data must be a Map");
    }

    /**
     * Process DED0021000 operation
     */
    private NewEPlatonEvent processDED0021000(Map<String, Object> data, NewEPlatonEvent event) {
        logger.debug("Processing DED0021000 with data: {}", data);

        // Simulate processing
        Map<String, Object> response = new HashMap<>();
        response.put("processed", true);
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("operation", "DED0021000_PROCESS");

        event.setResponse(response);

        logger.info("DED0021000 processed successfully");

        return event;
    }

    /**
     * Validate DED0021000 operation
     */
    private NewEPlatonEvent validateDED0021000(Map<String, Object> data, NewEPlatonEvent event) {
        logger.debug("Validating DED0021000 with data: {}", data);

        // Simulate validation
        Map<String, Object> response = new HashMap<>();
        response.put("validated", true);
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("operation", "DED0021000_VALIDATE");

        event.setResponse(response);

        logger.info("DED0021000 validated successfully");

        return event;
    }

    /**
     * Report DED0021000 operation
     */
    private NewEPlatonEvent reportDED0021000(Map<String, Object> data, NewEPlatonEvent event) {
        logger.debug("Reporting DED0021000 with data: {}", data);

        // Simulate reporting
        Map<String, Object> response = new HashMap<>();
        response.put("reported", true);
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("operation", "DED0021000_REPORT");

        event.setResponse(response);

        logger.info("DED0021000 reported successfully");

        return event;
    }

    /**
     * Validate event
     */
    private boolean validateEvent(NewEPlatonEvent event) {
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
     * Pre-process operation
     */
    private void preProcess(NewEPlatonEvent event) throws NewEPlatonException {
        logger.debug("Pre-processing DED0021000 operation for event: {}", event.getEventId());

        // Set request time if not set
        if (event.getTpsvcInfo().getRequestTime() == null) {
            event.getTpsvcInfo().setRequestTime(LocalDateTime.now());
        }

        // Additional pre-processing logic can be added here
    }

    /**
     * Post-process operation
     */
    private void postProcess(NewEPlatonEvent event) throws NewEPlatonException {
        logger.debug("Post-processing DED0021000 operation for event: {}", event.getEventId());

        // Set response time
        event.getTpsvcInfo().setResponseTime(LocalDateTime.now());

        // Additional post-processing logic can be added here
    }

    /**
     * Get operation information
     */
    public String getOperationInfo() {
        return String.format("Operation: %s, Type: %s, Version: %s", operationName, operationType, operationVersion);
    }

    /**
     * Check if operation is ready
     */
    public boolean isReady() {
        return operationName != null && !operationName.trim().isEmpty();
    }

    // Getters and Setters
    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getOperationVersion() {
        return operationVersion;
    }

    public void setOperationVersion(String operationVersion) {
        this.operationVersion = operationVersion;
    }

    @Override
    public String toString() {
        return "NewDED0021000{" +
                "operationName='" + operationName + '\'' +
                ", operationType='" + operationType + '\'' +
                ", operationVersion='" + operationVersion + '\'' +
                '}';
    }
}