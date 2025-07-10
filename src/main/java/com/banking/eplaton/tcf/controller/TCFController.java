package com.banking.eplaton.tcf.controller;

import com.banking.eplaton.tcf.TCF;
import com.banking.eplaton.transfer.EPlatonEvent;
import com.banking.eplaton.transfer.EPlatonCommonDTO;
import com.banking.eplaton.transfer.TPSVCINFODTO;
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
 * TCF (Transaction Control Framework) REST Controller
 * 
 * Provides REST API endpoints for TCF operations.
 */
@RestController
@RequestMapping("/api/tcf")
public class TCFController {

    private static final Logger logger = LoggerFactory.getLogger(TCFController.class);

    @Autowired
    private TCF tcf;

    /**
     * Execute TCF transaction
     */
    @PostMapping("/execute")
    public ResponseEntity<Map<String, Object>> executeTransaction(@RequestBody EPlatonEvent event) {
        try {
            logger.info("TCF execute request received for event: {}", event.getEventId());

            // Validate request
            if (event == null) {
                return ResponseEntity.badRequest().body(createErrorResponse("Event is required"));
            }

            // Execute TCF
            EPlatonEvent result = tcf.execute(event);

            // Create response
            Map<String, Object> response = createSuccessResponse(result);

            logger.info("TCF execute completed for event: {}", event.getEventId());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("TCF execute failed", e);
            return ResponseEntity.internalServerError().body(createErrorResponse(e.getMessage()));
        }
    }

    /**
     * Get TCF status
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        try {
            logger.debug("TCF status request received");

            Map<String, Object> status = new HashMap<>();
            status.put("ready", tcf.isReady());
            status.put("initialized", tcf.isReady());
            status.put("timestamp", LocalDateTime.now());

            return ResponseEntity.ok(status);

        } catch (Exception e) {
            logger.error("TCF status request failed", e);
            return ResponseEntity.internalServerError().body(createErrorResponse(e.getMessage()));
        }
    }

    /**
     * Initialize TCF
     */
    @PostMapping("/initialize")
    public ResponseEntity<Map<String, Object>> initialize() {
        try {
            logger.info("TCF initialize request received");

            tcf.initialize();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "TCF initialized successfully");
            response.put("timestamp", LocalDateTime.now());

            logger.info("TCF initialized successfully");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("TCF initialization failed", e);
            return ResponseEntity.internalServerError().body(createErrorResponse(e.getMessage()));
        }
    }

    /**
     * Cleanup TCF
     */
    @PostMapping("/cleanup")
    public ResponseEntity<Map<String, Object>> cleanup() {
        try {
            logger.info("TCF cleanup request received");

            tcf.cleanup();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "TCF cleaned up successfully");
            response.put("timestamp", LocalDateTime.now());

            logger.info("TCF cleaned up successfully");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("TCF cleanup failed", e);
            return ResponseEntity.internalServerError().body(createErrorResponse(e.getMessage()));
        }
    }

    /**
     * Get current EPlaton event
     */
    @GetMapping("/event")
    public ResponseEntity<Map<String, Object>> getCurrentEvent() {
        try {
            logger.debug("TCF get current event request received");

            EPlatonEvent event = tcf.getEPlatonEvent();

            if (event == null) {
                return ResponseEntity.ok(createErrorResponse("No current event"));
            }

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("event", event);
            response.put("timestamp", LocalDateTime.now());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("TCF get current event failed", e);
            return ResponseEntity.internalServerError().body(createErrorResponse(e.getMessage()));
        }
    }

    /**
     * Test TCF with sample event
     */
    @PostMapping("/test")
    public ResponseEntity<Map<String, Object>> testTCF() {
        try {
            logger.info("TCF test request received");

            // Create test event
            EPlatonEvent testEvent = createTestEvent();

            // Execute TCF
            EPlatonEvent result = tcf.execute(testEvent);

            // Create response
            Map<String, Object> response = createSuccessResponse(result);
            response.put("test", true);

            logger.info("TCF test completed successfully");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("TCF test failed", e);
            return ResponseEntity.internalServerError().body(createErrorResponse(e.getMessage()));
        }
    }

    /**
     * Create success response
     */
    private Map<String, Object> createSuccessResponse(EPlatonEvent event) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
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

    /**
     * Create test event
     */
    private EPlatonEvent createTestEvent() {
        EPlatonEvent event = new EPlatonEvent();
        event.setEventId(UUID.randomUUID().toString());
        event.setTransactionId(UUID.randomUUID().toString());

        // Set common data
        EPlatonCommonDTO common = new EPlatonCommonDTO();
        common.setBankCode("001");
        common.setBranchCode("001");
        common.setRequestTime(LocalDateTime.now());
        event.setCommon(common);

        // Set TPSVC info
        TPSVCINFODTO tpsvc = new TPSVCINFODTO();
        tpsvc.setSystemName("TEST_SYSTEM");
        tpsvc.setActionName("CashCardEPlatonAction");
        tpsvc.setOperationName("CREATE_CARD");
        tpsvc.setErrorCode("IZZ000");
        tpsvc.setStatus("INIT");
        event.setTpsvcInfo(tpsvc);

        return event;
    }
}