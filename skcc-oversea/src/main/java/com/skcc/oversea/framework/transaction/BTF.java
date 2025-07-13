package com.skcc.oversea.framework.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.skcc.oversea.foundation.logej.LOGEJ;
import com.skcc.oversea.foundation.constant.Constants;
import com.skcc.oversea.framework.constant.FrameworkConstants;

/**
 * Business Transaction Framework (BTF) for SKCC Oversea
 * 
 * Handles business logic execution and transaction routing.
 */
@Component
public class BTF {

    private static final Logger logger = LoggerFactory.getLogger(BTF.class);

    @Autowired
    private BusinessActionRegistry actionRegistry;

    /**
     * Process business transaction
     */
    public Object process(String transactionId, String transactionType, Object request) {
        LOGEJ.startTransaction(transactionId, transactionType, "BTF_PROCESS");

        try {
            logger.info("{} Starting BTF processing for transaction: {}",
                    FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId);

            // Validate business request
            validateBusinessRequest(request);

            // Route to appropriate business action
            Object result = routeToAction(transactionId, transactionType, request);

            // Post-process business result
            Object processedResult = postProcessBusinessResult(transactionId, transactionType, result);

            LOGEJ.endTransaction(transactionId, Constants.TXN_STATUS_SUCCESS, "BTF processing completed successfully");
            logger.info("{} BTF processing completed successfully for transaction: {}",
                    FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId);

            return processedResult;

        } catch (Exception e) {
            LOGEJ.logError(FrameworkConstants.ERROR_CODE_TRANSACTION_ERROR,
                    "BTF processing failed: " + e.getMessage(),
                    "BTF_PROCESS", e);

            LOGEJ.endTransaction(transactionId, Constants.TXN_STATUS_FAILED,
                    "BTF processing failed: " + e.getMessage());

            logger.error("{} BTF processing failed for transaction: {} - Error: {}",
                    FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId, e.getMessage(), e);

            throw new RuntimeException("BTF processing failed", e);
        }
    }

    /**
     * Process read-only business transaction
     */
    public Object processReadOnly(String transactionId, String transactionType, Object request) {
        LOGEJ.startTransaction(transactionId, transactionType, "BTF_READ_ONLY");

        try {
            logger.info("{} Starting BTF read-only processing for transaction: {}",
                    FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId);

            // Validate business request
            validateBusinessRequest(request);

            // Route to appropriate read-only business action
            Object result = routeToReadOnlyAction(transactionId, transactionType, request);

            // Post-process business result
            Object processedResult = postProcessBusinessResult(transactionId, transactionType, result);

            LOGEJ.endTransaction(transactionId, Constants.TXN_STATUS_SUCCESS,
                    "BTF read-only processing completed successfully");
            logger.info("{} BTF read-only processing completed successfully for transaction: {}",
                    FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId);

            return processedResult;

        } catch (Exception e) {
            LOGEJ.logError(FrameworkConstants.ERROR_CODE_TRANSACTION_ERROR,
                    "BTF read-only processing failed: " + e.getMessage(),
                    "BTF_READ_ONLY", e);

            LOGEJ.endTransaction(transactionId, Constants.TXN_STATUS_FAILED,
                    "BTF read-only processing failed: " + e.getMessage());

            logger.error("{} BTF read-only processing failed for transaction: {} - Error: {}",
                    FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId, e.getMessage(), e);

            throw new RuntimeException("BTF read-only processing failed", e);
        }
    }

    /**
     * Validate business request
     */
    private void validateBusinessRequest(Object request) {
        if (request == null) {
            throw new IllegalArgumentException("Business request cannot be null");
        }

        logger.debug("{} Business request validation passed", FrameworkConstants.LOG_PREFIX_VALIDATION);
    }

    /**
     * Route to appropriate business action
     */
    private Object routeToAction(String transactionId, String transactionType, Object request) {
        logger.debug("{} Routing to business action for transaction: {} - Type: {}",
                FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId, transactionType);

        // Get business action from registry
        BusinessAction action = actionRegistry.getAction(transactionType);
        if (action == null) {
            throw new RuntimeException("No business action found for transaction type: " + transactionType);
        }

        // Execute business action
        return action.execute(transactionId, request);
    }

    /**
     * Route to appropriate read-only business action
     */
    private Object routeToReadOnlyAction(String transactionId, String transactionType, Object request) {
        logger.debug("{} Routing to read-only business action for transaction: {} - Type: {}",
                FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId, transactionType);

        // Get read-only business action from registry
        BusinessAction action = actionRegistry.getReadOnlyAction(transactionType);
        if (action == null) {
            throw new RuntimeException("No read-only business action found for transaction type: " + transactionType);
        }

        // Execute read-only business action
        return action.execute(transactionId, request);
    }

    /**
     * Post-process business result
     */
    private Object postProcessBusinessResult(String transactionId, String transactionType, Object result) {
        logger.debug("{} Post-processing business result for transaction: {}",
                FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId);

        // Add business processing metadata
        if (result instanceof TransactionResponse) {
            TransactionResponse txnResponse = (TransactionResponse) result;
            txnResponse.setTransactionId(transactionId);
            txnResponse.setBusinessProcessed(true);
            txnResponse.setBusinessProcessingTimestamp(System.currentTimeMillis());
        }

        return result;
    }

    /**
     * Business Action interface
     */
    public interface BusinessAction {
        Object execute(String transactionId, Object request);
    }

    /**
     * Business Action Registry
     */
    @Component
    public static class BusinessActionRegistry {
        private final java.util.Map<String, BusinessAction> actions = new java.util.HashMap<>();
        private final java.util.Map<String, BusinessAction> readOnlyActions = new java.util.HashMap<>();

        public void registerAction(String transactionType, BusinessAction action) {
            actions.put(transactionType, action);
        }

        public void registerReadOnlyAction(String transactionType, BusinessAction action) {
            readOnlyActions.put(transactionType, action);
        }

        public BusinessAction getAction(String transactionType) {
            return actions.get(transactionType);
        }

        public BusinessAction getReadOnlyAction(String transactionType) {
            return readOnlyActions.get(transactionType);
        }
    }

    /**
     * Transaction Response class
     */
    public static class TransactionResponse {
        private String transactionId;
        private boolean businessProcessed;
        private long businessProcessingTimestamp;
        private Object data;

        // Getters and Setters
        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public boolean isBusinessProcessed() {
            return businessProcessed;
        }

        public void setBusinessProcessed(boolean businessProcessed) {
            this.businessProcessed = businessProcessed;
        }

        public long getBusinessProcessingTimestamp() {
            return businessProcessingTimestamp;
        }

        public void setBusinessProcessingTimestamp(long businessProcessingTimestamp) {
            this.businessProcessingTimestamp = businessProcessingTimestamp;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}