package com.banking.coses.controller;

import com.banking.coses.action.ActionManager;
import com.banking.coses.transfer.CosesCommonDTO;
import com.banking.coses.transfer.CosesEvent;
import com.banking.coses.exception.CosesAppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

/**
 * COSES Framework REST Controller
 * 
 * Provides REST API endpoints for COSES Framework operations.
 */
@RestController
@RequestMapping("/api/coses")
public class CosesFrameworkController {

    private static final Logger logger = LoggerFactory.getLogger(CosesFrameworkController.class);

    @Autowired
    private ActionManager actionManager;

    /**
     * Execute COSES action
     */
    @PostMapping("/execute")
    public ResponseEntity<Map<String, Object>> executeAction(@RequestBody Map<String, Object> request) {
        try {
            String actionName = (String) request.get("actionName");
            Object eventData = request.get("eventData");

            logger.info("COSES execute request received for action: {}", actionName);

            // Validate request
            if (actionName == null || actionName.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(createErrorResponse("Action name is required"));
            }

            // Create event
            CosesEvent event = new CosesEvent(eventData);

            // Set common data if provided
            if (request.containsKey("common")) {
                @SuppressWarnings("unchecked")
                Map<String, Object> commonData = (Map<String, Object>) request.get("common");
                setCommonData(event, commonData);
            }

            // Execute action
            CosesCommonDTO result = actionManager.executeAction(actionName, event);

            // Create response
            Map<String, Object> response = createSuccessResponse(result, event);

            logger.info("COSES execute completed for action: {}", actionName);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("COSES execute failed", e);
            return ResponseEntity.internalServerError().body(createErrorResponse(e.getMessage()));
        }
    }

    /**
     * Get action information
     */
    @GetMapping("/actions/{actionName}")
    public ResponseEntity<Map<String, Object>> getActionInfo(@PathVariable String actionName) {
        try {
            logger.debug("COSES get action info request for: {}", actionName);

            if (!actionManager.hasAction(actionName)) {
                return ResponseEntity.notFound().build();
            }

            Map<String, Object> actionInfo = new HashMap<>();
            actionInfo.put("actionName", actionName);
            actionInfo.put("actionType", actionManager.getActionType(actionName));
            actionInfo.put("exists", true);
            actionInfo.put("timestamp", LocalDateTime.now());

            return ResponseEntity.ok(actionInfo);

        } catch (Exception e) {
            logger.error("COSES get action info failed", e);
            return ResponseEntity.internalServerError().body(createErrorResponse(e.getMessage()));
        }
    }

    /**
     * Get all actions
     */
    @GetMapping("/actions")
    public ResponseEntity<Map<String, Object>> getAllActions() {
        try {
            logger.debug("COSES get all actions request");

            Map<String, Object> response = new HashMap<>();
            response.put("actions", actionManager.getActionNames());
            response.put("totalCount", actionManager.getActionCount());
            response.put("statistics", actionManager.getActionStatistics());
            response.put("timestamp", LocalDateTime.now());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("COSES get all actions failed", e);
            return ResponseEntity.internalServerError().body(createErrorResponse(e.getMessage()));
        }
    }

    /**
     * Register new action
     */
    @PostMapping("/actions")
    public ResponseEntity<Map<String, Object>> registerAction(@RequestBody Map<String, Object> request) {
        try {
            String actionName = (String) request.get("actionName");
            String actionType = (String) request.get("actionType");

            logger.info("COSES register action request for: {}", actionName);

            // Validate request
            if (actionName == null || actionName.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(createErrorResponse("Action name is required"));
            }

            // Check if action already exists
            if (actionManager.hasAction(actionName)) {
                return ResponseEntity.badRequest().body(createErrorResponse("Action already exists: " + actionName));
            }

            // For now, we'll return an error since dynamic action registration
            // would require more complex implementation
            return ResponseEntity.badRequest().body(createErrorResponse("Dynamic action registration not supported"));

        } catch (Exception e) {
            logger.error("COSES register action failed", e);
            return ResponseEntity.internalServerError().body(createErrorResponse(e.getMessage()));
        }
    }

    /**
     * Remove action
     */
    @DeleteMapping("/actions/{actionName}")
    public ResponseEntity<Map<String, Object>> removeAction(@PathVariable String actionName) {
        try {
            logger.info("COSES remove action request for: {}", actionName);

            if (!actionManager.hasAction(actionName)) {
                return ResponseEntity.notFound().build();
            }

            actionManager.removeAction(actionName);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Action removed: " + actionName);
            response.put("timestamp", LocalDateTime.now());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("COSES remove action failed", e);
            return ResponseEntity.internalServerError().body(createErrorResponse(e.getMessage()));
        }
    }

    /**
     * Test COSES framework
     */
    @PostMapping("/test")
    public ResponseEntity<Map<String, Object>> testFramework() {
        try {
            logger.info("COSES test request received");

            // Create test event
            Map<String, Object> testData = new HashMap<>();
            testData.put("test", true);
            testData.put("message", "COSES Framework Test");
            testData.put("timestamp", LocalDateTime.now().toString());

            CosesEvent testEvent = new CosesEvent(testData);

            // Try to execute a test action (if available)
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "COSES Framework is operational");
            response.put("event", testEvent);
            response.put("actionCount", actionManager.getActionCount());
            response.put("timestamp", LocalDateTime.now());

            logger.info("COSES test completed successfully");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("COSES test failed", e);
            return ResponseEntity.internalServerError().body(createErrorResponse(e.getMessage()));
        }
    }

    /**
     * Get framework status
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        try {
            logger.debug("COSES status request");

            Map<String, Object> status = new HashMap<>();
            status.put("operational", true);
            status.put("actionCount", actionManager.getActionCount());
            status.put("valid", actionManager.isValid());
            status.put("timestamp", LocalDateTime.now());

            return ResponseEntity.ok(status);

        } catch (Exception e) {
            logger.error("COSES status request failed", e);
            return ResponseEntity.internalServerError().body(createErrorResponse(e.getMessage()));
        }
    }

    /**
     * Set common data from request
     */
    private void setCommonData(CosesEvent event, Map<String, Object> commonData) {
        if (commonData.containsKey("userId")) {
            event.getCommon().setUserId((String) commonData.get("userId"));
        }
        if (commonData.containsKey("sessionId")) {
            event.getCommon().setSessionId((String) commonData.get("sessionId"));
        }
        if (commonData.containsKey("bankCode")) {
            event.getCommon().setBankCode((String) commonData.get("bankCode"));
        }
        if (commonData.containsKey("branchCode")) {
            event.getCommon().setBranchCode((String) commonData.get("branchCode"));
        }
        if (commonData.containsKey("terminalId")) {
            event.getCommon().setTerminalId((String) commonData.get("terminalId"));
        }
        if (commonData.containsKey("operatorId")) {
            event.getCommon().setOperatorId((String) commonData.get("operatorId"));
        }
    }

    /**
     * Create success response
     */
    private Map<String, Object> createSuccessResponse(CosesCommonDTO result, CosesEvent event) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("result", result);
        response.put("event", event);
        response.put("timestamp", LocalDateTime.now());
        return response;
    }

    /**
     * Create error response
     */
    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("error", message);
        response.put("timestamp", LocalDateTime.now());
        return response;
    }
}