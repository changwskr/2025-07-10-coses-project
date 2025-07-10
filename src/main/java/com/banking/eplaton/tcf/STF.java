package com.banking.eplaton.tcf;

import com.banking.eplaton.transfer.EPlatonEvent;
import com.banking.eplaton.transfer.EPlatonCommonDTO;
import com.banking.eplaton.transfer.TPSVCINFODTO;
import com.banking.eplaton.exception.EPlatonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;

/**
 * STF (Service Transaction Framework) Implementation
 * 
 * Handles service transaction processing in the EPlaton framework.
 * This replaces the legacy STF with Spring Boot features.
 */
@Component
@Transactional
public class STF extends AbstractTCF implements ISTF {

    private static final Logger logger = LoggerFactory.getLogger(STF.class);

    private Object stfTxInfo;
    private boolean transactionStarted = false;

    public STF() {
        super();
    }

    @Override
    public EPlatonEvent execute(EPlatonEvent event) {
        try {
            logger.info("STF execution started for event: {}", event.getEventId());

            // Validate event
            if (!validate(event)) {
                throw EPlatonException.invalidEvent("STF validation failed");
            }

            // Pre-process
            preProcess(event);

            // Execute STF logic
            EPlatonEvent result = executeSTFLogic(event);

            // Post-process
            postProcess(result);

            logger.info("STF execution completed for event: {}", event.getEventId());

            return result;

        } catch (Exception e) {
            logger.error("STF execution failed for event: {}", event.getEventId(), e);
            handleSTFError(event, e);
            throw new RuntimeException("STF execution error", e);
        }
    }

    @Override
    public Object getSTF_SPtxinfo() {
        return stfTxInfo;
    }

    @Override
    public boolean validate(EPlatonEvent event) {
        if (!validateEvent(event)) {
            return false;
        }

        // Additional STF-specific validation
        EPlatonCommonDTO common = event.getCommon();
        TPSVCINFODTO tpsvc = event.getTpsvcInfo();

        if (common.getBankCode() == null || common.getBankCode().trim().isEmpty()) {
            logger.error("Bank code is required");
            return false;
        }

        if (common.getBranchCode() == null || common.getBranchCode().trim().isEmpty()) {
            logger.error("Branch code is required");
            return false;
        }

        if (tpsvc.getSystemName() == null || tpsvc.getSystemName().trim().isEmpty()) {
            logger.error("System name is required");
            return false;
        }

        return true;
    }

    @Override
    public void preProcess(EPlatonEvent event) {
        logger.debug("STF pre-processing for event: {}", event.getEventId());

        // Set transaction start time
        event.getCommon().setRequestTime(LocalDateTime.now());

        // Generate transaction info
        stfTxInfo = generateTransactionInfo(event);

        // Call custom pre-processing hooks
        try {
            stfActive01(event);
            stfActive02(event);
            stfActive03(event);
        } catch (Exception e) {
            logger.warn("STF pre-processing hooks failed", e);
        }
    }

    @Override
    public void postProcess(EPlatonEvent event) {
        logger.debug("STF post-processing for event: {}", event.getEventId());

        // Set transaction end time
        event.getCommon().setResponseTime(LocalDateTime.now());

        // Update transaction status
        if (event.hasError()) {
            event.getCommon().setStatus("ERROR");
        } else {
            event.getCommon().setStatus("SUCCESS");
        }
    }

    /**
     * Execute STF business logic
     */
    private EPlatonEvent executeSTFLogic(EPlatonEvent event) {
        logger.debug("Executing STF business logic for event: {}", event.getEventId());

        // Start transaction
        startTransaction(event);

        try {
            // Process service transaction
            processServiceTransaction(event);

            // Mark success
            markSuccess(event);

            return event;

        } catch (Exception e) {
            // Mark error and rollback
            markError(event, e);
            rollbackTransaction(event);
            throw e;
        }
    }

    /**
     * Start transaction
     */
    private void startTransaction(EPlatonEvent event) {
        logger.debug("Starting STF transaction for event: {}", event.getEventId());
        transactionStarted = true;

        // Set initial error code
        event.getTpsvcInfo().setErrorCode("IZZ000");
    }

    /**
     * Process service transaction
     */
    private void processServiceTransaction(EPlatonEvent event) {
        logger.debug("Processing service transaction for event: {}", event.getEventId());

        // Validate service parameters
        validateServiceParameters(event);

        // Process service logic
        executeServiceLogic(event);

        // Update transaction info
        updateTransactionInfo(event);
    }

    /**
     * Validate service parameters
     */
    private void validateServiceParameters(EPlatonEvent event) {
        logger.debug("Validating service parameters for event: {}", event.getEventId());

        // Add service-specific validation logic here
        // This is where you would validate business rules, etc.
    }

    /**
     * Execute service logic
     */
    private void executeServiceLogic(EPlatonEvent event) {
        logger.debug("Executing service logic for event: {}", event.getEventId());

        // Add service-specific business logic here
        // This is where the actual service processing would occur
    }

    /**
     * Update transaction info
     */
    private void updateTransactionInfo(EPlatonEvent event) {
        logger.debug("Updating transaction info for event: {}", event.getEventId());

        // Update transaction information
        stfTxInfo = generateTransactionInfo(event);
    }

    /**
     * Mark success
     */
    private void markSuccess(EPlatonEvent event) {
        logger.debug("Marking STF success for event: {}", event.getEventId());

        event.getTpsvcInfo().setErrorCode("IZZ000");
        event.getTpsvcInfo().setStatus("SUCCESS");
    }

    /**
     * Mark error
     */
    private void markError(EPlatonEvent event, Exception e) {
        logger.error("Marking STF error for event: {}", event.getEventId(), e);

        event.getTpsvcInfo().setErrorCode("ESTF001");
        event.getTpsvcInfo().setErrorMessage("STF processing error: " + e.getMessage());
        event.getTpsvcInfo().setStatus("ERROR");
    }

    /**
     * Rollback transaction
     */
    private void rollbackTransaction(EPlatonEvent event) {
        logger.debug("Rolling back STF transaction for event: {}", event.getEventId());

        if (transactionStarted) {
            // Spring will handle the rollback automatically with @Transactional
            transactionStarted = false;
        }
    }

    /**
     * Handle STF error
     */
    private void handleSTFError(EPlatonEvent event, Exception e) {
        logger.error("Handling STF error for event: {}", event.getEventId(), e);

        event.getTpsvcInfo().setErrorCode("ESTF002");
        event.getTpsvcInfo().setErrorMessage("STF execution error: " + e.getMessage());
        event.getTpsvcInfo().setStatus("ERROR");
    }

    /**
     * Generate transaction info
     */
    private Object generateTransactionInfo(EPlatonEvent event) {
        Map<String, Object> info = new HashMap<>();
        info.put("transactionId", event.getTransactionId());
        info.put("eventId", event.getEventId());
        info.put("timestamp", LocalDateTime.now());
        info.put("status", event.getTpsvcInfo().getStatus());
        return info;
    }

    @Override
    protected void doInitialize() throws Exception {
        logger.info("Initializing STF components");
        // Add STF-specific initialization logic
    }

    @Override
    protected void doCleanup() throws Exception {
        logger.info("Cleaning up STF components");
        // Add STF-specific cleanup logic
    }
}