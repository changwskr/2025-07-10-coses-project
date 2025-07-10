package com.banking.kdb.oversea.foundation.tpmservice;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.foundation.logej.LOGEJ;
import com.banking.kdb.oversea.foundation.utility.CommonUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Transaction Processing Monitor (TPM) Service for KDB Oversea Foundation
 * 
 * Provides transaction monitoring, performance tracking, and system health
 * monitoring.
 */
public class TPMService {

    private static final FoundationLogger logger = FoundationLogger.getLogger(TPMService.class);

    private static final ConcurrentHashMap<String, TransactionMetrics> transactionMetrics = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, SystemMetrics> systemMetrics = new ConcurrentHashMap<>();
    private static final AtomicLong totalTransactions = new AtomicLong(0);
    private static final AtomicLong successfulTransactions = new AtomicLong(0);
    private static final AtomicLong failedTransactions = new AtomicLong(0);

    /**
     * Record transaction start
     */
    public static String startTransaction(String transactionType, String transactionId, String userId) {
        String key = transactionType + "_" + transactionId;

        TransactionMetrics metrics = new TransactionMetrics();
        metrics.setTransactionType(transactionType);
        metrics.setTransactionId(transactionId);
        metrics.setUserId(userId);
        metrics.setStartTime(LocalDateTime.now());
        metrics.setStatus("IN_PROGRESS");

        transactionMetrics.put(key, metrics);
        totalTransactions.incrementAndGet();

        LOGEJ.logTransactionStep("TPM", transactionId, "START",
                String.format("Type: %s, User: %s", transactionType, userId));

        return key;
    }

    /**
     * Record transaction completion
     */
    public static void completeTransaction(String transactionType, String transactionId, String status,
            BigDecimal amount, String details) {
        String key = transactionType + "_" + transactionId;
        TransactionMetrics metrics = transactionMetrics.get(key);

        if (metrics != null) {
            metrics.setEndTime(LocalDateTime.now());
            metrics.setStatus(status);
            metrics.setAmount(amount);
            metrics.setDetails(details);
            metrics.setDuration(calculateDuration(metrics.getStartTime(), metrics.getEndTime()));

            if ("SUCCESS".equals(status)) {
                successfulTransactions.incrementAndGet();
            } else {
                failedTransactions.incrementAndGet();
            }

            LOGEJ.logTransactionStep("TPM", transactionId, "COMPLETE",
                    String.format("Status: %s, Amount: %s, Duration: %dms", status, amount, metrics.getDuration()));

            // Log performance metrics
            LOGEJ.logPerformanceMetric(transactionType + "_duration", String.valueOf(metrics.getDuration()), "ms");
            if (amount != null) {
                LOGEJ.logBusinessMetric(transactionType + "_amount", amount.toString(), "USD");
            }
        }
    }

    /**
     * Record transaction step
     */
    public static void recordTransactionStep(String transactionType, String transactionId, String step,
            String details, long stepDuration) {
        String key = transactionType + "_" + transactionId;
        TransactionMetrics metrics = transactionMetrics.get(key);

        if (metrics != null) {
            metrics.addStep(step, stepDuration, details);

            LOGEJ.logTransactionStep("TPM", transactionId, step,
                    String.format("Duration: %dms, Details: %s", stepDuration, details));
        }
    }

    /**
     * Record system metric
     */
    public static void recordSystemMetric(String metricName, String value, String unit) {
        SystemMetrics metrics = systemMetrics.computeIfAbsent(metricName, k -> new SystemMetrics(metricName));
        metrics.setValue(value);
        metrics.setUnit(unit);
        metrics.setLastUpdated(LocalDateTime.now());

        LOGEJ.logPerformanceMetric(metricName, value, unit);
    }

    /**
     * Record business metric
     */
    public static void recordBusinessMetric(String metricName, String value, String context) {
        LOGEJ.logBusinessMetric(metricName, value, context);
    }

    /**
     * Get transaction statistics
     */
    public static TransactionStatistics getTransactionStatistics() {
        TransactionStatistics stats = new TransactionStatistics();
        stats.setTotalTransactions(totalTransactions.get());
        stats.setSuccessfulTransactions(successfulTransactions.get());
        stats.setFailedTransactions(failedTransactions.get());

        if (totalTransactions.get() > 0) {
            double successRate = (double) successfulTransactions.get() / totalTransactions.get() * 100;
            stats.setSuccessRate(successRate);
        }

        // Calculate average duration by transaction type
        transactionMetrics.values().stream()
                .filter(m -> m.getDuration() > 0)
                .forEach(m -> {
                    String type = m.getTransactionType();
                    stats.addAverageDuration(type, m.getDuration());
                });

        return stats;
    }

    /**
     * Get transaction metrics by type
     */
    public static java.util.List<TransactionMetrics> getTransactionMetricsByType(String transactionType) {
        return transactionMetrics.values().stream()
                .filter(m -> transactionType.equals(m.getTransactionType()))
                .toList();
    }

    /**
     * Get system metrics
     */
    public static java.util.Map<String, SystemMetrics> getSystemMetrics() {
        return new ConcurrentHashMap<>(systemMetrics);
    }

    /**
     * Get transaction metrics by user
     */
    public static java.util.List<TransactionMetrics> getTransactionMetricsByUser(String userId) {
        return transactionMetrics.values().stream()
                .filter(m -> userId.equals(m.getUserId()))
                .toList();
    }

    /**
     * Get slow transactions (duration > threshold)
     */
    public static java.util.List<TransactionMetrics> getSlowTransactions(long thresholdMs) {
        return transactionMetrics.values().stream()
                .filter(m -> m.getDuration() > thresholdMs)
                .toList();
    }

    /**
     * Get failed transactions
     */
    public static java.util.List<TransactionMetrics> getFailedTransactions() {
        return transactionMetrics.values().stream()
                .filter(m -> !"SUCCESS".equals(m.getStatus()))
                .toList();
    }

    /**
     * Clear old transaction metrics
     */
    public static void clearOldMetrics(int maxAgeHours) {
        LocalDateTime cutoff = LocalDateTime.now().minusHours(maxAgeHours);

        transactionMetrics.entrySet().removeIf(entry -> {
            TransactionMetrics metrics = entry.getValue();
            return metrics.getStartTime() != null && metrics.getStartTime().isBefore(cutoff);
        });
    }

    /**
     * Get transaction performance report
     */
    public static String getPerformanceReport() {
        TransactionStatistics stats = getTransactionStatistics();
        StringBuilder report = new StringBuilder();

        report.append("=== Transaction Performance Report ===\n");
        report.append(String.format("Total Transactions: %d\n", stats.getTotalTransactions()));
        report.append(String.format("Successful Transactions: %d\n", stats.getSuccessfulTransactions()));
        report.append(String.format("Failed Transactions: %d\n", stats.getFailedTransactions()));
        report.append(String.format("Success Rate: %.2f%%\n", stats.getSuccessRate()));

        report.append("\n=== Average Duration by Transaction Type ===\n");
        stats.getAverageDurations().forEach((type, duration) -> {
            report.append(String.format("%s: %dms\n", type, duration));
        });

        report.append("\n=== System Metrics ===\n");
        systemMetrics.forEach((name, metrics) -> {
            report.append(String.format("%s: %s %s\n", name, metrics.getValue(), metrics.getUnit()));
        });

        return report.toString();
    }

    /**
     * Calculate duration between two timestamps
     */
    private static long calculateDuration(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            return 0;
        }
        return java.time.temporal.ChronoUnit.MILLIS.between(startTime, endTime);
    }

    /**
     * Transaction Metrics class
     */
    public static class TransactionMetrics {
        private String transactionType;
        private String transactionId;
        private String userId;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private String status;
        private BigDecimal amount;
        private String details;
        private long duration;
        private final java.util.List<TransactionStep> steps = new java.util.ArrayList<>();

        // Getters and Setters
        public String getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(String transactionType) {
            this.transactionType = transactionType;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public LocalDateTime getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
        }

        public LocalDateTime getEndTime() {
            return endTime;
        }

        public void setEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public long getDuration() {
            return duration;
        }

        public void setDuration(long duration) {
            this.duration = duration;
        }

        public java.util.List<TransactionStep> getSteps() {
            return steps;
        }

        public void addStep(String stepName, long duration, String details) {
            steps.add(new TransactionStep(stepName, duration, details));
        }
    }

    /**
     * Transaction Step class
     */
    public static class TransactionStep {
        private final String stepName;
        private final long duration;
        private final String details;
        private final LocalDateTime timestamp;

        public TransactionStep(String stepName, long duration, String details) {
            this.stepName = stepName;
            this.duration = duration;
            this.details = details;
            this.timestamp = LocalDateTime.now();
        }

        // Getters
        public String getStepName() {
            return stepName;
        }

        public long getDuration() {
            return duration;
        }

        public String getDetails() {
            return details;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }
    }

    /**
     * System Metrics class
     */
    public static class SystemMetrics {
        private final String metricName;
        private String value;
        private String unit;
        private LocalDateTime lastUpdated;

        public SystemMetrics(String metricName) {
            this.metricName = metricName;
        }

        // Getters and Setters
        public String getMetricName() {
            return metricName;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public LocalDateTime getLastUpdated() {
            return lastUpdated;
        }

        public void setLastUpdated(LocalDateTime lastUpdated) {
            this.lastUpdated = lastUpdated;
        }
    }

    /**
     * Transaction Statistics class
     */
    public static class TransactionStatistics {
        private long totalTransactions;
        private long successfulTransactions;
        private long failedTransactions;
        private double successRate;
        private final java.util.Map<String, Long> averageDurations = new java.util.HashMap<>();

        // Getters and Setters
        public long getTotalTransactions() {
            return totalTransactions;
        }

        public void setTotalTransactions(long totalTransactions) {
            this.totalTransactions = totalTransactions;
        }

        public long getSuccessfulTransactions() {
            return successfulTransactions;
        }

        public void setSuccessfulTransactions(long successfulTransactions) {
            this.successfulTransactions = successfulTransactions;
        }

        public long getFailedTransactions() {
            return failedTransactions;
        }

        public void setFailedTransactions(long failedTransactions) {
            this.failedTransactions = failedTransactions;
        }

        public double getSuccessRate() {
            return successRate;
        }

        public void setSuccessRate(double successRate) {
            this.successRate = successRate;
        }

        public java.util.Map<String, Long> getAverageDurations() {
            return averageDurations;
        }

        public void addAverageDuration(String transactionType, long duration) {
            averageDurations.merge(transactionType, duration, (oldValue, newValue) -> (oldValue + newValue) / 2);
        }
    }

    private TPMService() {
        // Private constructor to prevent instantiation
    }
}