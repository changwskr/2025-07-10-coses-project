package com.banking.kdb.oversea.framework.transaction.service;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.foundation.logej.LOGEJ;
import com.banking.kdb.oversea.foundation.utility.CommonUtil;
import com.banking.kdb.oversea.framework.constant.FrameworkConstants;
import com.banking.kdb.oversea.framework.transaction.constant.TransactionConstants;
import com.banking.kdb.oversea.framework.transaction.dao.TransactionDAO;
import com.banking.kdb.oversea.framework.transaction.delegate.TransactionDelegate;
import com.banking.kdb.oversea.framework.transaction.helper.TransactionHelper;
import com.banking.kdb.oversea.framework.transaction.model.Transaction;
import com.banking.kdb.oversea.framework.transaction.tcf.TransactionControlFramework;
import com.banking.kdb.oversea.framework.transaction.tpmutil.TPMUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Transaction Service for KDB Oversea Framework
 * 
 * Provides high-level transaction processing services.
 */
@Service
@Transactional
public class TransactionService {

    private static final FoundationLogger logger = FoundationLogger.getLogger(TransactionService.class);

    @Autowired
    private TransactionDAO transactionDAO;

    @Autowired
    private TransactionDelegate transactionDelegate;

    @Autowired
    private TransactionControlFramework transactionControlFramework;

    /**
     * Create a new transaction
     */
    public Transaction createTransaction(Transaction transaction) {
        logger.info("Creating transaction - Type: {}, Amount: {}, Currency: {}",
                transaction.getTransactionType(), transaction.getAmount(), transaction.getCurrency());

        // Generate transaction ID if not provided
        if (CommonUtil.isNullOrEmpty(transaction.getTransactionId())) {
            transaction.setTransactionId(TransactionHelper.generateTransactionId());
        }

        // Start TPM monitoring
        TPMUtil.startTransactionMonitoring(transaction.getTransactionId());

        try {
            // Create transaction using delegate
            Transaction createdTransaction = transactionDelegate.createTransaction(transaction);

            // Update TPM monitoring
            TPMUtil.updateTransactionMonitoring(transaction.getTransactionId(), "CREATED",
                    FrameworkConstants.TRANSACTION_STATUS_INITIATED);

            // Start transaction control framework processing
            transactionControlFramework.startTransaction(createdTransaction);

            logger.info("Transaction created successfully - ID: {}", createdTransaction.getTransactionId());
            return createdTransaction;

        } catch (Exception e) {
            // Record error in TPM
            TPMUtil.recordTransactionError(transaction.getTransactionId(),
                    FrameworkConstants.ERROR_CODE_SYSTEM_ERROR, e.getMessage());

            // Complete monitoring with error
            TPMUtil.completeTransactionMonitoring(transaction.getTransactionId(),
                    FrameworkConstants.TRANSACTION_STATUS_FAILED);

            logger.error("Failed to create transaction - Error: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create transaction: " + e.getMessage(), e);
        }
    }

    /**
     * Process a transaction
     */
    public Transaction processTransaction(String transactionId) {
        logger.info("Processing transaction - ID: {}", transactionId);

        // Update TPM monitoring
        TPMUtil.updateTransactionMonitoring(transactionId, "PROCESSING",
                FrameworkConstants.TRANSACTION_STATUS_PROCESSING);

        try {
            // Process transaction using control framework
            Transaction processedTransaction = transactionControlFramework.continueTransaction(transactionId,
                    "PROCESSING");

            // Complete TPM monitoring
            TPMUtil.completeTransactionMonitoring(transactionId,
                    FrameworkConstants.TRANSACTION_STATUS_COMPLETED);

            logger.info("Transaction processed successfully - ID: {}", transactionId);
            return processedTransaction;

        } catch (Exception e) {
            // Record error in TPM
            TPMUtil.recordTransactionError(transactionId,
                    FrameworkConstants.ERROR_CODE_PROCESSING_ERROR, e.getMessage());

            // Complete monitoring with error
            TPMUtil.completeTransactionMonitoring(transactionId,
                    FrameworkConstants.TRANSACTION_STATUS_FAILED);

            logger.error("Failed to process transaction - ID: {}, Error: {}", transactionId, e.getMessage(), e);
            throw new RuntimeException("Failed to process transaction: " + e.getMessage(), e);
        }
    }

    /**
     * Find transaction by ID
     */
    public Optional<Transaction> findTransactionById(String transactionId) {
        logger.debug("Finding transaction by ID: {}", transactionId);
        return transactionDelegate.findTransactionById(transactionId);
    }

    /**
     * Find transactions by customer ID
     */
    public List<Transaction> findTransactionsByCustomerId(String customerId) {
        logger.debug("Finding transactions by customer ID: {}", customerId);
        return transactionDelegate.findTransactionsByCustomerId(customerId);
    }

    /**
     * Find transactions by account
     */
    public List<Transaction> findTransactionsByAccount(String accountNumber) {
        logger.debug("Finding transactions by account: {}", accountNumber);
        return transactionDelegate.findTransactionsByAccount(accountNumber);
    }

    /**
     * Find transactions by status
     */
    public List<Transaction> findTransactionsByStatus(String status) {
        logger.debug("Finding transactions by status: {}", status);
        return transactionDelegate.findTransactionsByStatus(status);
    }

    /**
     * Find transactions by type
     */
    public List<Transaction> findTransactionsByType(String transactionType) {
        logger.debug("Finding transactions by type: {}", transactionType);
        return transactionDelegate.findTransactionsByType(transactionType);
    }

    /**
     * Find transactions by amount range
     */
    public List<Transaction> findTransactionsByAmountRange(BigDecimal minAmount, BigDecimal maxAmount) {
        logger.debug("Finding transactions by amount range: {} - {}", minAmount, maxAmount);
        return transactionDelegate.findTransactionsByAmountRange(minAmount, maxAmount);
    }

    /**
     * Find transactions by date range
     */
    public List<Transaction> findTransactionsByDateRange(String startDate, String endDate) {
        logger.debug("Finding transactions by date range: {} - {}", startDate, endDate);
        return transactionDelegate.findTransactionsByDateRange(startDate, endDate);
    }

    /**
     * Update transaction status
     */
    public Transaction updateTransactionStatus(String transactionId, String status, String modifiedBy) {
        logger.info("Updating transaction status - ID: {}, Status: {}", transactionId, status);

        // Update TPM monitoring
        TPMUtil.updateTransactionMonitoring(transactionId, "STATUS_UPDATE", status);

        try {
            Transaction updatedTransaction = transactionDelegate.updateTransactionStatus(transactionId, status,
                    modifiedBy);

            // Log status update
            LOGEJ.logTransactionOperation("STATUS_UPDATE", transactionId,
                    "Status updated to: " + status + " by " + modifiedBy);

            logger.info("Transaction status updated successfully - ID: {}, Status: {}",
                    transactionId, status);
            return updatedTransaction;

        } catch (Exception e) {
            // Record error in TPM
            TPMUtil.recordTransactionError(transactionId,
                    FrameworkConstants.ERROR_CODE_UPDATE_ERROR, e.getMessage());

            logger.error("Failed to update transaction status - ID: {}, Error: {}",
                    transactionId, e.getMessage(), e);
            throw new RuntimeException("Failed to update transaction status: " + e.getMessage(), e);
        }
    }

    /**
     * Authorize transaction
     */
    public Transaction authorizeTransaction(String transactionId, String authorizedBy, String authorizationLevel) {
        logger.info("Authorizing transaction - ID: {}, By: {}, Level: {}",
                transactionId, authorizedBy, authorizationLevel);

        // Update TPM monitoring
        TPMUtil.updateTransactionMonitoring(transactionId, "AUTHORIZATION",
                FrameworkConstants.AUTHORIZATION_STATUS_APPROVED);

        try {
            Transaction authorizedTransaction = transactionDelegate.authorizeTransaction(transactionId, authorizedBy,
                    authorizationLevel);

            // Continue processing in control framework
            transactionControlFramework.continueTransaction(transactionId, "AUTHORIZATION");

            // Log authorization
            LOGEJ.logTransactionOperation("AUTHORIZATION", transactionId,
                    "Transaction authorized by " + authorizedBy + " at level " + authorizationLevel);

            logger.info("Transaction authorized successfully - ID: {}, By: {}",
                    transactionId, authorizedBy);
            return authorizedTransaction;

        } catch (Exception e) {
            // Record error in TPM
            TPMUtil.recordTransactionError(transactionId,
                    FrameworkConstants.ERROR_CODE_AUTHORIZATION_ERROR, e.getMessage());

            logger.error("Failed to authorize transaction - ID: {}, Error: {}",
                    transactionId, e.getMessage(), e);
            throw new RuntimeException("Failed to authorize transaction: " + e.getMessage(), e);
        }
    }

    /**
     * Decline transaction
     */
    public Transaction declineTransaction(String transactionId, String reason, String modifiedBy) {
        logger.info("Declining transaction - ID: {}, Reason: {}", transactionId, reason);

        // Update TPM monitoring
        TPMUtil.updateTransactionMonitoring(transactionId, "DECLINED",
                FrameworkConstants.TRANSACTION_STATUS_FAILED);

        try {
            Transaction declinedTransaction = transactionDelegate.declineTransaction(transactionId, reason, modifiedBy);

            // Complete monitoring
            TPMUtil.completeTransactionMonitoring(transactionId,
                    FrameworkConstants.TRANSACTION_STATUS_FAILED);

            // Log decline
            LOGEJ.logTransactionOperation("DECLINE", transactionId,
                    "Transaction declined: " + reason + " by " + modifiedBy);

            logger.info("Transaction declined successfully - ID: {}, Reason: {}",
                    transactionId, reason);
            return declinedTransaction;

        } catch (Exception e) {
            // Record error in TPM
            TPMUtil.recordTransactionError(transactionId,
                    FrameworkConstants.ERROR_CODE_DECLINE_ERROR, e.getMessage());

            logger.error("Failed to decline transaction - ID: {}, Error: {}",
                    transactionId, e.getMessage(), e);
            throw new RuntimeException("Failed to decline transaction: " + e.getMessage(), e);
        }
    }

    /**
     * Retry failed transaction
     */
    public Transaction retryTransaction(String transactionId, String modifiedBy) {
        logger.info("Retrying transaction - ID: {}", transactionId);

        // Update TPM monitoring
        TPMUtil.updateTransactionMonitoring(transactionId, "RETRY",
                FrameworkConstants.TRANSACTION_STATUS_PENDING);

        try {
            Transaction retriedTransaction = transactionDelegate.retryTransaction(transactionId, modifiedBy);

            // Log retry
            LOGEJ.logTransactionOperation("RETRY", transactionId,
                    "Transaction retry initiated by " + modifiedBy);

            logger.info("Transaction retry successful - ID: {}", transactionId);
            return retriedTransaction;

        } catch (Exception e) {
            // Record error in TPM
            TPMUtil.recordTransactionError(transactionId,
                    FrameworkConstants.ERROR_CODE_RETRY_ERROR, e.getMessage());

            logger.error("Failed to retry transaction - ID: {}, Error: {}",
                    transactionId, e.getMessage(), e);
            throw new RuntimeException("Failed to retry transaction: " + e.getMessage(), e);
        }
    }

    /**
     * Cancel transaction
     */
    public Transaction cancelTransaction(String transactionId, String reason, String modifiedBy) {
        logger.info("Cancelling transaction - ID: {}, Reason: {}", transactionId, reason);

        // Update TPM monitoring
        TPMUtil.updateTransactionMonitoring(transactionId, "CANCELLED",
                FrameworkConstants.TRANSACTION_STATUS_CANCELLED);

        try {
            Transaction cancelledTransaction = transactionDelegate.cancelTransaction(transactionId, reason, modifiedBy);

            // Cancel in control framework
            transactionControlFramework.cancelTransaction(transactionId, reason);

            // Complete monitoring
            TPMUtil.completeTransactionMonitoring(transactionId,
                    FrameworkConstants.TRANSACTION_STATUS_CANCELLED);

            // Log cancellation
            LOGEJ.logTransactionOperation("CANCEL", transactionId,
                    "Transaction cancelled: " + reason + " by " + modifiedBy);

            logger.info("Transaction cancelled successfully - ID: {}, Reason: {}",
                    transactionId, reason);
            return cancelledTransaction;

        } catch (Exception e) {
            // Record error in TPM
            TPMUtil.recordTransactionError(transactionId,
                    FrameworkConstants.ERROR_CODE_CANCEL_ERROR, e.getMessage());

            logger.error("Failed to cancel transaction - ID: {}, Error: {}",
                    transactionId, e.getMessage(), e);
            throw new RuntimeException("Failed to cancel transaction: " + e.getMessage(), e);
        }
    }

    /**
     * Validate transaction
     */
    public boolean validateTransaction(Transaction transaction) {
        logger.debug("Validating transaction - ID: {}", transaction.getTransactionId());

        // Update TPM monitoring
        TPMUtil.updateTransactionMonitoring(transaction.getTransactionId(), "VALIDATION",
                FrameworkConstants.VALIDATION_STATUS_PENDING);

        try {
            boolean isValid = transactionDelegate.validateTransaction(transaction);

            // Update TPM monitoring
            TPMUtil.updateTransactionMonitoring(transaction.getTransactionId(), "VALIDATION",
                    isValid ? FrameworkConstants.VALIDATION_STATUS_PASS : FrameworkConstants.VALIDATION_STATUS_FAIL);

            if (!isValid) {
                TPMUtil.recordTransactionError(transaction.getTransactionId(),
                        FrameworkConstants.ERROR_CODE_VALIDATION_ERROR, "Transaction validation failed");
            }

            logger.debug("Transaction validation completed - ID: {}, Valid: {}",
                    transaction.getTransactionId(), isValid);
            return isValid;

        } catch (Exception e) {
            // Record error in TPM
            TPMUtil.recordTransactionError(transaction.getTransactionId(),
                    FrameworkConstants.ERROR_CODE_VALIDATION_ERROR, e.getMessage());

            logger.error("Transaction validation failed - ID: {}, Error: {}",
                    transaction.getTransactionId(), e.getMessage(), e);
            return false;
        }
    }

    /**
     * Check transaction limits
     */
    public boolean checkTransactionLimits(Transaction transaction) {
        logger.debug("Checking transaction limits - ID: {}", transaction.getTransactionId());

        try {
            boolean withinLimits = transactionDelegate.checkTransactionLimits(transaction);

            if (!withinLimits) {
                TPMUtil.recordTransactionWarning(transaction.getTransactionId(),
                        FrameworkConstants.WARNING_CODE_LIMIT_EXCEEDED, "Transaction limits exceeded");
            }

            logger.debug("Transaction limits check completed - ID: {}, Within Limits: {}",
                    transaction.getTransactionId(), withinLimits);
            return withinLimits;

        } catch (Exception e) {
            // Record error in TPM
            TPMUtil.recordTransactionError(transaction.getTransactionId(),
                    FrameworkConstants.ERROR_CODE_LIMIT_CHECK_ERROR, e.getMessage());

            logger.error("Transaction limits check failed - ID: {}, Error: {}",
                    transaction.getTransactionId(), e.getMessage(), e);
            return false;
        }
    }

    /**
     * Calculate transaction fees
     */
    public BigDecimal calculateTransactionFees(Transaction transaction) {
        logger.debug("Calculating transaction fees - ID: {}", transaction.getTransactionId());

        try {
            BigDecimal fees = transactionDelegate.calculateTransactionFees(transaction);

            logger.debug("Transaction fees calculated - ID: {}, Fees: {}",
                    transaction.getTransactionId(), fees);
            return fees;

        } catch (Exception e) {
            // Record error in TPM
            TPMUtil.recordTransactionError(transaction.getTransactionId(),
                    FrameworkConstants.ERROR_CODE_FEE_CALCULATION_ERROR, e.getMessage());

            logger.error("Transaction fee calculation failed - ID: {}, Error: {}",
                    transaction.getTransactionId(), e.getMessage(), e);
            return BigDecimal.ZERO;
        }
    }

    /**
     * Get transaction statistics
     */
    public Object getTransactionStatistics(String customerId, String period) {
        logger.debug("Getting transaction statistics - Customer: {}, Period: {}", customerId, period);
        return transactionDelegate.getTransactionStatistics(customerId, period);
    }

    /**
     * Get transaction summary
     */
    public Object getTransactionSummary(String customerId, String accountNumber, String period) {
        logger.debug("Getting transaction summary - Customer: {}, Account: {}, Period: {}",
                customerId, accountNumber, period);
        return transactionDelegate.getTransactionSummary(customerId, accountNumber, period);
    }

    /**
     * Find pending transactions
     */
    public List<Transaction> findPendingTransactions() {
        logger.debug("Finding pending transactions");
        return transactionDelegate.findPendingTransactions();
    }

    /**
     * Find transactions requiring authorization
     */
    public List<Transaction> findTransactionsRequiringAuthorization() {
        logger.debug("Finding transactions requiring authorization");
        return transactionDelegate.findTransactionsRequiringAuthorization();
    }

    /**
     * Find high priority transactions
     */
    public List<Transaction> findHighPriorityTransactions() {
        logger.debug("Finding high priority transactions");
        return transactionDelegate.findHighPriorityTransactions();
    }

    /**
     * Find slow transactions
     */
    public List<Transaction> findSlowTransactions(Long thresholdMs) {
        logger.debug("Finding slow transactions - Threshold: {}ms", thresholdMs);
        return transactionDelegate.findSlowTransactions(thresholdMs);
    }

    /**
     * Find failed transactions
     */
    public List<Transaction> findFailedTransactions() {
        logger.debug("Finding failed transactions");
        return transactionDelegate.findFailedTransactions();
    }

    /**
     * Find retry transactions
     */
    public List<Transaction> findRetryTransactions() {
        logger.debug("Finding retry transactions");
        return transactionDelegate.findRetryTransactions();
    }

    /**
     * Get system statistics
     */
    public Object getSystemStatistics() {
        logger.debug("Getting system statistics");

        // Combine statistics from different components
        Object tcfStats = transactionControlFramework.getSystemStatistics();
        Object tpmStats = TPMUtil.getSystemStatistics();

        // Return combined statistics
        return Map.of(
                "transactionControlFramework", tcfStats,
                "transactionProcessingMonitor", tpmStats,
                "timestamp", LocalDateTime.now());
    }

    /**
     * Get performance summary
     */
    public Object getPerformanceSummary() {
        logger.debug("Getting performance summary");
        return TPMUtil.getPerformanceSummary();
    }

    /**
     * Get transaction monitoring data
     */
    public Object getTransactionMonitoringData(String transactionId) {
        logger.debug("Getting transaction monitoring data - ID: {}", transactionId);

        return Map.of(
                "transactionId", transactionId,
                "monitor", TPMUtil.getTransactionMonitor(transactionId),
                "metrics", TPMUtil.getTransactionMetrics(transactionId),
                "controlState", transactionControlFramework.getTransactionControlState(transactionId));
    }

    /**
     * Get all active transaction monitors
     */
    public Object getAllActiveTransactionMonitors() {
        logger.debug("Getting all active transaction monitors");
        return TPMUtil.getAllActiveTransactionMonitors();
    }

    /**
     * Get all system metrics
     */
    public Object getAllSystemMetrics() {
        logger.debug("Getting all system metrics");
        return TPMUtil.getAllSystemMetrics();
    }

    /**
     * Get all active alerts
     */
    public Object getAllActiveAlerts() {
        logger.debug("Getting all active alerts");
        return TPMUtil.getAllActiveAlerts();
    }

    /**
     * Acknowledge alert
     */
    public void acknowledgeAlert(String alertId, String acknowledgedBy) {
        logger.info("Acknowledging alert - ID: {}, By: {}", alertId, acknowledgedBy);
        TPMUtil.acknowledgeAlert(alertId, acknowledgedBy);
    }

    /**
     * Clear all monitoring data
     */
    public void clearAllMonitoringData() {
        logger.info("Clearing all monitoring data");
        TPMUtil.clearAllMonitoringData();
    }

    /**
     * Reset system statistics
     */
    public void resetSystemStatistics() {
        logger.info("Resetting system statistics");
        TPMUtil.resetSystemStatistics();
        transactionControlFramework.resetSystemStatistics();
    }

    /**
     * Shutdown transaction service
     */
    public void shutdown() {
        logger.info("Shutting down transaction service");

        // Shutdown control framework
        transactionControlFramework.shutdown();

        // Clear monitoring data
        TPMUtil.clearAllMonitoringData();

        logger.info("Transaction service shutdown completed");
    }
}