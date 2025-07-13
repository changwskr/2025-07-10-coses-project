package com.banking.kdb.oversea.framework.transaction.tpmutil;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.foundation.logej.LOGEJ;
import com.banking.kdb.oversea.foundation.utility.CommonUtil;
import com.banking.kdb.oversea.framework.constant.FrameworkConstants;
import com.banking.kdb.oversea.framework.transaction.constant.TransactionConstants;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * TPM (Transaction Processing Monitor) Utility for KDB Oversea Framework
 * 
 * Provides transaction processing monitoring and utility functions.
 */
public class TPMUtil {

    private static final FoundationLogger logger = FoundationLogger.getLogger(TPMUtil.class);

    // Performance monitoring
    private static final AtomicLong totalTransactionsProcessed = new AtomicLong(0);
    private static final AtomicLong totalProcessingTime = new AtomicLong(0);
    private static final AtomicLong totalErrors = new AtomicLong(0);
    private static final AtomicLong totalWarnings = new AtomicLong(0);

    // Transaction tracking
    private static final Map<String, TransactionMonitor> activeTransactions = new ConcurrentHashMap<>();
    private static final Map<String, TransactionMetrics> transactionMetrics = new ConcurrentHashMap<>();

    // System health monitoring
    private static final Map<String, SystemMetric> systemMetrics = new ConcurrentHashMap<>();
    private static final Map<String, Alert> activeAlerts = new ConcurrentHashMap<>();

    // Date formatters
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    private static final DateTimeFormatter LOG_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * Transaction Monitor
     */
    public static class TransactionMonitor {
        private String transactionId;
        private LocalDateTime startTime;
        private LocalDateTime lastUpdateTime;
        private String currentStep;
        private String status;
        private long processingTimeMs;
        private int retryCount;
        private Map<String, Object> context;
        private Map<String, Long> stepTimings;

        public TransactionMonitor(String transactionId) {
            this.transactionId = transactionId;
            this.startTime = LocalDateTime.now();
            this.lastUpdateTime = LocalDateTime.now();
            this.currentStep = "INITIATED";
            this.status = FrameworkConstants.TRANSACTION_STATUS_INITIATED;
            this.processingTimeMs = 0;
            this.retryCount = 0;
            this.context = new ConcurrentHashMap<>();
            this.stepTimings = new ConcurrentHashMap<>();
        }

        // Getters and setters
        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
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

        public String getCurrentStep() {
            return currentStep;
        }

        public void setCurrentStep(String currentStep) {
            this.currentStep = currentStep;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public long getProcessingTimeMs() {
            return processingTimeMs;
        }

        public void setProcessingTimeMs(long processingTimeMs) {
            this.processingTimeMs = processingTimeMs;
        }

        public int getRetryCount() {
            return retryCount;
        }

        public void setRetryCount(int retryCount) {
            this.retryCount = retryCount;
        }

        public Map<String, Object> getContext() {
            return context;
        }

        public void setContext(Map<String, Object> context) {
            this.context = context;
        }

        public Map<String, Long> getStepTimings() {
            return stepTimings;
        }

        public void setStepTimings(Map<String, Long> stepTimings) {
            this.stepTimings = stepTimings;
        }
    }

    /**
     * Transaction Metrics
     */
    public static class TransactionMetrics {
        private String transactionId;
        private long totalProcessingTime;
        private long validationTime;
        private long authorizationTime;
        private long routingTime;
        private long processingTime;
        private int stepCount;
        private int errorCount;
        private int retryCount;
        private Map<String, Long> stepDurations;

        public TransactionMetrics(String transactionId) {
            this.transactionId = transactionId;
            this.totalProcessingTime = 0;
            this.validationTime = 0;
            this.authorizationTime = 0;
            this.routingTime = 0;
            this.processingTime = 0;
            this.stepCount = 0;
            this.errorCount = 0;
            this.retryCount = 0;
            this.stepDurations = new ConcurrentHashMap<>();
        }

        // Getters and setters
        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public long getTotalProcessingTime() {
            return totalProcessingTime;
        }

        public void setTotalProcessingTime(long totalProcessingTime) {
            this.totalProcessingTime = totalProcessingTime;
        }

        public long getValidationTime() {
            return validationTime;
        }

        public void setValidationTime(long validationTime) {
            this.validationTime = validationTime;
        }

        public long getAuthorizationTime() {
            return authorizationTime;
        }

        public void setAuthorizationTime(long authorizationTime) {
            this.authorizationTime = authorizationTime;
        }

        public long getRoutingTime() {
            return routingTime;
        }

        public void setRoutingTime(long routingTime) {
            this.routingTime = routingTime;
        }

        public long getProcessingTime() {
            return processingTime;
        }

        public void setProcessingTime(long processingTime) {
            this.processingTime = processingTime;
        }

        public int getStepCount() {
            return stepCount;
        }

        public void setStepCount(int stepCount) {
            this.stepCount = stepCount;
        }

        public int getErrorCount() {
            return errorCount;
        }

        public void setErrorCount(int errorCount) {
            this.errorCount = errorCount;
        }

        public int getRetryCount() {
            return retryCount;
        }

        public void setRetryCount(int retryCount) {
            this.retryCount = retryCount;
        }

        public Map<String, Long> getStepDurations() {
            return stepDurations;
        }

        public void setStepDurations(Map<String, Long> stepDurations) {
            this.stepDurations = stepDurations;
        }
    }

    /**
     * System Metric
     */
    public static class SystemMetric {
        private String metricName;
        private String metricValue;
        private String metricUnit;
        private LocalDateTime timestamp;
        private String category;

        public SystemMetric(String metricName, String metricValue, String metricUnit, String category) {
            this.metricName = metricName;
            this.metricValue = metricValue;
            this.metricUnit = metricUnit;
            this.category = category;
            this.timestamp = LocalDateTime.now();
        }

        // Getters and setters
        public String getMetricName() {
            return metricName;
        }

        public void setMetricName(String metricName) {
            this.metricName = metricName;
        }

        public String getMetricValue() {
            return metricValue;
        }

        public void setMetricValue(String metricValue) {
            this.metricValue = metricValue;
        }

        public String getMetricUnit() {
            return metricUnit;
        }

        public void setMetricUnit(String metricUnit) {
            this.metricUnit = metricUnit;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }

    /**
     * Alert
     */
    public static class Alert {
        private String alertId;
        private String alertType;
        private String severity;
        private String message;
        private LocalDateTime timestamp;
        private boolean acknowledged;
        private String acknowledgedBy;
        private LocalDateTime acknowledgedAt;

        public Alert(String alertId, String alertType, String severity, String message) {
            this.alertId = alertId;
            this.alertType = alertType;
            this.severity = severity;
            this.message = message;
            this.timestamp = LocalDateTime.now();
            this.acknowledged = false;
        }

        // Getters and setters
        public String getAlertId() {
            return alertId;
        }

        public void setAlertId(String alertId) {
            this.alertId = alertId;
        }

        public String getAlertType() {
            return alertType;
        }

        public void setAlertType(String alertType) {
            this.alertType = alertType;
        }

        public String getSeverity() {
            return severity;
        }

        public void setSeverity(String severity) {
            this.severity = severity;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }

        public boolean isAcknowledged() {
            return acknowledged;
        }

        public void setAcknowledged(boolean acknowledged) {
            this.acknowledged = acknowledged;
        }

        public String getAcknowledgedBy() {
            return acknowledgedBy;
        }

        public void setAcknowledgedBy(String acknowledgedBy) {
            this.acknowledgedBy = acknowledgedBy;
        }

        public LocalDateTime getAcknowledgedAt() {
            return acknowledgedAt;
        }

        public void setAcknowledgedAt(LocalDateTime acknowledgedAt) {
            this.acknowledgedAt = acknowledgedAt;
        }
    }

    /**
     * Start monitoring a transaction
     */
    public static void startTransactionMonitoring(String transactionId) {
        if (CommonUtil.isNullOrEmpty(transactionId)) {
            logger.warn("Cannot start monitoring - Transaction ID is null or empty");
            return;
        }

        logger.debug("Starting transaction monitoring - ID: {}", transactionId);

        TransactionMonitor monitor = new TransactionMonitor(transactionId);
        activeTransactions.put(transactionId, monitor);

        TransactionMetrics metrics = new TransactionMetrics(transactionId);
        transactionMetrics.put(transactionId, metrics);

        // Log monitoring start
        LOGEJ.logTransactionOperation("TPM_START", transactionId, "Transaction monitoring started");

        logger.debug("Transaction monitoring started successfully - ID: {}", transactionId);
    }

    /**
     * Update transaction monitoring
     */
    public static void updateTransactionMonitoring(String transactionId, String step, String status) {
        TransactionMonitor monitor = activeTransactions.get(transactionId);
        if (monitor == null) {
            logger.warn("Transaction monitor not found - ID: {}", transactionId);
            return;
        }

        logger.debug("Updating transaction monitoring - ID: {}, Step: {}, Status: {}",
                transactionId, step, status);

        // Calculate step duration
        long stepDuration = java.time.Duration.between(monitor.getLastUpdateTime(), LocalDateTime.now()).toMillis();

        // Update monitor
        monitor.setCurrentStep(step);
        monitor.setStatus(status);
        monitor.setLastUpdateTime(LocalDateTime.now());
        monitor.getStepTimings().put(step, stepDuration);

        // Update metrics
        TransactionMetrics metrics = transactionMetrics.get(transactionId);
        if (metrics != null) {
            metrics.setStepCount(metrics.getStepCount() + 1);
            metrics.getStepDurations().put(step, stepDuration);

            // Update specific step times
            switch (step) {
                case "VALIDATION":
                    metrics.setValidationTime(metrics.getValidationTime() + stepDuration);
                    break;
                case "AUTHORIZATION":
                    metrics.setAuthorizationTime(metrics.getAuthorizationTime() + stepDuration);
                    break;
                case "ROUTING":
                    metrics.setRoutingTime(metrics.getRoutingTime() + stepDuration);
                    break;
                case "PROCESSING":
                    metrics.setProcessingTime(metrics.getProcessingTime() + stepDuration);
                    break;
            }
        }

        // Log step update
        LOGEJ.logTransactionOperation("TPM_STEP", transactionId,
                "Step updated: " + step + " -> " + status + " (Duration: " + stepDuration + "ms)");

        logger.debug("Transaction monitoring updated successfully - ID: {}, Step: {}, Duration: {}ms",
                transactionId, step, stepDuration);
    }

    /**
     * Complete transaction monitoring
     */
    public static void completeTransactionMonitoring(String transactionId, String finalStatus) {
        TransactionMonitor monitor = activeTransactions.remove(transactionId);
        if (monitor == null) {
            logger.warn("Transaction monitor not found for completion - ID: {}", transactionId);
            return;
        }

        logger.debug("Completing transaction monitoring - ID: {}, Status: {}", transactionId, finalStatus);

        // Calculate total processing time
        long totalProcessingTime = java.time.Duration.between(monitor.getStartTime(), LocalDateTime.now()).toMillis();
        monitor.setProcessingTimeMs(totalProcessingTime);

        // Update final metrics
        TransactionMetrics metrics = transactionMetrics.get(transactionId);
        if (metrics != null) {
            metrics.setTotalProcessingTime(totalProcessingTime);
            metrics.setRetryCount(monitor.getRetryCount());
        }

        // Update global statistics
        TPMUtil.totalTransactionsProcessed.incrementAndGet();
        TPMUtil.totalProcessingTime.addAndGet(totalProcessingTime);

        // Log completion
        LOGEJ.logTransactionOperation("TPM_COMPLETE", transactionId,
                "Transaction monitoring completed - Status: " + finalStatus +
                        ", Total Time: " + totalProcessingTime + "ms");

        logger.info("Transaction monitoring completed - ID: {}, Status: {}, Total Time: {}ms",
                transactionId, finalStatus, totalProcessingTime);
    }

    /**
     * Record transaction error
     */
    public static void recordTransactionError(String transactionId, String errorCode, String errorMessage) {
        logger.warn("Recording transaction error - ID: {}, Error: {} - {}",
                transactionId, errorCode, errorMessage);

        // Update monitor
        TransactionMonitor monitor = activeTransactions.get(transactionId);
        if (monitor != null) {
            monitor.getContext().put("last_error_code", errorCode);
            monitor.getContext().put("last_error_message", errorMessage);
            monitor.setRetryCount(monitor.getRetryCount() + 1);
        }

        // Update metrics
        TransactionMetrics metrics = transactionMetrics.get(transactionId);
        if (metrics != null) {
            metrics.setErrorCount(metrics.getErrorCount() + 1);
        }

        // Update global statistics
        totalErrors.incrementAndGet();

        // Log error
        LOGEJ.logTransactionOperation("TPM_ERROR", transactionId,
                "Transaction error recorded: " + errorCode + " - " + errorMessage);

        logger.warn("Transaction error recorded successfully - ID: {}, Error: {}",
                transactionId, errorCode);
    }

    /**
     * Record transaction warning
     */
    public static void recordTransactionWarning(String transactionId, String warningCode, String warningMessage) {
        logger.warn("Recording transaction warning - ID: {}, Warning: {} - {}",
                transactionId, warningCode, warningMessage);

        // Update monitor
        TransactionMonitor monitor = activeTransactions.get(transactionId);
        if (monitor != null) {
            monitor.getContext().put("last_warning_code", warningCode);
            monitor.getContext().put("last_warning_message", warningMessage);
        }

        // Update global statistics
        totalWarnings.incrementAndGet();

        // Log warning
        LOGEJ.logTransactionOperation("TPM_WARNING", transactionId,
                "Transaction warning recorded: " + warningCode + " - " + warningMessage);

        logger.warn("Transaction warning recorded successfully - ID: {}, Warning: {}",
                transactionId, warningCode);
    }

    /**
     * Get transaction monitor
     */
    public static TransactionMonitor getTransactionMonitor(String transactionId) {
        return activeTransactions.get(transactionId);
    }

    /**
     * Get transaction metrics
     */
    public static TransactionMetrics getTransactionMetrics(String transactionId) {
        return transactionMetrics.get(transactionId);
    }

    /**
     * Get all active transaction monitors
     */
    public static Map<String, TransactionMonitor> getAllActiveTransactionMonitors() {
        return new ConcurrentHashMap<>(activeTransactions);
    }

    /**
     * Get all transaction metrics
     */
    public static Map<String, TransactionMetrics> getAllTransactionMetrics() {
        return new ConcurrentHashMap<>(transactionMetrics);
    }

    /**
     * Update system metric
     */
    public static void updateSystemMetric(String metricName, String metricValue, String metricUnit, String category) {
        logger.debug("Updating system metric - Name: {}, Value: {}, Unit: {}, Category: {}",
                metricName, metricValue, metricUnit, category);

        SystemMetric metric = new SystemMetric(metricName, metricValue, metricUnit, category);
        systemMetrics.put(metricName, metric);

        // Log metric update
        LOGEJ.logPerformanceMetric(metricName, metricValue, metricUnit);
    }

    /**
     * Get system metric
     */
    public static SystemMetric getSystemMetric(String metricName) {
        return systemMetrics.get(metricName);
    }

    /**
     * Get all system metrics
     */
    public static Map<String, SystemMetric> getAllSystemMetrics() {
        return new ConcurrentHashMap<>(systemMetrics);
    }

    /**
     * Create alert
     */
    public static void createAlert(String alertType, String severity, String message) {
        String alertId = generateAlertId();

        logger.warn("Creating alert - ID: {}, Type: {}, Severity: {}, Message: {}",
                alertId, alertType, severity, message);

        Alert alert = new Alert(alertId, alertType, severity, message);
        activeAlerts.put(alertId, alert);

        // Log alert creation
        LOGEJ.logSystemAlert(alertType, severity, message);

        logger.warn("Alert created successfully - ID: {}, Type: {}, Severity: {}",
                alertId, alertType, severity);
    }

    /**
     * Acknowledge alert
     */
    public static void acknowledgeAlert(String alertId, String acknowledgedBy) {
        Alert alert = activeAlerts.get(alertId);
        if (alert == null) {
            logger.warn("Alert not found for acknowledgment - ID: {}", alertId);
            return;
        }

        logger.info("Acknowledging alert - ID: {}, By: {}", alertId, acknowledgedBy);

        alert.setAcknowledged(true);
        alert.setAcknowledgedBy(acknowledgedBy);
        alert.setAcknowledgedAt(LocalDateTime.now());

        // Log alert acknowledgment
        LOGEJ.logSystemAlert("ALERT_ACKNOWLEDGED", "INFO",
                "Alert acknowledged: " + alertId + " by " + acknowledgedBy);

        logger.info("Alert acknowledged successfully - ID: {}, By: {}", alertId, acknowledgedBy);
    }

    /**
     * Get alert
     */
    public static Alert getAlert(String alertId) {
        return activeAlerts.get(alertId);
    }

    /**
     * Get all active alerts
     */
    public static Map<String, Alert> getAllActiveAlerts() {
        return new ConcurrentHashMap<>(activeAlerts);
    }

    /**
     * Get system statistics
     */
    public static Map<String, Object> getSystemStatistics() {
        Map<String, Object> statistics = new HashMap<>();

        statistics.put("totalTransactionsProcessed", totalTransactionsProcessed.get());
        statistics.put("totalProcessingTime", totalProcessingTime.get());
        statistics.put("totalErrors", totalErrors.get());
        statistics.put("totalWarnings", totalWarnings.get());
        statistics.put("activeTransactions", activeTransactions.size());
        statistics.put("systemMetrics", systemMetrics.size());
        statistics.put("activeAlerts", activeAlerts.size());

        // Calculate averages
        long total = totalTransactionsProcessed.get();
        if (total > 0) {
            statistics.put("averageProcessingTime", totalProcessingTime.get() / total);
            statistics.put("errorRate", (double) totalErrors.get() / total * 100);
            statistics.put("warningRate", (double) totalWarnings.get() / total * 100);
        }

        return statistics;
    }

    /**
     * Generate unique alert ID
     */
    public static String generateAlertId() {
        return "ALT" + LocalDateTime.now().format(TIMESTAMP_FORMATTER) +
                UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    /**
     * Generate unique transaction ID
     */
    public static String generateTransactionId() {
        return "TXN" + LocalDateTime.now().format(TIMESTAMP_FORMATTER) +
                UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    /**
     * Generate unique reference number
     */
    public static String generateReferenceNumber() {
        return "REF" + LocalDateTime.now().format(TIMESTAMP_FORMATTER) +
                UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    /**
     * Generate hash for data
     */
    public static String generateHash(String data) {
        if (CommonUtil.isNullOrEmpty(data)) {
            return "";
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Failed to generate hash - Error: {}", e.getMessage(), e);
            return "";
        }
    }

    /**
     * Generate secure random string
     */
    public static String generateSecureRandomString(int length) {
        if (length <= 0) {
            return "";
        }

        SecureRandom random = new SecureRandom();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }

        return sb.toString();
    }

    /**
     * Format timestamp for logging
     */
    public static String formatTimestamp(LocalDateTime timestamp) {
        if (timestamp == null) {
            return "";
        }
        return timestamp.format(LOG_FORMATTER);
    }

    /**
     * Calculate percentage
     */
    public static double calculatePercentage(long part, long total) {
        if (total == 0) {
            return 0.0;
        }
        return (double) part / total * 100;
    }

    /**
     * Format duration in human readable format
     */
    public static String formatDuration(long milliseconds) {
        if (milliseconds < 1000) {
            return milliseconds + "ms";
        } else if (milliseconds < 60000) {
            return String.format("%.2fs", milliseconds / 1000.0);
        } else if (milliseconds < 3600000) {
            return String.format("%.2fm", milliseconds / 60000.0);
        } else {
            return String.format("%.2fh", milliseconds / 3600000.0);
        }
    }

    /**
     * Format amount with currency
     */
    public static String formatAmount(BigDecimal amount, String currency) {
        if (amount == null || CommonUtil.isNullOrEmpty(currency)) {
            return "";
        }
        return String.format("%s %.2f", currency, amount);
    }

    /**
     * Clear all monitoring data
     */
    public static void clearAllMonitoringData() {
        logger.info("Clearing all monitoring data");

        activeTransactions.clear();
        transactionMetrics.clear();
        systemMetrics.clear();
        activeAlerts.clear();

        totalTransactionsProcessed.set(0);
        totalProcessingTime.set(0);
        totalErrors.set(0);
        totalWarnings.set(0);

        logger.info("All monitoring data cleared successfully");
    }

    /**
     * Reset system statistics
     */
    public static void resetSystemStatistics() {
        logger.info("Resetting system statistics");

        totalTransactionsProcessed.set(0);
        totalProcessingTime.set(0);
        totalErrors.set(0);
        totalWarnings.set(0);

        logger.info("System statistics reset successfully");
    }

    /**
     * Get performance summary
     */
    public static Map<String, Object> getPerformanceSummary() {
        Map<String, Object> summary = new HashMap<>();

        long total = totalTransactionsProcessed.get();
        long totalTime = totalProcessingTime.get();
        long errors = totalErrors.get();
        long warnings = totalWarnings.get();

        summary.put("totalTransactions", total);
        summary.put("totalProcessingTime", formatDuration(totalTime));
        summary.put("averageProcessingTime", total > 0 ? formatDuration(totalTime / total) : "0ms");
        summary.put("errorCount", errors);
        summary.put("warningCount", warnings);
        summary.put("errorRate", total > 0 ? String.format("%.2f%%", calculatePercentage(errors, total)) : "0%");
        summary.put("warningRate", total > 0 ? String.format("%.2f%%", calculatePercentage(warnings, total)) : "0%");
        summary.put("activeTransactions", activeTransactions.size());
        summary.put("systemMetrics", systemMetrics.size());
        summary.put("activeAlerts", activeAlerts.size());

        return summary;
    }

    private TPMUtil() {
        // Private constructor to prevent instantiation
    }
}