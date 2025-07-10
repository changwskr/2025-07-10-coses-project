package com.banking.neweplatonframework.controller;

import com.banking.neweplatonframework.transfer.NewEPlatonEvent;
import com.banking.neweplatonframework.business.NewEPlatonBizDelegateService;
import com.banking.neweplatonframework.business.tcf.NewTCF;
import com.banking.neweplatonframework.exception.NewEPlatonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

/**
 * New EPlaton Framework REST Controller
 * 
 * Provides REST API endpoints for New EPlaton Framework operations.
 * This replaces the legacy EJB-based approach with Spring Boot REST services.
 */
@RestController
@RequestMapping("/api/neweplaton")
@CrossOrigin(origins = "*")
public class NewEPlatonFrameworkController {

    private static final Logger logger = LoggerFactory.getLogger(NewEPlatonFrameworkController.class);

    @Autowired
    private NewEPlatonBizDelegateService bizDelegateService;

    @Autowired
    private NewTCF tcf;

    /**
     * Execute business operation
     */
    @PostMapping("/execute")
    public ResponseEntity<Map<String, Object>> executeBusinessOperation(@RequestBody NewEPlatonEvent event) {
        try {
            logger.info("Received business operation request for event: {}", event.getEventId());

            // Execute business operation
            NewEPlatonEvent result = bizDelegateService.execute(event);

            // Prepare response
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("event", result);
            response.put("timestamp", LocalDateTime.now());

            logger.info("Business operation completed successfully for event: {}", event.getEventId());

            return ResponseEntity.ok(response);

        } catch (NewEPlatonException e) {
            logger.error("Business operation failed for event: {}", event.getEventId(), e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", e.getErrorCode());
            errorResponse.put("errorMessage", e.getErrorMessage());
            errorResponse.put("timestamp", LocalDateTime.now());

            return ResponseEntity.status(e.getHttpStatus()).body(errorResponse);

        } catch (Exception e) {
            logger.error("Unexpected error in business operation for event: {}", event.getEventId(), e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", "ESYS001");
            errorResponse.put("errorMessage", "System error: " + e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Execute TCF operation
     */
    @PostMapping("/tcf/execute")
    public ResponseEntity<Map<String, Object>> executeTCF(@RequestBody NewEPlatonEvent event) {
        try {
            logger.info("Received TCF execution request for event: {}", event.getEventId());

            // Execute TCF
            NewEPlatonEvent result = tcf.execute(event);

            // Prepare response
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("event", result);
            response.put("timestamp", LocalDateTime.now());

            logger.info("TCF execution completed successfully for event: {}", event.getEventId());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("TCF execution failed for event: {}", event.getEventId(), e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", "ETCF001");
            errorResponse.put("errorMessage", "TCF execution error: " + e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Get available actions
     */
    @GetMapping("/actions")
    public ResponseEntity<Map<String, Object>> getAvailableActions() {
        try {
            logger.info("Received request for available actions");

            Map<String, Object> actions = bizDelegateService.getAvailableActions();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("actions", actions);
            response.put("timestamp", LocalDateTime.now());

            logger.info("Retrieved {} available actions", actions.size());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Failed to get available actions", e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", "EACT002");
            errorResponse.put("errorMessage", "Failed to get available actions: " + e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Get action statistics
     */
    @GetMapping("/actions/stats")
    public ResponseEntity<Map<String, Object>> getActionStatistics() {
        try {
            logger.info("Received request for action statistics");

            Map<String, Object> stats = bizDelegateService.getActionStatistics();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("statistics", stats);
            response.put("timestamp", LocalDateTime.now());

            logger.info("Retrieved action statistics");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Failed to get action statistics", e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", "ESTAT001");
            errorResponse.put("errorMessage", "Failed to get action statistics: " + e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Get TCF status
     */
    @GetMapping("/tcf/status")
    public ResponseEntity<Map<String, Object>> getTCFStatus() {
        try {
            logger.info("Received request for TCF status");

            Map<String, Object> status = new HashMap<>();
            status.put("ready", tcf.isReady());
            status.put("initialized", tcf.getNewEPlatonEvent() != null);
            status.put("stf", tcf.getStf() != null);
            status.put("btf", tcf.getBtf() != null);
            status.put("etf", tcf.getEtf() != null);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("status", status);
            response.put("timestamp", LocalDateTime.now());

            logger.info("Retrieved TCF status");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Failed to get TCF status", e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", "ETCF002");
            errorResponse.put("errorMessage", "Failed to get TCF status: " + e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Initialize TCF
     */
    @PostMapping("/tcf/initialize")
    public ResponseEntity<Map<String, Object>> initializeTCF() {
        try {
            logger.info("Received request to initialize TCF");

            tcf.initialize();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "TCF initialized successfully");
            response.put("timestamp", LocalDateTime.now());

            logger.info("TCF initialized successfully");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Failed to initialize TCF", e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("errorCode", "ETCF003");
            errorResponse.put("errorMessage", "Failed to initialize TCF: " + e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "New EPlaton Framework");
        health.put("timestamp", LocalDateTime.now());
        health.put("version", "1.0.0");

        return ResponseEntity.ok(health);
    }
}