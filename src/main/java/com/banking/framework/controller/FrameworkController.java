package com.banking.framework.controller;

import com.banking.framework.action.ActionManager;
import com.banking.framework.action.AbstractAction;
import com.banking.framework.exception.CosesAppException;
import com.banking.framework.transfer.CosesCommonDTO;
import com.banking.framework.transfer.CosesEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Framework REST Controller for Coses Framework (Spring Boot Version)
 * 
 * Provides REST API endpoints for framework management and action execution.
 */
@RestController
@RequestMapping("/framework")
@Tag(name = "Framework Management", description = "Coses Framework management APIs")
public class FrameworkController {

    @Autowired
    private ActionManager actionManager;

    /**
     * Execute an action by name
     */
    @PostMapping("/actions/{actionName}/execute")
    @Operation(summary = "Execute Action", description = "Execute a framework action by name")
    public ResponseEntity<CosesCommonDTO> executeAction(
            @PathVariable String actionName,
            @RequestBody CosesEvent event) throws CosesAppException {

        CosesCommonDTO result = actionManager.executeAction(actionName, event);
        return ResponseEntity.ok(result);
    }

    /**
     * Execute an action by type
     */
    @PostMapping("/actions/type/{actionType}/execute")
    @Operation(summary = "Execute Action by Type", description = "Execute a framework action by type")
    public ResponseEntity<CosesCommonDTO> executeActionByType(
            @PathVariable String actionType,
            @RequestBody CosesEvent event) throws CosesAppException {

        CosesCommonDTO result = actionManager.executeActionByType(actionType, event);
        return ResponseEntity.ok(result);
    }

    /**
     * Get all registered actions
     */
    @GetMapping("/actions")
    @Operation(summary = "Get All Actions", description = "Get all registered framework actions")
    public ResponseEntity<Map<String, Object>> getAllActions() {
        Map<String, AbstractAction> actions = actionManager.getAllActions();
        Map<String, Object> response = new HashMap<>();

        Map<String, Object> actionDetails = new HashMap<>();
        for (Map.Entry<String, AbstractAction> entry : actions.entrySet()) {
            AbstractAction action = entry.getValue();
            Map<String, Object> details = new HashMap<>();
            details.put("actionType", action.getActionType());
            details.put("description", action.getDescription());
            details.put("enabled", action.isEnabled());
            details.put("createdAt", action.getCreatedAt());
            actionDetails.put(entry.getKey(), details);
        }

        response.put("actions", actionDetails);
        response.put("totalCount", actions.size());

        return ResponseEntity.ok(response);
    }

    /**
     * Get action by name
     */
    @GetMapping("/actions/{actionName}")
    @Operation(summary = "Get Action", description = "Get a specific framework action by name")
    public ResponseEntity<Map<String, Object>> getAction(@PathVariable String actionName) throws CosesAppException {
        AbstractAction action = actionManager.getAction(actionName);
        if (action == null) {
            throw new CosesAppException("ACTION_NOT_FOUND",
                    "Action not found: " + actionName,
                    org.springframework.http.HttpStatus.NOT_FOUND);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("actionName", action.getActionName());
        response.put("actionType", action.getActionType());
        response.put("description", action.getDescription());
        response.put("enabled", action.isEnabled());
        response.put("createdAt", action.getCreatedAt());

        return ResponseEntity.ok(response);
    }

    /**
     * Get actions by type
     */
    @GetMapping("/actions/type/{actionType}")
    @Operation(summary = "Get Actions by Type", description = "Get all framework actions of a specific type")
    public ResponseEntity<Map<String, Object>> getActionsByType(@PathVariable String actionType) {
        Map<String, AbstractAction> actions = actionManager.getActionsByType(actionType);
        Map<String, Object> response = new HashMap<>();

        Map<String, Object> actionDetails = new HashMap<>();
        for (Map.Entry<String, AbstractAction> entry : actions.entrySet()) {
            AbstractAction action = entry.getValue();
            Map<String, Object> details = new HashMap<>();
            details.put("actionType", action.getActionType());
            details.put("description", action.getDescription());
            details.put("enabled", action.isEnabled());
            details.put("createdAt", action.getCreatedAt());
            actionDetails.put(entry.getKey(), details);
        }

        response.put("actions", actionDetails);
        response.put("actionType", actionType);
        response.put("count", actions.size());

        return ResponseEntity.ok(response);
    }

    /**
     * Enable an action
     */
    @PutMapping("/actions/{actionName}/enable")
    @Operation(summary = "Enable Action", description = "Enable a framework action")
    public ResponseEntity<CosesCommonDTO> enableAction(@PathVariable String actionName) throws CosesAppException {
        actionManager.enableAction(actionName);

        CosesCommonDTO response = new CosesCommonDTO();
        response.markSuccess();
        response.setErrorMessage("Action enabled successfully: " + actionName);

        return ResponseEntity.ok(response);
    }

    /**
     * Disable an action
     */
    @PutMapping("/actions/{actionName}/disable")
    @Operation(summary = "Disable Action", description = "Disable a framework action")
    public ResponseEntity<CosesCommonDTO> disableAction(@PathVariable String actionName) throws CosesAppException {
        actionManager.disableAction(actionName);

        CosesCommonDTO response = new CosesCommonDTO();
        response.markSuccess();
        response.setErrorMessage("Action disabled successfully: " + actionName);

        return ResponseEntity.ok(response);
    }

    /**
     * Get framework statistics
     */
    @GetMapping("/statistics")
    @Operation(summary = "Get Framework Statistics", description = "Get framework statistics and metrics")
    public ResponseEntity<Map<String, Object>> getFrameworkStatistics() {
        Map<String, Object> statistics = actionManager.getActionStatistics();

        Map<String, Object> response = new HashMap<>();
        response.put("statistics", statistics);
        response.put("timestamp", java.time.LocalDateTime.now());
        response.put("frameworkVersion", "2.0.0");

        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    @Operation(summary = "Framework Health Check", description = "Check framework health status")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", java.time.LocalDateTime.now());
        health.put("actionCount", actionManager.getActionCount());
        health.put("frameworkVersion", "2.0.0");

        return ResponseEntity.ok(health);
    }
}