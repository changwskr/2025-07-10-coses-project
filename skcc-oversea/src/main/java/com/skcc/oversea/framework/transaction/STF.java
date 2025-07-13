package com.skcc.oversea.framework.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.skcc.oversea.foundation.logej.LOGEJ;
import com.skcc.oversea.foundation.constant.Constants;
import com.skcc.oversea.framework.constant.FrameworkConstants;

/**
 * Service Transaction Framework (STF) for SKCC Oversea
 * 
 * Handles service-level transaction processing and validation.
 */
@Component
public class STF {

    private static final Logger logger = LoggerFactory.getLogger(STF.class);

    /**
     * Process service transaction
     */
    public Object process(String transactionId, String transactionType, Object request) {
        LOGEJ.startTransaction(transactionId, transactionType, "STF_PROCESS");

        try {
            logger.info("{} Starting STF processing for transaction: {}",
                    FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId);

            // Validate request
            validateRequest(request);

            // Pre-process request
            Object preProcessedRequest = preProcess(transactionId, transactionType, request);

            // Process request
            Object processedRequest = processRequest(transactionId, transactionType, preProcessedRequest);

            // Post-process request
            Object postProcessedRequest = postProcess(transactionId, transactionType, processedRequest);

            LOGEJ.endTransaction(transactionId, Constants.TXN_STATUS_SUCCESS, "STF processing completed successfully");
            logger.info("{} STF processing completed successfully for transaction: {}",
                    FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId);

            return postProcessedRequest;

        } catch (Exception e) {
            LOGEJ.logError(FrameworkConstants.ERROR_CODE_TRANSACTION_ERROR,
                    "STF processing failed: " + e.getMessage(),
                    "STF_PROCESS", e);

            LOGEJ.endTransaction(transactionId, Constants.TXN_STATUS_FAILED,
                    "STF processing failed: " + e.getMessage());

            logger.error("{} STF processing failed for transaction: {} - Error: {}",
                    FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId, e.getMessage(), e);

            throw new RuntimeException("STF processing failed", e);
        }
    }

    /**
     * Validate request
     */
    private void validateRequest(Object request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        logger.debug("{} Request validation passed", FrameworkConstants.LOG_PREFIX_VALIDATION);
    }

    /**
     * Pre-process request
     */
    private Object preProcess(String transactionId, String transactionType, Object request) {
        logger.debug("{} Pre-processing request for transaction: {}",
                FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId);

        // Add transaction metadata
        if (request instanceof TransactionRequest) {
            TransactionRequest txnRequest = (TransactionRequest) request;
            txnRequest.setTransactionId(transactionId);
            txnRequest.setTransactionType(transactionType);
            txnRequest.setTimestamp(System.currentTimeMillis());
        }

        return request;
    }

    /**
     * Process request
     */
    private Object processRequest(String transactionId, String transactionType, Object request) {
        logger.debug("{} Processing request for transaction: {}",
                FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId);

        // Apply business rules and transformations
        // This is where service-level business logic would be applied

        return request;
    }

    /**
     * Post-process request
     */
    private Object postProcess(String transactionId, String transactionType, Object request) {
        logger.debug("{} Post-processing request for transaction: {}",
                FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId);

        // Add processing metadata
        if (request instanceof TransactionRequest) {
            TransactionRequest txnRequest = (TransactionRequest) request;
            txnRequest.setProcessed(true);
            txnRequest.setProcessingTimestamp(System.currentTimeMillis());
        }

        return request;
    }

    /**
     * Transaction Request class
     */
    public static class TransactionRequest {
        private String transactionId;
        private String transactionType;
        private long timestamp;
        private boolean processed;
        private long processingTimestamp;
        private Object data;

        // Getters and Setters
        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public String getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(String transactionType) {
            this.transactionType = transactionType;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public boolean isProcessed() {
            return processed;
        }

        public void setProcessed(boolean processed) {
            this.processed = processed;
        }

        public long getProcessingTimestamp() {
            return processingTimestamp;
        }

        public void setProcessingTimestamp(long processingTimestamp) {
            this.processingTimestamp = processingTimestamp;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}