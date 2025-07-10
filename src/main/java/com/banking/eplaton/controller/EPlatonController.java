package com.banking.eplaton.controller;

import com.banking.eplaton.service.EPlatonBizDelegateService;
import com.banking.eplaton.transfer.EPlatonEvent;
import com.banking.framework.exception.CosesAppException;
import com.banking.model.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * EPlaton REST Controller
 * 
 * Provides REST API endpoints for EPlaton framework operations.
 */
@RestController
@RequestMapping("/eplaton")
@Tag(name = "EPlaton Framework", description = "EPlaton framework APIs")
public class EPlatonController {

    @Autowired
    private EPlatonBizDelegateService eplatonBizDelegateService;

    /**
     * Execute EPlaton event
     */
    @PostMapping("/execute")
    @Operation(summary = "Execute EPlaton Event", description = "Execute an EPlaton business event")
    public ResponseEntity<ApiResponse<EPlatonEvent>> executeEvent(@Valid @RequestBody EPlatonEvent event) {
        try {
            EPlatonEvent result = eplatonBizDelegateService.execute(event);
            return ResponseEntity.ok(ApiResponse.success("EPlaton event executed successfully", result));
        } catch (CosesAppException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getErrorCode() + ": " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Internal server error: " + e.getMessage()));
        }
    }

    /**
     * Execute CashCard operation
     */
    @PostMapping("/cashcard/{operation}")
    @Operation(summary = "Execute CashCard Operation", description = "Execute a CashCard operation through EPlaton")
    public ResponseEntity<ApiResponse<EPlatonEvent>> executeCashCardOperation(
            @PathVariable String operation,
            @RequestBody Map<String, Object> requestData) {
        try {
            EPlatonEvent event = new EPlatonEvent("CashCardEPlatonAction", "CashCard");
            event.setEventData(requestData);
            requestData.put("operation", operation.toUpperCase());

            EPlatonEvent result = eplatonBizDelegateService.execute(event);
            return ResponseEntity.ok(ApiResponse.success("CashCard operation executed successfully", result));
        } catch (CosesAppException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getErrorCode() + ": " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Internal server error: " + e.getMessage()));
        }
    }

    /**
     * Get cache statistics
     */
    @GetMapping("/cache/statistics")
    @Operation(summary = "Get Cache Statistics", description = "Get EPlaton action cache statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getCacheStatistics() {
        try {
            Map<String, Object> stats = eplatonBizDelegateService.getCacheStatistics();
            return ResponseEntity.ok(ApiResponse.success(stats));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error getting cache statistics: " + e.getMessage()));
        }
    }

    /**
     * Clear action cache
     */
    @DeleteMapping("/cache")
    @Operation(summary = "Clear Action Cache", description = "Clear the EPlaton action cache")
    public ResponseEntity<ApiResponse<Void>> clearActionCache() {
        try {
            eplatonBizDelegateService.clearActionCache();
            return ResponseEntity.ok(ApiResponse.success("Action cache cleared successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error clearing cache: " + e.getMessage()));
        }
    }

    /**
     * Health check
     */
    @GetMapping("/health")
    @Operation(summary = "EPlaton Health Check", description = "Check EPlaton framework health")
    public ResponseEntity<ApiResponse<Map<String, Object>>> healthCheck() {
        try {
            Map<String, Object> health = Map.of(
                    "status", "UP",
                    "framework", "EPlaton",
                    "version", "2.0.0",
                    "timestamp", System.currentTimeMillis());
            return ResponseEntity.ok(ApiResponse.success(health));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(ApiResponse.error("EPlaton framework is down: " + e.getMessage()));
        }
    }
}