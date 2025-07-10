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
import java.util.Map;
import java.util.HashMap;

/**
 * ETF (End Transaction Framework) Implementation
 * 
 * Handles end transaction processing in the EPlaton framework.
 * This replaces the legacy ETF with Spring Boot features.
 */
@Component
@Transactional
public class ETF extends AbstractTCF implements IETF {

    private static final Logger logger = LoggerFactory.getLogger(ETF.class);

    private Object stfTxInfo;

    public ETF() {
        super();
    }

    public ETF(Object stfTxInfo) {
        super();
        this.stfTxInfo = stfTxInfo;
    }

    @Override
    public EPlatonEvent execute(EPlatonEvent event) {
        try {
            logger.info("ETF execution started for event: {}", event.getEventId());

            // Validate event
            if (!validateEvent(event)) {
                throw EPlatonException.invalidEvent("ETF validation failed");
            }

            // Check transaction status
            if (event.hasError()) {
                logger.warn("Transaction has errors, rolling back for event: {}", event.getEventId());
                rollback(event);
            } else {
                logger.debug("Transaction successful, committing for event: {}", event.getEventId());
                commit(event);
            }

            // Finalize transaction
            finalize(event);

            // Log result
            logResult(event);

            logger.info("ETF execution completed for event: {}", event.getEventId());

            return event;

        } catch (Exception e) {
            logger.error("ETF execution failed for event: {}", event.getEventId(), e);
            handleETFError(event, e);
            throw new RuntimeException("ETF execution error", e);
        }
    }

    @Override
    public void commit(EPlatonEvent event) {
        logger.debug("ETF committing transaction for event: {}", event.getEventId());

        try {
            // Call custom commit hooks
            etfActive01(event);
            etfActive02(event);
            etfActive03(event);

            // Update transaction status
            event.getTpsvcInfo().setStatus("COMMITTED");
            event.getCommon().setStatus("SUCCESS");

            logger.info("ETF transaction committed successfully for event: {}", event.getEventId());

        } catch (Exception e) {
            logger.error("ETF commit failed for event: {}", event.getEventId(), e);
            throw new RuntimeException("ETF commit error", e);
        }
    }

    @Override
    public void rollback(EPlatonEvent event) {
        logger.debug("ETF rolling back transaction for event: {}", event.getEventId());

        try {
            // Update transaction status
            event.getTpsvcInfo().setStatus("ROLLED_BACK");
            event.getCommon().setStatus("ERROR");

            // Spring will handle the actual rollback automatically with @Transactional

            logger.info("ETF transaction rolled back successfully for event: {}", event.getEventId());

        } catch (Exception e) {
            logger.error("ETF rollback failed for event: {}", event.getEventId(), e);
            throw new RuntimeException("ETF rollback error", e);
        }
    }

    @Override
    public void finalize(EPlatonEvent event) {
        logger.debug("ETF finalizing transaction for event: {}", event.getEventId());

        try {
            // Set final response time
            event.getCommon().setResponseTime(LocalDateTime.now());

            // Update final transaction info
            updateFinalTransactionInfo(event);

            logger.debug("ETF transaction finalized for event: {}", event.getEventId());

        } catch (Exception e) {
            logger.error("ETF finalize failed for event: {}", event.getEventId(), e);
            // Don't throw exception during finalize
        }
    }

    @Override
    public void logResult(EPlatonEvent event) {
        logger.debug("ETF logging result for event: {}", event.getEventId());

        try {
            // Log transaction result
            logTransactionResult(event);

            // Log performance metrics
            logPerformanceMetrics(event);

            logger.debug("ETF result logged for event: {}", event.getEventId());

        } catch (Exception e) {
            logger.error("ETF log result failed for event: {}", event.getEventId(), e);
            // Don't throw exception during logging
        }
    }

    /**
     * Handle ETF error
     */
    private void handleETFError(EPlatonEvent event, Exception e) {
        logger.error("Handling ETF error for event: {}", event.getEventId(), e);

        event.getTpsvcInfo().setErrorCode("EETF001");
        event.getTpsvcInfo().setErrorMessage("ETF execution error: " + e.getMessage());
        event.getTpsvcInfo().setStatus("ERROR");
        event.getCommon().setStatus("ERROR");
    }

    /**
     * Update final transaction info
     */
    private void updateFinalTransactionInfo(EPlatonEvent event) {
        if (stfTxInfo instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> txInfo = (Map<String, Object>) stfTxInfo;
            txInfo.put("finalStatus", event.getTpsvcInfo().getStatus());
            txInfo.put("endTime", LocalDateTime.now());
        }
    }

    /**
     * Log transaction result
     */
    private void logTransactionResult(EPlatonEvent event) {
        logger.info("Transaction Result - Event: {}, Status: {}, Error: {}",
                event.getEventId(),
                event.getTpsvcInfo().getStatus(),
                event.getTpsvcInfo().getErrorMessage());
    }

    /**
     * Log performance metrics
     */
    private void logPerformanceMetrics(EPlatonEvent event) {
        LocalDateTime startTime = event.getCommon().getRequestTime();
        LocalDateTime endTime = event.getCommon().getResponseTime();

        if (startTime != null && endTime != null) {
            long duration = java.time.Duration.between(startTime, endTime).toMillis();
            logger.info("Transaction Performance - Event: {}, Duration: {}ms",
                    event.getEventId(), duration);
        }
    }

    /**
     * Set STF transaction info
     */
    public void setStfTxInfo(Object stfTxInfo) {
        this.stfTxInfo = stfTxInfo;
    }

    /**
     * Get STF transaction info
     */
    public Object getStfTxInfo() {
        return stfTxInfo;
    }

    @Override
    protected void doInitialize() throws Exception {
        logger.info("Initializing ETF components");
        // Add ETF-specific initialization logic
    }

    @Override
    protected void doCleanup() throws Exception {
        logger.info("Cleaning up ETF components");
        // Add ETF-specific cleanup logic
    }
}