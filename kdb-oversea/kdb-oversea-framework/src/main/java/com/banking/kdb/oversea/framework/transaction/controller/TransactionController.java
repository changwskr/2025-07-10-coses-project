package com.banking.kdb.oversea.framework.transaction.controller;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.framework.constant.FrameworkConstants;
import com.banking.kdb.oversea.framework.transaction.model.Transaction;
import com.banking.kdb.oversea.framework.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Transaction Controller for KDB Oversea Framework
 * 
 * Provides REST endpoints for transaction operations.
 */
@RestController
@RequestMapping("/api/v1/transactions")
@Tag(name = "Transaction Management", description = "Transaction processing and management APIs")
public class TransactionController {

    private static final FoundationLogger logger = FoundationLogger.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    /**
     * Create a new transaction
     */
    @PostMapping
    @Operation(summary = "Create a new transaction", description = "Creates a new transaction with the provided details")
    @PreAuthorize("hasRole('TRANSACTION_USER') or hasRole('TRANSACTION_ADMIN')")
    public ResponseEntity<Transaction> createTransaction(
            @Parameter(description = "Transaction details", required = true) @Valid @RequestBody Transaction transaction) {

        logger.info("Creating transaction - Type: {}, Amount: {}",
                transaction.getTransactionType(), transaction.getAmount());

        try {
            Transaction createdTransaction = transactionService.createTransaction(transaction);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
        } catch (Exception e) {
            logger.error("Failed to create transaction - Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Process a transaction
     */
    @PostMapping("/{transactionId}/process")
    @Operation(summary = "Process a transaction", description = "Processes an existing transaction")
    @PreAuthorize("hasRole('TRANSACTION_USER') or hasRole('TRANSACTION_ADMIN')")
    public ResponseEntity<Transaction> processTransaction(
            @Parameter(description = "Transaction ID", required = true) @PathVariable String transactionId) {

        logger.info("Processing transaction - ID: {}", transactionId);

        try {
            Transaction processedTransaction = transactionService.processTransaction(transactionId);
            return ResponseEntity.ok(processedTransaction);
        } catch (Exception e) {
            logger.error("Failed to process transaction - ID: {}, Error: {}", transactionId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transaction by ID
     */
    @GetMapping("/{transactionId}")
    @Operation(summary = "Get transaction by ID", description = "Retrieves a transaction by its ID")
    @PreAuthorize("hasRole('TRANSACTION_USER') or hasRole('TRANSACTION_ADMIN') or hasRole('TRANSACTION_AUDITOR')")
    public ResponseEntity<Transaction> getTransactionById(
            @Parameter(description = "Transaction ID", required = true) @PathVariable String transactionId) {

        logger.debug("Getting transaction by ID: {}", transactionId);

        try {
            return transactionService.findTransactionById(transactionId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Failed to get transaction - ID: {}, Error: {}", transactionId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transactions by customer ID
     */
    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get transactions by customer ID", description = "Retrieves all transactions for a customer")
    @PreAuthorize("hasRole('TRANSACTION_USER') or hasRole('TRANSACTION_ADMIN') or hasRole('TRANSACTION_AUDITOR')")
    public ResponseEntity<List<Transaction>> getTransactionsByCustomerId(
            @Parameter(description = "Customer ID", required = true) @PathVariable String customerId) {

        logger.debug("Getting transactions by customer ID: {}", customerId);

        try {
            List<Transaction> transactions = transactionService.findTransactionsByCustomerId(customerId);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            logger.error("Failed to get transactions by customer ID - Customer: {}, Error: {}",
                    customerId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transactions by account
     */
    @GetMapping("/account/{accountNumber}")
    @Operation(summary = "Get transactions by account", description = "Retrieves all transactions for an account")
    @PreAuthorize("hasRole('TRANSACTION_USER') or hasRole('TRANSACTION_ADMIN') or hasRole('TRANSACTION_AUDITOR')")
    public ResponseEntity<List<Transaction>> getTransactionsByAccount(
            @Parameter(description = "Account number", required = true) @PathVariable String accountNumber) {

        logger.debug("Getting transactions by account: {}", accountNumber);

        try {
            List<Transaction> transactions = transactionService.findTransactionsByAccount(accountNumber);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            logger.error("Failed to get transactions by account - Account: {}, Error: {}",
                    accountNumber, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transactions by status
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Get transactions by status", description = "Retrieves all transactions with a specific status")
    @PreAuthorize("hasRole('TRANSACTION_USER') or hasRole('TRANSACTION_ADMIN') or hasRole('TRANSACTION_AUDITOR')")
    public ResponseEntity<List<Transaction>> getTransactionsByStatus(
            @Parameter(description = "Transaction status", required = true) @PathVariable String status) {

        logger.debug("Getting transactions by status: {}", status);

        try {
            List<Transaction> transactions = transactionService.findTransactionsByStatus(status);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            logger.error("Failed to get transactions by status - Status: {}, Error: {}",
                    status, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transactions by type
     */
    @GetMapping("/type/{transactionType}")
    @Operation(summary = "Get transactions by type", description = "Retrieves all transactions of a specific type")
    @PreAuthorize("hasRole('TRANSACTION_USER') or hasRole('TRANSACTION_ADMIN') or hasRole('TRANSACTION_AUDITOR')")
    public ResponseEntity<List<Transaction>> getTransactionsByType(
            @Parameter(description = "Transaction type", required = true) @PathVariable String transactionType) {

        logger.debug("Getting transactions by type: {}", transactionType);

        try {
            List<Transaction> transactions = transactionService.findTransactionsByType(transactionType);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            logger.error("Failed to get transactions by type - Type: {}, Error: {}",
                    transactionType, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transactions by amount range
     */
    @GetMapping("/amount-range")
    @Operation(summary = "Get transactions by amount range", description = "Retrieves transactions within an amount range")
    @PreAuthorize("hasRole('TRANSACTION_USER') or hasRole('TRANSACTION_ADMIN') or hasRole('TRANSACTION_AUDITOR')")
    public ResponseEntity<List<Transaction>> getTransactionsByAmountRange(
            @Parameter(description = "Minimum amount", required = true) @RequestParam BigDecimal minAmount,
            @Parameter(description = "Maximum amount", required = true) @RequestParam BigDecimal maxAmount) {

        logger.debug("Getting transactions by amount range: {} - {}", minAmount, maxAmount);

        try {
            List<Transaction> transactions = transactionService.findTransactionsByAmountRange(minAmount, maxAmount);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            logger.error("Failed to get transactions by amount range - Min: {}, Max: {}, Error: {}",
                    minAmount, maxAmount, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transactions by date range
     */
    @GetMapping("/date-range")
    @Operation(summary = "Get transactions by date range", description = "Retrieves transactions within a date range")
    @PreAuthorize("hasRole('TRANSACTION_USER') or hasRole('TRANSACTION_ADMIN') or hasRole('TRANSACTION_AUDITOR')")
    public ResponseEntity<List<Transaction>> getTransactionsByDateRange(
            @Parameter(description = "Start date", required = true) @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date", required = true) @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        logger.debug("Getting transactions by date range: {} - {}", startDate, endDate);

        try {
            List<Transaction> transactions = transactionService.findTransactionsByDateRange(
                    startDate.toString(), endDate.toString());
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            logger.error("Failed to get transactions by date range - Start: {}, End: {}, Error: {}",
                    startDate, endDate, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update transaction status
     */
    @PutMapping("/{transactionId}/status")
    @Operation(summary = "Update transaction status", description = "Updates the status of a transaction")
    @PreAuthorize("hasRole('TRANSACTION_ADMIN') or hasRole('TRANSACTION_AUTHORIZER')")
    public ResponseEntity<Transaction> updateTransactionStatus(
            @Parameter(description = "Transaction ID", required = true) @PathVariable String transactionId,
            @Parameter(description = "New status", required = true) @RequestParam String status,
            @Parameter(description = "Modified by", required = true) @RequestParam String modifiedBy) {

        logger.info("Updating transaction status - ID: {}, Status: {}, By: {}", transactionId, status, modifiedBy);

        try {
            Transaction updatedTransaction = transactionService.updateTransactionStatus(transactionId, status,
                    modifiedBy);
            return ResponseEntity.ok(updatedTransaction);
        } catch (Exception e) {
            logger.error("Failed to update transaction status - ID: {}, Error: {}", transactionId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Authorize transaction
     */
    @PostMapping("/{transactionId}/authorize")
    @Operation(summary = "Authorize transaction", description = "Authorizes a transaction")
    @PreAuthorize("hasRole('TRANSACTION_AUTHORIZER') or hasRole('TRANSACTION_ADMIN')")
    public ResponseEntity<Transaction> authorizeTransaction(
            @Parameter(description = "Transaction ID", required = true) @PathVariable String transactionId,
            @Parameter(description = "Authorized by", required = true) @RequestParam String authorizedBy,
            @Parameter(description = "Authorization level", required = true) @RequestParam String authorizationLevel) {

        logger.info("Authorizing transaction - ID: {}, By: {}, Level: {}",
                transactionId, authorizedBy, authorizationLevel);

        try {
            Transaction authorizedTransaction = transactionService.authorizeTransaction(
                    transactionId, authorizedBy, authorizationLevel);
            return ResponseEntity.ok(authorizedTransaction);
        } catch (Exception e) {
            logger.error("Failed to authorize transaction - ID: {}, Error: {}", transactionId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Decline transaction
     */
    @PostMapping("/{transactionId}/decline")
    @Operation(summary = "Decline transaction", description = "Declines a transaction")
    @PreAuthorize("hasRole('TRANSACTION_AUTHORIZER') or hasRole('TRANSACTION_ADMIN')")
    public ResponseEntity<Transaction> declineTransaction(
            @Parameter(description = "Transaction ID", required = true) @PathVariable String transactionId,
            @Parameter(description = "Reason for decline", required = true) @RequestParam String reason,
            @Parameter(description = "Modified by", required = true) @RequestParam String modifiedBy) {

        logger.info("Declining transaction - ID: {}, Reason: {}", transactionId, reason);

        try {
            Transaction declinedTransaction = transactionService.declineTransaction(transactionId, reason, modifiedBy);
            return ResponseEntity.ok(declinedTransaction);
        } catch (Exception e) {
            logger.error("Failed to decline transaction - ID: {}, Error: {}", transactionId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retry failed transaction
     */
    @PostMapping("/{transactionId}/retry")
    @Operation(summary = "Retry failed transaction", description = "Retries a failed transaction")
    @PreAuthorize("hasRole('TRANSACTION_USER') or hasRole('TRANSACTION_ADMIN')")
    public ResponseEntity<Transaction> retryTransaction(
            @Parameter(description = "Transaction ID", required = true) @PathVariable String transactionId,
            @Parameter(description = "Modified by", required = true) @RequestParam String modifiedBy) {

        logger.info("Retrying transaction - ID: {}", transactionId);

        try {
            Transaction retriedTransaction = transactionService.retryTransaction(transactionId, modifiedBy);
            return ResponseEntity.ok(retriedTransaction);
        } catch (Exception e) {
            logger.error("Failed to retry transaction - ID: {}, Error: {}", transactionId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Cancel transaction
     */
    @PostMapping("/{transactionId}/cancel")
    @Operation(summary = "Cancel transaction", description = "Cancels a transaction")
    @PreAuthorize("hasRole('TRANSACTION_USER') or hasRole('TRANSACTION_ADMIN')")
    public ResponseEntity<Transaction> cancelTransaction(
            @Parameter(description = "Transaction ID", required = true) @PathVariable String transactionId,
            @Parameter(description = "Reason for cancellation", required = true) @RequestParam String reason,
            @Parameter(description = "Modified by", required = true) @RequestParam String modifiedBy) {

        logger.info("Cancelling transaction - ID: {}, Reason: {}", transactionId, reason);

        try {
            Transaction cancelledTransaction = transactionService.cancelTransaction(transactionId, reason, modifiedBy);
            return ResponseEntity.ok(cancelledTransaction);
        } catch (Exception e) {
            logger.error("Failed to cancel transaction - ID: {}, Error: {}", transactionId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Validate transaction
     */
    @PostMapping("/{transactionId}/validate")
    @Operation(summary = "Validate transaction", description = "Validates a transaction")
    @PreAuthorize("hasRole('TRANSACTION_USER') or hasRole('TRANSACTION_ADMIN')")
    public ResponseEntity<Map<String, Object>> validateTransaction(
            @Parameter(description = "Transaction ID", required = true) @PathVariable String transactionId) {

        logger.info("Validating transaction - ID: {}", transactionId);

        try {
            return transactionService.findTransactionById(transactionId)
                    .map(transaction -> {
                        boolean isValid = transactionService.validateTransaction(transaction);
                        return ResponseEntity.ok(Map.of(
                                "transactionId", transactionId,
                                "valid", isValid,
                                "timestamp", LocalDateTime.now()));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Failed to validate transaction - ID: {}, Error: {}", transactionId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get pending transactions
     */
    @GetMapping("/pending")
    @Operation(summary = "Get pending transactions", description = "Retrieves all pending transactions")
    @PreAuthorize("hasRole('TRANSACTION_USER') or hasRole('TRANSACTION_ADMIN') or hasRole('TRANSACTION_AUDITOR')")
    public ResponseEntity<List<Transaction>> getPendingTransactions() {
        logger.debug("Getting pending transactions");

        try {
            List<Transaction> transactions = transactionService.findPendingTransactions();
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            logger.error("Failed to get pending transactions - Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transactions requiring authorization
     */
    @GetMapping("/requiring-authorization")
    @Operation(summary = "Get transactions requiring authorization", description = "Retrieves transactions that require authorization")
    @PreAuthorize("hasRole('TRANSACTION_AUTHORIZER') or hasRole('TRANSACTION_ADMIN')")
    public ResponseEntity<List<Transaction>> getTransactionsRequiringAuthorization() {
        logger.debug("Getting transactions requiring authorization");

        try {
            List<Transaction> transactions = transactionService.findTransactionsRequiringAuthorization();
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            logger.error("Failed to get transactions requiring authorization - Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get high priority transactions
     */
    @GetMapping("/high-priority")
    @Operation(summary = "Get high priority transactions", description = "Retrieves high priority transactions")
    @PreAuthorize("hasRole('TRANSACTION_USER') or hasRole('TRANSACTION_ADMIN') or hasRole('TRANSACTION_AUDITOR')")
    public ResponseEntity<List<Transaction>> getHighPriorityTransactions() {
        logger.debug("Getting high priority transactions");

        try {
            List<Transaction> transactions = transactionService.findHighPriorityTransactions();
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            logger.error("Failed to get high priority transactions - Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get failed transactions
     */
    @GetMapping("/failed")
    @Operation(summary = "Get failed transactions", description = "Retrieves failed transactions")
    @PreAuthorize("hasRole('TRANSACTION_USER') or hasRole('TRANSACTION_ADMIN') or hasRole('TRANSACTION_AUDITOR')")
    public ResponseEntity<List<Transaction>> getFailedTransactions() {
        logger.debug("Getting failed transactions");

        try {
            List<Transaction> transactions = transactionService.findFailedTransactions();
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            logger.error("Failed to get failed transactions - Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get retry transactions
     */
    @GetMapping("/retry")
    @Operation(summary = "Get retry transactions", description = "Retrieves transactions scheduled for retry")
    @PreAuthorize("hasRole('TRANSACTION_USER') or hasRole('TRANSACTION_ADMIN') or hasRole('TRANSACTION_AUDITOR')")
    public ResponseEntity<List<Transaction>> getRetryTransactions() {
        logger.debug("Getting retry transactions");

        try {
            List<Transaction> transactions = transactionService.findRetryTransactions();
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            logger.error("Failed to get retry transactions - Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transaction statistics
     */
    @GetMapping("/statistics")
    @Operation(summary = "Get transaction statistics", description = "Retrieves transaction statistics")
    @PreAuthorize("hasRole('TRANSACTION_ADMIN') or hasRole('TRANSACTION_AUDITOR')")
    public ResponseEntity<Object> getTransactionStatistics(
            @Parameter(description = "Customer ID") @RequestParam(required = false) String customerId,
            @Parameter(description = "Period") @RequestParam(required = false) String period) {

        logger.debug("Getting transaction statistics - Customer: {}, Period: {}", customerId, period);

        try {
            Object statistics = transactionService.getTransactionStatistics(customerId, period);
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            logger.error("Failed to get transaction statistics - Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transaction summary
     */
    @GetMapping("/summary")
    @Operation(summary = "Get transaction summary", description = "Retrieves transaction summary")
    @PreAuthorize("hasRole('TRANSACTION_USER') or hasRole('TRANSACTION_ADMIN') or hasRole('TRANSACTION_AUDITOR')")
    public ResponseEntity<Object> getTransactionSummary(
            @Parameter(description = "Customer ID") @RequestParam(required = false) String customerId,
            @Parameter(description = "Account number") @RequestParam(required = false) String accountNumber,
            @Parameter(description = "Period") @RequestParam(required = false) String period) {

        logger.debug("Getting transaction summary - Customer: {}, Account: {}, Period: {}",
                customerId, accountNumber, period);

        try {
            Object summary = transactionService.getTransactionSummary(customerId, accountNumber, period);
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            logger.error("Failed to get transaction summary - Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get system statistics
     */
    @GetMapping("/system/statistics")
    @Operation(summary = "Get system statistics", description = "Retrieves system statistics")
    @PreAuthorize("hasRole('TRANSACTION_ADMIN')")
    public ResponseEntity<Object> getSystemStatistics() {
        logger.debug("Getting system statistics");

        try {
            Object statistics = transactionService.getSystemStatistics();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            logger.error("Failed to get system statistics - Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get performance summary
     */
    @GetMapping("/system/performance")
    @Operation(summary = "Get performance summary", description = "Retrieves performance summary")
    @PreAuthorize("hasRole('TRANSACTION_ADMIN')")
    public ResponseEntity<Object> getPerformanceSummary() {
        logger.debug("Getting performance summary");

        try {
            Object summary = transactionService.getPerformanceSummary();
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            logger.error("Failed to get performance summary - Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transaction monitoring data
     */
    @GetMapping("/{transactionId}/monitoring")
    @Operation(summary = "Get transaction monitoring data", description = "Retrieves monitoring data for a transaction")
    @PreAuthorize("hasRole('TRANSACTION_ADMIN')")
    public ResponseEntity<Object> getTransactionMonitoringData(
            @Parameter(description = "Transaction ID", required = true) @PathVariable String transactionId) {

        logger.debug("Getting transaction monitoring data - ID: {}", transactionId);

        try {
            Object monitoringData = transactionService.getTransactionMonitoringData(transactionId);
            return ResponseEntity.ok(monitoringData);
        } catch (Exception e) {
            logger.error("Failed to get transaction monitoring data - ID: {}, Error: {}",
                    transactionId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get all active transaction monitors
     */
    @GetMapping("/system/monitors")
    @Operation(summary = "Get all active transaction monitors", description = "Retrieves all active transaction monitors")
    @PreAuthorize("hasRole('TRANSACTION_ADMIN')")
    public ResponseEntity<Object> getAllActiveTransactionMonitors() {
        logger.debug("Getting all active transaction monitors");

        try {
            Object monitors = transactionService.getAllActiveTransactionMonitors();
            return ResponseEntity.ok(monitors);
        } catch (Exception e) {
            logger.error("Failed to get active transaction monitors - Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get all system metrics
     */
    @GetMapping("/system/metrics")
    @Operation(summary = "Get all system metrics", description = "Retrieves all system metrics")
    @PreAuthorize("hasRole('TRANSACTION_ADMIN')")
    public ResponseEntity<Object> getAllSystemMetrics() {
        logger.debug("Getting all system metrics");

        try {
            Object metrics = transactionService.getAllSystemMetrics();
            return ResponseEntity.ok(metrics);
        } catch (Exception e) {
            logger.error("Failed to get system metrics - Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get all active alerts
     */
    @GetMapping("/system/alerts")
    @Operation(summary = "Get all active alerts", description = "Retrieves all active alerts")
    @PreAuthorize("hasRole('TRANSACTION_ADMIN')")
    public ResponseEntity<Object> getAllActiveAlerts() {
        logger.debug("Getting all active alerts");

        try {
            Object alerts = transactionService.getAllActiveAlerts();
            return ResponseEntity.ok(alerts);
        } catch (Exception e) {
            logger.error("Failed to get active alerts - Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Acknowledge alert
     */
    @PostMapping("/system/alerts/{alertId}/acknowledge")
    @Operation(summary = "Acknowledge alert", description = "Acknowledges an alert")
    @PreAuthorize("hasRole('TRANSACTION_ADMIN')")
    public ResponseEntity<Void> acknowledgeAlert(
            @Parameter(description = "Alert ID", required = true) @PathVariable String alertId,
            @Parameter(description = "Acknowledged by", required = true) @RequestParam String acknowledgedBy) {

        logger.info("Acknowledging alert - ID: {}, By: {}", alertId, acknowledgedBy);

        try {
            transactionService.acknowledgeAlert(alertId, acknowledgedBy);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Failed to acknowledge alert - ID: {}, Error: {}", alertId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Performs a health check on the transaction service")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        logger.debug("Performing health check");

        try {
            Map<String, Object> health = Map.of(
                    "status", "UP",
                    "service", "Transaction Service",
                    "timestamp", LocalDateTime.now(),
                    "version", FrameworkConstants.FRAMEWORK_VERSION);
            return ResponseEntity.ok(health);
        } catch (Exception e) {
            logger.error("Health check failed - Error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of(
                    "status", "DOWN",
                    "service", "Transaction Service",
                    "timestamp", LocalDateTime.now(),
                    "error", e.getMessage()));
        }
    }
}