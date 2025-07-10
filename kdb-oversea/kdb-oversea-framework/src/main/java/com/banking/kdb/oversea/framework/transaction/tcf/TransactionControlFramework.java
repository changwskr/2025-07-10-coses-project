package com.banking.kdb.oversea.framework.transaction.tcf;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.foundation.logej.LOGEJ;
import com.banking.kdb.oversea.foundation.utility.CommonUtil;
import com.banking.kdb.oversea.framework.constant.FrameworkConstants;
import com.banking.kdb.oversea.framework.transaction.constant.TransactionConstants;
import com.banking.kdb.oversea.framework.transaction.delegate.TransactionDelegate;
import com.banking.kdb.oversea.framework.transaction.helper.TransactionHelper;
import com.banking.kdb.oversea.framework.transaction.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Transaction Control Framework (TCF) for KDB Oversea Framework
 * 
 * Provides centralized transaction control and management capabilities.
 */
@Component
public class TransactionControlFramework {

    private static final FoundationLogger logger = FoundationLogger.getLogger(TransactionControlFramework.class);

    @Autowired
    private TransactionDelegate transactionDelegate;

    // Transaction control state
    private final AtomicLong transactionCounter = new AtomicLong(0);
    private final Map<String, TransactionControlState> transactionStates = new ConcurrentHashMap<>();
    private final Map<String, TransactionControlRule> controlRules = new ConcurrentHashMap<>();

    // System control flags
    private volatile boolean systemEnabled = true;
    private volatile boolean maintenanceMode = false;
    private volatile boolean emergencyMode = false;

    // Performance monitoring
    private final AtomicLong totalTransactions = new AtomicLong(0);
    private final AtomicLong successfulTransactions = new AtomicLong(0);
    private final AtomicLong failedTransactions = new AtomicLong(0);
    private final AtomicLong totalProcessingTime = new AtomicLong(0);

    /**
     * Transaction Control State
     */
    public static class TransactionControlState {
        private String transactionId;
        private String status;
        private LocalDateTime startTime;
        private LocalDateTime lastUpdateTime;
        private int retryCount;
        private String currentStep;
        private Map<String, Object> context;

        public TransactionControlState(String transactionId) {
            this.transactionId = transactionId;
            this.status = FrameworkConstants.TRANSACTION_STATUS_INITIATED;
            this.startTime = LocalDateTime.now();
            this.lastUpdateTime = LocalDateTime.now();
            this.retryCount = 0;
            this.context = new ConcurrentHashMap<>();
        }

        // Getters and setters
        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public LocalDateTime getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
        }

        public LocalDateTime getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        public int getRetryCount() {
            return retryCount;
        }

        public void setRetryCount(int retryCount) {
            this.retryCount = retryCount;
        }

        public String getCurrentStep() {
            return currentStep;
        }

        public void setCurrentStep(String currentStep) {
            this.currentStep = currentStep;
        }

        public Map<String, Object> getContext() {
            return context;
        }

        public void setContext(Map<String, Object> context) {
            this.context = context;
        }
    }

    /**
     * Transaction Control Rule
     */
    public static class TransactionControlRule {
        private String ruleId;
        private String ruleName;
        private String ruleType;
        private String condition;
        private String action;
        private boolean enabled;
        private int priority;
        private Map<String, Object> parameters;

        public TransactionControlRule(String ruleId, String ruleName, String ruleType) {
            this.ruleId = ruleId;
            this.ruleName = ruleName;
            this.ruleType = ruleType;
            this.enabled = true;
            this.priority = 0;
            this.parameters = new ConcurrentHashMap<>();
        }

        // Getters and setters
        public String getRuleId() {
            return ruleId;
        }

        public void setRuleId(String ruleId) {
            this.ruleId = ruleId;
        }

        public String getRuleName() {
            return ruleName;
        }

        public void setRuleName(String ruleName) {
            this.ruleName = ruleName;
        }

        public String getRuleType() {
            return ruleType;
        }

        public void setRuleType(String ruleType) {
            this.ruleType = ruleType;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public Map<String, Object> getParameters() {
            return parameters;
        }

        public void setParameters(Map<String, Object> parameters) {
            this.parameters = parameters;
        }
    }

    /**
     * Initialize transaction control framework
     */
    public void initialize() {
        logger.info("Initializing Transaction Control Framework");

        // Initialize default control rules
        initializeDefaultRules();

        // Reset counters
        transactionCounter.set(0);
        totalTransactions.set(0);
        successfulTransactions.set(0);
        failedTransactions.set(0);
        totalProcessingTime.set(0);

        // Clear transaction states
        transactionStates.clear();

        logger.info("Transaction Control Framework initialized successfully");
    }

    /**
     * Initialize default control rules
     */
    private void initializeDefaultRules() {
        // Amount limit rule
        TransactionControlRule amountLimitRule = new TransactionControlRule(
                "AMOUNT_LIMIT", "Amount Limit Check", "VALIDATION");
        amountLimitRule.setCondition("amount > 1000000");
        amountLimitRule.setAction("REQUIRE_AUTHORIZATION");
        amountLimitRule.setPriority(10);
        controlRules.put(amountLimitRule.getRuleId(), amountLimitRule);

        // Frequency limit rule
        TransactionControlRule frequencyLimitRule = new TransactionControlRule(
                "FREQUENCY_LIMIT", "Frequency Limit Check", "VALIDATION");
        frequencyLimitRule.setCondition("customer_transaction_count > 10");
        frequencyLimitRule.setAction("REQUIRE_REVIEW");
        frequencyLimitRule.setPriority(8);
        controlRules.put(frequencyLimitRule.getRuleId(), frequencyLimitRule);

        // International transaction rule
        TransactionControlRule internationalRule = new TransactionControlRule(
                "INTERNATIONAL_TRANSACTION", "International Transaction Check", "VALIDATION");
        internationalRule.setCondition("is_international = true");
        internationalRule.setAction("REQUIRE_COMPLIANCE_CHECK");
        internationalRule.setPriority(12);
        controlRules.put(internationalRule.getRuleId(), internationalRule);

        // High-risk transaction rule
        TransactionControlRule highRiskRule = new TransactionControlRule(
                "HIGH_RISK", "High Risk Transaction Check", "VALIDATION");
        highRiskRule.setCondition("risk_score > 80");
        highRiskRule.setAction("REQUIRE_MANUAL_REVIEW");
        highRiskRule.setPriority(15);
        controlRules.put(highRiskRule.getRuleId(), highRiskRule);

        logger.info("Initialized {} default control rules", controlRules.size());
    }

    /**
     * Start transaction processing
     */
    public Transaction startTransaction(Transaction transaction) {
        if (!systemEnabled) {
            throw new IllegalStateException("Transaction Control Framework is disabled");
        }

        if (maintenanceMode) {
            throw new IllegalStateException("System is in maintenance mode");
        }

        if (emergencyMode) {
            throw new IllegalStateException("System is in emergency mode");
        }

        String transactionId = transaction.getTransactionId();
        logger.info("Starting transaction processing - ID: {}", transactionId);

        // Create control state
        TransactionControlState controlState = new TransactionControlState(transactionId);
        transactionStates.put(transactionId, controlState);

        // Increment counters
        transactionCounter.incrementAndGet();
        totalTransactions.incrementAndGet();

        // Apply control rules
        applyControlRules(transaction, controlState);

        // Update control state
        controlState.setStatus(FrameworkConstants.TRANSACTION_STATUS_PROCESSING);
        controlState.setCurrentStep("STARTED");
        controlState.setLastUpdateTime(LocalDateTime.now());

        // Log transaction start
        LOGEJ.logTransactionOperation("TCF_START", transactionId, "Transaction processing started");

        logger.info("Transaction processing started successfully - ID: {}", transactionId);
        return transaction;
    }

    /**
     * Apply control rules to transaction
     */
    private void applyControlRules(Transaction transaction, TransactionControlState controlState) {
        logger.debug("Applying control rules to transaction - ID: {}", transaction.getTransactionId());

        for (TransactionControlRule rule : controlRules.values()) {
            if (!rule.isEnabled()) {
                continue;
            }

            if (evaluateRuleCondition(rule, transaction, controlState)) {
                executeRuleAction(rule, transaction, controlState);
            }
        }
    }

    /**
     * Evaluate rule condition
     */
    private boolean evaluateRuleCondition(TransactionControlRule rule, Transaction transaction,
            TransactionControlState controlState) {
        String condition = rule.getCondition();
        if (CommonUtil.isNullOrEmpty(condition)) {
            return false;
        }

        try {
            switch (rule.getRuleId()) {
                case "AMOUNT_LIMIT":
                    return transaction.getAmount().compareTo(new BigDecimal("1000000")) > 0;

                case "FREQUENCY_LIMIT":
                    // This would typically check against customer transaction history
                    return false;

                case "INTERNATIONAL_TRANSACTION":
                    return TransactionHelper.isInternationalTransaction(transaction);

                case "HIGH_RISK":
                    int riskScore = TransactionHelper.calculateRiskScore(transaction);
                    return riskScore > 80;

                default:
                    return false;
            }
        } catch (Exception e) {
            logger.error("Error evaluating rule condition - Rule: {}, Error: {}",
                    rule.getRuleId(), e.getMessage(), e);
            return false;
        }
    }

    /**
     * Execute rule action
     */
    private void executeRuleAction(TransactionControlRule rule, Transaction transaction,
            TransactionControlState controlState) {
        String action = rule.getAction();
        if (CommonUtil.isNullOrEmpty(action)) {
            return;
        }

        logger.info("Executing rule action - Rule: {}, Action: {}, Transaction: {}",
                rule.getRuleId(), action, transaction.getTransactionId());

        try {
            switch (action) {
                case "REQUIRE_AUTHORIZATION":
                    transaction.setAuthorizationStatus(FrameworkConstants.AUTHORIZATION_STATUS_REQUIRED);
                    controlState.getContext().put("requires_authorization", true);
                    break;

                case "REQUIRE_REVIEW":
                    transaction.setValidationStatus(FrameworkConstants.VALIDATION_STATUS_REVIEW);
                    controlState.getContext().put("requires_review", true);
                    break;

                case "REQUIRE_COMPLIANCE_CHECK":
                    transaction.setValidationStatus(FrameworkConstants.VALIDATION_STATUS_PENDING);
                    controlState.getContext().put("requires_compliance_check", true);
                    break;

                case "REQUIRE_MANUAL_REVIEW":
                    transaction.setValidationStatus(FrameworkConstants.VALIDATION_STATUS_REVIEW);
                    transaction.setAuthorizationStatus(FrameworkConstants.AUTHORIZATION_STATUS_REQUIRED);
                    controlState.getContext().put("requires_manual_review", true);
                    break;

                case "BLOCK_TRANSACTION":
                    transaction.setStatus(FrameworkConstants.TRANSACTION_STATUS_BLOCKED);
                    controlState.setStatus(FrameworkConstants.TRANSACTION_STATUS_BLOCKED);
                    break;

                case "FLAG_TRANSACTION":
                    controlState.getContext().put("flagged", true);
                    break;

                default:
                    logger.warn("Unknown rule action: {}", action);
                    break;
            }

            // Log rule execution
            LOGEJ.logTransactionOperation("RULE_EXECUTION", transaction.getTransactionId(),
                    "Rule executed: " + rule.getRuleId() + " -> " + action);

        } catch (Exception e) {
            logger.error("Error executing rule action - Rule: {}, Action: {}, Error: {}",
                    rule.getRuleId(), action, e.getMessage(), e);
        }
    }

    /**
     * Continue transaction processing
     */
    public Transaction continueTransaction(String transactionId, String step) {
        TransactionControlState controlState = transactionStates.get(transactionId);
        if (controlState == null) {
            throw new IllegalArgumentException("Transaction control state not found: " + transactionId);
        }

        logger.info("Continuing transaction processing - ID: {}, Step: {}", transactionId, step);

        // Update control state
        controlState.setCurrentStep(step);
        controlState.setLastUpdateTime(LocalDateTime.now());

        // Get transaction
        Transaction transaction = transactionDelegate.findTransactionById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found: " + transactionId));

        // Process step
        switch (step) {
            case "VALIDATION":
                processValidationStep(transaction, controlState);
                break;

            case "AUTHORIZATION":
                processAuthorizationStep(transaction, controlState);
                break;

            case "ROUTING":
                processRoutingStep(transaction, controlState);
                break;

            case "PROCESSING":
                processProcessingStep(transaction, controlState);
                break;

            case "COMPLETION":
                processCompletionStep(transaction, controlState);
                break;

            default:
                logger.warn("Unknown processing step: {}", step);
                break;
        }

        return transaction;
    }

    /**
     * Process validation step
     */
    private void processValidationStep(Transaction transaction, TransactionControlState controlState) {
        logger.debug("Processing validation step - ID: {}", transaction.getTransactionId());

        // Perform validation
        boolean isValid = transactionDelegate.validateTransaction(transaction);

        if (isValid) {
            transaction.setValidationStatus(FrameworkConstants.VALIDATION_STATUS_PASS);
            controlState.setStatus(FrameworkConstants.TRANSACTION_STATUS_PENDING);
        } else {
            transaction.setValidationStatus(FrameworkConstants.VALIDATION_STATUS_FAIL);
            controlState.setStatus(FrameworkConstants.TRANSACTION_STATUS_FAILED);
        }

        // Update transaction
        transactionDelegate.updateValidationStatus(transaction.getTransactionId(),
                transaction.getValidationStatus(), transaction.getValidationMessage());
    }

    /**
     * Process authorization step
     */
    private void processAuthorizationStep(Transaction transaction, TransactionControlState controlState) {
        logger.debug("Processing authorization step - ID: {}", transaction.getTransactionId());

        // Check if authorization is required
        Boolean requiresAuthorization = (Boolean) controlState.getContext().get("requires_authorization");

        if (requiresAuthorization != null && requiresAuthorization) {
            // Wait for manual authorization
            transaction.setAuthorizationStatus(FrameworkConstants.AUTHORIZATION_STATUS_PENDING);
        } else {
            // Auto-authorize
            transaction.setAuthorizationStatus(FrameworkConstants.AUTHORIZATION_STATUS_APPROVED);
            transaction.setAuthorizedBy("SYSTEM");
            transaction.setAuthorizationDate(LocalDateTime.now());
        }

        // Update transaction
        transactionDelegate.updateAuthorizationStatus(transaction.getTransactionId(),
                transaction.getAuthorizationStatus(), transaction.getAuthorizedBy());
    }

    /**
     * Process routing step
     */
    private void processRoutingStep(Transaction transaction, TransactionControlState controlState) {
        logger.debug("Processing routing step - ID: {}", transaction.getTransactionId());

        // Determine routing destination
        String routingDestination = transactionDelegate.routeTransaction(transaction);

        transaction.setRoutingStatus(FrameworkConstants.ROUTING_STATUS_COMPLETED);
        transaction.setRoutingType(routingDestination);

        // Update transaction
        transactionDelegate.updateRoutingStatus(transaction.getTransactionId(),
                transaction.getRoutingStatus(), transaction.getRoutingType());
    }

    /**
     * Process processing step
     */
    private void processProcessingStep(Transaction transaction, TransactionControlState controlState) {
        logger.debug("Processing processing step - ID: {}", transaction.getTransactionId());

        long startTime = System.currentTimeMillis();

        try {
            // Process transaction
            Transaction processedTransaction = transactionDelegate.processTransaction(transaction.getTransactionId());

            long processingTime = System.currentTimeMillis() - startTime;

            // Update performance metrics
            totalProcessingTime.addAndGet(processingTime);
            successfulTransactions.incrementAndGet();

            // Update control state
            controlState.setStatus(FrameworkConstants.TRANSACTION_STATUS_COMPLETED);
            controlState.getContext().put("processing_time_ms", processingTime);

        } catch (Exception e) {
            long processingTime = System.currentTimeMillis() - startTime;

            // Update performance metrics
            totalProcessingTime.addAndGet(processingTime);
            failedTransactions.incrementAndGet();

            // Update control state
            controlState.setStatus(FrameworkConstants.TRANSACTION_STATUS_FAILED);
            controlState.getContext().put("error_message", e.getMessage());
            controlState.getContext().put("processing_time_ms", processingTime);

            logger.error("Transaction processing failed - ID: {}, Error: {}",
                    transaction.getTransactionId(), e.getMessage(), e);
        }
    }

    /**
     * Process completion step
     */
    private void processCompletionStep(Transaction transaction, TransactionControlState controlState) {
        logger.debug("Processing completion step - ID: {}", transaction.getTransactionId());

        // Complete transaction
        transactionDelegate.completeTransaction(transaction.getTransactionId(), "SYSTEM");

        // Update control state
        controlState.setStatus(FrameworkConstants.TRANSACTION_STATUS_COMPLETED);
        controlState.setLastUpdateTime(LocalDateTime.now());

        // Log completion
        LOGEJ.logTransactionOperation("TCF_COMPLETE", transaction.getTransactionId(),
                "Transaction processing completed");
    }

    /**
     * Complete transaction processing
     */
    public Transaction completeTransaction(String transactionId) {
        TransactionControlState controlState = transactionStates.get(transactionId);
        if (controlState == null) {
            throw new IllegalArgumentException("Transaction control state not found: " + transactionId);
        }

        logger.info("Completing transaction processing - ID: {}", transactionId);

        // Update control state
        controlState.setStatus(FrameworkConstants.TRANSACTION_STATUS_COMPLETED);
        controlState.setCurrentStep("COMPLETED");
        controlState.setLastUpdateTime(LocalDateTime.now());

        // Get transaction
        Transaction transaction = transactionDelegate.findTransactionById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found: " + transactionId));

        // Log completion
        LOGEJ.logTransactionOperation("TCF_COMPLETE", transactionId, "Transaction processing completed");

        logger.info("Transaction processing completed successfully - ID: {}", transactionId);
        return transaction;
    }

    /**
     * Cancel transaction processing
     */
    public Transaction cancelTransaction(String transactionId, String reason) {
        TransactionControlState controlState = transactionStates.get(transactionId);
        if (controlState == null) {
            throw new IllegalArgumentException("Transaction control state not found: " + transactionId);
        }

        logger.info("Cancelling transaction processing - ID: {}, Reason: {}", transactionId, reason);

        // Update control state
        controlState.setStatus(FrameworkConstants.TRANSACTION_STATUS_CANCELLED);
        controlState.setCurrentStep("CANCELLED");
        controlState.setLastUpdateTime(LocalDateTime.now());
        controlState.getContext().put("cancellation_reason", reason);

        // Cancel transaction
        Transaction transaction = transactionDelegate.cancelTransaction(transactionId, reason, "SYSTEM");

        // Log cancellation
        LOGEJ.logTransactionOperation("TCF_CANCEL", transactionId, "Transaction cancelled: " + reason);

        logger.info("Transaction processing cancelled successfully - ID: {}", transactionId);
        return transaction;
    }

    /**
     * Get transaction control state
     */
    public TransactionControlState getTransactionControlState(String transactionId) {
        return transactionStates.get(transactionId);
    }

    /**
     * Get all transaction control states
     */
    public Map<String, TransactionControlState> getAllTransactionControlStates() {
        return new ConcurrentHashMap<>(transactionStates);
    }

    /**
     * Add control rule
     */
    public void addControlRule(TransactionControlRule rule) {
        if (rule == null || CommonUtil.isNullOrEmpty(rule.getRuleId())) {
            throw new IllegalArgumentException("Invalid control rule");
        }

        logger.info("Adding control rule - ID: {}, Name: {}", rule.getRuleId(), rule.getRuleName());
        controlRules.put(rule.getRuleId(), rule);
    }

    /**
     * Remove control rule
     */
    public void removeControlRule(String ruleId) {
        if (CommonUtil.isNullOrEmpty(ruleId)) {
            throw new IllegalArgumentException("Rule ID cannot be null or empty");
        }

        logger.info("Removing control rule - ID: {}", ruleId);
        controlRules.remove(ruleId);
    }

    /**
     * Get control rule
     */
    public TransactionControlRule getControlRule(String ruleId) {
        return controlRules.get(ruleId);
    }

    /**
     * Get all control rules
     */
    public Map<String, TransactionControlRule> getAllControlRules() {
        return new ConcurrentHashMap<>(controlRules);
    }

    /**
     * Enable/disable system
     */
    public void setSystemEnabled(boolean enabled) {
        logger.info("Setting system enabled: {}", enabled);
        this.systemEnabled = enabled;
    }

    /**
     * Check if system is enabled
     */
    public boolean isSystemEnabled() {
        return systemEnabled;
    }

    /**
     * Set maintenance mode
     */
    public void setMaintenanceMode(boolean maintenanceMode) {
        logger.info("Setting maintenance mode: {}", maintenanceMode);
        this.maintenanceMode = maintenanceMode;
    }

    /**
     * Check if system is in maintenance mode
     */
    public boolean isMaintenanceMode() {
        return maintenanceMode;
    }

    /**
     * Set emergency mode
     */
    public void setEmergencyMode(boolean emergencyMode) {
        logger.info("Setting emergency mode: {}", emergencyMode);
        this.emergencyMode = emergencyMode;
    }

    /**
     * Check if system is in emergency mode
     */
    public boolean isEmergencyMode() {
        return emergencyMode;
    }

    /**
     * Get system statistics
     */
    public Map<String, Object> getSystemStatistics() {
        Map<String, Object> statistics = new ConcurrentHashMap<>();

        statistics.put("totalTransactions", totalTransactions.get());
        statistics.put("successfulTransactions", successfulTransactions.get());
        statistics.put("failedTransactions", failedTransactions.get());
        statistics.put("totalProcessingTime", totalProcessingTime.get());
        statistics.put("activeTransactions", transactionStates.size());
        statistics.put("controlRules", controlRules.size());
        statistics.put("systemEnabled", systemEnabled);
        statistics.put("maintenanceMode", maintenanceMode);
        statistics.put("emergencyMode", emergencyMode);

        // Calculate averages
        long total = totalTransactions.get();
        if (total > 0) {
            statistics.put("averageProcessingTime", totalProcessingTime.get() / total);
            statistics.put("successRate", (double) successfulTransactions.get() / total * 100);
            statistics.put("failureRate", (double) failedTransactions.get() / total * 100);
        }

        return statistics;
    }

    /**
     * Clear transaction control states
     */
    public void clearTransactionControlStates() {
        logger.info("Clearing transaction control states");
        transactionStates.clear();
    }

    /**
     * Reset system statistics
     */
    public void resetSystemStatistics() {
        logger.info("Resetting system statistics");
        totalTransactions.set(0);
        successfulTransactions.set(0);
        failedTransactions.set(0);
        totalProcessingTime.set(0);
    }

    /**
     * Shutdown transaction control framework
     */
    public void shutdown() {
        logger.info("Shutting down Transaction Control Framework");

        // Set system to disabled
        systemEnabled = false;

        // Clear states
        transactionStates.clear();

        // Reset statistics
        resetSystemStatistics();

        logger.info("Transaction Control Framework shutdown completed");
    }
}