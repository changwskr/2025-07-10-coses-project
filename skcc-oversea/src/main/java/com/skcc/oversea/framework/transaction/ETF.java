package com.skcc.oversea.framework.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.skcc.oversea.foundation.logej.LOGEJ;
import com.skcc.oversea.foundation.constant.Constants;
import com.skcc.oversea.framework.constant.FrameworkConstants;

/**
 * Error Transaction Framework (ETF) for SKCC Oversea
 * 
 * Handles error processing and recovery for transactions.
 */
@Component
public class ETF {

    private static final Logger logger = LoggerFactory.getLogger(ETF.class);

    /**
     * Process successful transaction
     */
    public Object processSuccess(String transactionId, String transactionType, Object result) {
        LOGEJ.startTransaction(transactionId, transactionType, "ETF_SUCCESS");

        try {
            logger.info("{} Processing successful transaction: {}",
                    FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId);

            // Add success metadata
            if (result instanceof TransactionResponse) {
                TransactionResponse txnResponse = (TransactionResponse) result;
                txnResponse.setSuccess(true);
                txnResponse.setErrorCode(Constants.ERROR_CODE_SUCCESS);
                txnResponse.setErrorMessage("Transaction completed successfully");
                txnResponse.setCompletionTimestamp(System.currentTimeMillis());
            }

            LOGEJ.endTransaction(transactionId, Constants.TXN_STATUS_SUCCESS, "ETF success processing completed");
            logger.info("{} ETF success processing completed for transaction: {}",
                    FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId);

            return result;

        } catch (Exception e) {
            LOGEJ.logError(FrameworkConstants.ERROR_CODE_TRANSACTION_ERROR,
                    "ETF success processing failed: " + e.getMessage(),
                    "ETF_SUCCESS", e);

            LOGEJ.endTransaction(transactionId, Constants.TXN_STATUS_FAILED,
                    "ETF success processing failed: " + e.getMessage());

            logger.error("{} ETF success processing failed for transaction: {} - Error: {}",
                    FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId, e.getMessage(), e);

            return createErrorResponse(transactionId, FrameworkConstants.ERROR_CODE_TRANSACTION_ERROR,
                    "ETF success processing failed: " + e.getMessage());
        }
    }

    /**
     * Process error transaction
     */
    public Object processError(String transactionId, String transactionType, Exception error) {
        LOGEJ.startTransaction(transactionId, transactionType, "ETF_ERROR");

        try {
            logger.warn("{} Processing error transaction: {} - Error: {}",
                    FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId, error.getMessage());

            // Log error details
            LOGEJ.logError(FrameworkConstants.ERROR_CODE_TRANSACTION_ERROR,
                    "Transaction failed: " + error.getMessage(),
                    "ETF_ERROR", error);

            // Create error response
            Object errorResponse = createErrorResponse(transactionId,
                    FrameworkConstants.ERROR_CODE_TRANSACTION_ERROR,
                    error.getMessage());

            // Perform error recovery if needed
            performErrorRecovery(transactionId, transactionType, error);

            LOGEJ.endTransaction(transactionId, Constants.TXN_STATUS_FAILED, "ETF error processing completed");
            logger.info("{} ETF error processing completed for transaction: {}",
                    FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId);

            return errorResponse;

        } catch (Exception e) {
            LOGEJ.logError(FrameworkConstants.ERROR_CODE_TRANSACTION_ERROR,
                    "ETF error processing failed: " + e.getMessage(),
                    "ETF_ERROR", e);

            LOGEJ.endTransaction(transactionId, Constants.TXN_STATUS_FAILED,
                    "ETF error processing failed: " + e.getMessage());

            logger.error("{} ETF error processing failed for transaction: {} - Error: {}",
                    FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId, e.getMessage(), e);

            return createErrorResponse(transactionId, FrameworkConstants.ERROR_CODE_TRANSACTION_ERROR,
                    "ETF error processing failed: " + e.getMessage());
        }
    }

    /**
     * Create error response
     */
    private Object createErrorResponse(String transactionId, String errorCode, String errorMessage) {
        TransactionResponse errorResponse = new TransactionResponse();
        errorResponse.setTransactionId(transactionId);
        errorResponse.setSuccess(false);
        errorResponse.setErrorCode(errorCode);
        errorResponse.setErrorMessage(errorMessage);
        errorResponse.setCompletionTimestamp(System.currentTimeMillis());
        return errorResponse;
    }

    /**
     * Perform error recovery
     */
    private void performErrorRecovery(String transactionId, String transactionType, Exception error) {
        logger.debug("{} Performing error recovery for transaction: {}",
                FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId);

        // Implement error recovery logic here
        // This could include:
        // - Rolling back database transactions
        // - Cleaning up temporary resources
        // - Sending notifications
        // - Logging for audit purposes

        // For now, just log the recovery attempt
        logger.info("{} Error recovery completed for transaction: {}",
                FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId);
    }

    /**
     * Transaction Response class
     */
    public static class TransactionResponse {
        private String transactionId;
        private boolean success;
        private String errorCode;
        private String errorMessage;
        private long completionTimestamp;
        private Object data;

        // Getters and Setters
        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public long getCompletionTimestamp() {
            return completionTimestamp;
        }

        public void setCompletionTimestamp(long completionTimestamp) {
            this.completionTimestamp = completionTimestamp;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}