package com.banking.kdb.oversea.framework.transaction.helper;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.foundation.utility.CommonUtil;
import com.banking.kdb.oversea.framework.constant.FrameworkConstants;
import com.banking.kdb.oversea.framework.transaction.constant.TransactionConstants;
import com.banking.kdb.oversea.framework.transaction.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Transaction Helper for KDB Oversea Framework
 * 
 * Provides utility methods for transaction operations.
 */
public class TransactionHelper {

    private static final FoundationLogger logger = FoundationLogger.getLogger(TransactionHelper.class);

    // Transaction ID patterns
    private static final Pattern TRANSACTION_ID_PATTERN = Pattern.compile("^[A-Z0-9]{10,20}$");
    private static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile("^[0-9]{10,20}$");
    private static final Pattern CURRENCY_PATTERN = Pattern.compile("^[A-Z]{3}$");
    private static final Pattern CUSTOMER_ID_PATTERN = Pattern.compile("^[A-Z0-9]{5,15}$");

    // Date formatters
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * Generate a unique transaction ID
     */
    public static String generateTransactionId() {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        String random = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "TXN" + timestamp + random;
    }

    /**
     * Generate a unique reference number
     */
    public static String generateReferenceNumber() {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        String random = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return "REF" + timestamp + random;
    }

    /**
     * Generate a unique external reference
     */
    public static String generateExternalReference() {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        String random = UUID.randomUUID().toString().substring(0, 10).toUpperCase();
        return "EXT" + timestamp + random;
    }

    /**
     * Validate transaction ID format
     */
    public static boolean isValidTransactionId(String transactionId) {
        if (CommonUtil.isNullOrEmpty(transactionId)) {
            return false;
        }
        return TRANSACTION_ID_PATTERN.matcher(transactionId).matches();
    }

    /**
     * Validate account number format
     */
    public static boolean isValidAccountNumber(String accountNumber) {
        if (CommonUtil.isNullOrEmpty(accountNumber)) {
            return false;
        }
        return ACCOUNT_NUMBER_PATTERN.matcher(accountNumber).matches();
    }

    /**
     * Validate currency code format
     */
    public static boolean isValidCurrency(String currency) {
        if (CommonUtil.isNullOrEmpty(currency)) {
            return false;
        }
        return CURRENCY_PATTERN.matcher(currency).matches();
    }

    /**
     * Validate customer ID format
     */
    public static boolean isValidCustomerId(String customerId) {
        if (CommonUtil.isNullOrEmpty(customerId)) {
            return false;
        }
        return CUSTOMER_ID_PATTERN.matcher(customerId).matches();
    }

    /**
     * Validate amount
     */
    public static boolean isValidAmount(BigDecimal amount) {
        if (amount == null) {
            return false;
        }
        return amount.compareTo(BigDecimal.ZERO) > 0 && amount.scale() <= 2;
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
     * Parse amount from string
     */
    public static BigDecimal parseAmount(String amountStr) {
        if (CommonUtil.isNullOrEmpty(amountStr)) {
            return null;
        }
        try {
            return new BigDecimal(amountStr.replaceAll("[^0-9.-]", ""));
        } catch (NumberFormatException e) {
            logger.warn("Failed to parse amount: {}", amountStr);
            return null;
        }
    }

    /**
     * Format date
     */
    public static String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(DATE_FORMATTER);
    }

    /**
     * Format datetime
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(DATETIME_FORMATTER);
    }

    /**
     * Parse date from string
     */
    public static LocalDateTime parseDate(String dateStr) {
        if (CommonUtil.isNullOrEmpty(dateStr)) {
            return null;
        }
        try {
            return LocalDateTime.parse(dateStr, DATE_FORMATTER);
        } catch (Exception e) {
            logger.warn("Failed to parse date: {}", dateStr);
            return null;
        }
    }

    /**
     * Parse datetime from string
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        if (CommonUtil.isNullOrEmpty(dateTimeStr)) {
            return null;
        }
        try {
            return LocalDateTime.parse(dateTimeStr, DATETIME_FORMATTER);
        } catch (Exception e) {
            logger.warn("Failed to parse datetime: {}", dateTimeStr);
            return null;
        }
    }

    /**
     * Calculate processing time in milliseconds
     */
    public static long calculateProcessingTime(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            return 0;
        }
        return java.time.Duration.between(startTime, endTime).toMillis();
    }

    /**
     * Calculate processing time in milliseconds from start time to now
     */
    public static long calculateProcessingTime(LocalDateTime startTime) {
        if (startTime == null) {
            return 0;
        }
        return calculateProcessingTime(startTime, LocalDateTime.now());
    }

    /**
     * Check if transaction is in final status
     */
    public static boolean isFinalStatus(String status) {
        if (CommonUtil.isNullOrEmpty(status)) {
            return false;
        }
        return FrameworkConstants.TRANSACTION_STATUS_COMPLETED.equals(status) ||
                FrameworkConstants.TRANSACTION_STATUS_FAILED.equals(status) ||
                FrameworkConstants.TRANSACTION_STATUS_CANCELLED.equals(status);
    }

    /**
     * Check if transaction can be retried
     */
    public static boolean canRetry(Transaction transaction) {
        if (transaction == null) {
            return false;
        }
        return FrameworkConstants.TRANSACTION_STATUS_FAILED.equals(transaction.getStatus()) &&
                transaction.getRetryCount() < transaction.getMaxRetryAttempts();
    }

    /**
     * Check if transaction requires authorization
     */
    public static boolean requiresAuthorization(Transaction transaction) {
        if (transaction == null) {
            return false;
        }
        return FrameworkConstants.AUTHORIZATION_STATUS_PENDING.equals(transaction.getAuthorizationStatus()) ||
                FrameworkConstants.AUTHORIZATION_STATUS_REQUIRED.equals(transaction.getAuthorizationStatus());
    }

    /**
     * Check if transaction is authorized
     */
    public static boolean isAuthorized(Transaction transaction) {
        if (transaction == null) {
            return false;
        }
        return FrameworkConstants.AUTHORIZATION_STATUS_APPROVED.equals(transaction.getAuthorizationStatus());
    }

    /**
     * Check if transaction is validated
     */
    public static boolean isValidated(Transaction transaction) {
        if (transaction == null) {
            return false;
        }
        return FrameworkConstants.VALIDATION_STATUS_PASS.equals(transaction.getValidationStatus());
    }

    /**
     * Check if transaction is routed
     */
    public static boolean isRouted(Transaction transaction) {
        if (transaction == null) {
            return false;
        }
        return FrameworkConstants.ROUTING_STATUS_COMPLETED.equals(transaction.getRoutingStatus());
    }

    /**
     * Check if transaction is queued
     */
    public static boolean isQueued(Transaction transaction) {
        if (transaction == null) {
            return false;
        }
        return FrameworkConstants.QUEUE_STATUS_QUEUED.equals(transaction.getQueueStatus());
    }

    /**
     * Get transaction priority level
     */
    public static int getPriorityLevel(String priority) {
        if (CommonUtil.isNullOrEmpty(priority)) {
            return 0;
        }
        switch (priority.toUpperCase()) {
            case TransactionConstants.PROCESSING_PRIORITY_HIGH:
                return 3;
            case TransactionConstants.PROCESSING_PRIORITY_NORMAL:
                return 2;
            case TransactionConstants.PROCESSING_PRIORITY_LOW:
                return 1;
            default:
                return 0;
        }
    }

    /**
     * Compare transaction priorities
     */
    public static int comparePriorities(String priority1, String priority2) {
        int level1 = getPriorityLevel(priority1);
        int level2 = getPriorityLevel(priority2);
        return Integer.compare(level2, level1); // Higher priority first
    }

    /**
     * Calculate retry delay based on retry count and strategy
     */
    public static long calculateRetryDelay(int retryCount, String retryStrategy) {
        if (retryCount <= 0) {
            return 0;
        }

        switch (retryStrategy) {
            case TransactionConstants.RETRY_STRATEGY_IMMEDIATE:
                return 0;
            case TransactionConstants.RETRY_STRATEGY_FIXED_DELAY:
                return TransactionConstants.DEFAULT_RETRY_DELAY_MS;
            case TransactionConstants.RETRY_STRATEGY_EXPONENTIAL_BACKOFF:
                return (long) (TransactionConstants.DEFAULT_RETRY_DELAY_MS *
                        Math.pow(TransactionConstants.DEFAULT_BACKOFF_MULTIPLIER, retryCount - 1));
            default:
                return TransactionConstants.DEFAULT_RETRY_DELAY_MS;
        }
    }

    /**
     * Calculate next retry time
     */
    public static LocalDateTime calculateNextRetryTime(int retryCount, String retryStrategy) {
        long delayMs = calculateRetryDelay(retryCount, retryStrategy);
        return LocalDateTime.now().plusNanos(delayMs * 1_000_000);
    }

    /**
     * Check if retry is due
     */
    public static boolean isRetryDue(Transaction transaction) {
        if (transaction == null || transaction.getNextRetryTime() == null) {
            return false;
        }
        return LocalDateTime.now().isAfter(transaction.getNextRetryTime());
    }

    /**
     * Get transaction status description
     */
    public static String getStatusDescription(String status) {
        if (CommonUtil.isNullOrEmpty(status)) {
            return "Unknown";
        }

        Map<String, String> statusDescriptions = new HashMap<>();
        statusDescriptions.put(FrameworkConstants.TRANSACTION_STATUS_INITIATED, "Transaction initiated");
        statusDescriptions.put(FrameworkConstants.TRANSACTION_STATUS_PENDING, "Transaction pending");
        statusDescriptions.put(FrameworkConstants.TRANSACTION_STATUS_PROCESSING, "Transaction processing");
        statusDescriptions.put(FrameworkConstants.TRANSACTION_STATUS_COMPLETED, "Transaction completed");
        statusDescriptions.put(FrameworkConstants.TRANSACTION_STATUS_FAILED, "Transaction failed");
        statusDescriptions.put(FrameworkConstants.TRANSACTION_STATUS_CANCELLED, "Transaction cancelled");
        statusDescriptions.put(FrameworkConstants.TRANSACTION_STATUS_SUSPENDED, "Transaction suspended");

        return statusDescriptions.getOrDefault(status, "Unknown status: " + status);
    }

    /**
     * Get authorization status description
     */
    public static String getAuthorizationStatusDescription(String authorizationStatus) {
        if (CommonUtil.isNullOrEmpty(authorizationStatus)) {
            return "Unknown";
        }

        Map<String, String> authStatusDescriptions = new HashMap<>();
        authStatusDescriptions.put(FrameworkConstants.AUTHORIZATION_STATUS_PENDING, "Authorization pending");
        authStatusDescriptions.put(FrameworkConstants.AUTHORIZATION_STATUS_REQUIRED, "Authorization required");
        authStatusDescriptions.put(FrameworkConstants.AUTHORIZATION_STATUS_APPROVED, "Authorization approved");
        authStatusDescriptions.put(FrameworkConstants.AUTHORIZATION_STATUS_DECLINED, "Authorization declined");
        authStatusDescriptions.put(FrameworkConstants.AUTHORIZATION_STATUS_EXPIRED, "Authorization expired");

        return authStatusDescriptions.getOrDefault(authorizationStatus,
                "Unknown authorization status: " + authorizationStatus);
    }

    /**
     * Get validation status description
     */
    public static String getValidationStatusDescription(String validationStatus) {
        if (CommonUtil.isNullOrEmpty(validationStatus)) {
            return "Unknown";
        }

        Map<String, String> validationStatusDescriptions = new HashMap<>();
        validationStatusDescriptions.put(FrameworkConstants.VALIDATION_STATUS_PENDING, "Validation pending");
        validationStatusDescriptions.put(FrameworkConstants.VALIDATION_STATUS_PASS, "Validation passed");
        validationStatusDescriptions.put(FrameworkConstants.VALIDATION_STATUS_FAIL, "Validation failed");
        validationStatusDescriptions.put(FrameworkConstants.VALIDATION_STATUS_WARNING, "Validation warning");
        validationStatusDescriptions.put(FrameworkConstants.VALIDATION_STATUS_REVIEW, "Validation review required");

        return validationStatusDescriptions.getOrDefault(validationStatus,
                "Unknown validation status: " + validationStatus);
    }

    /**
     * Get routing status description
     */
    public static String getRoutingStatusDescription(String routingStatus) {
        if (CommonUtil.isNullOrEmpty(routingStatus)) {
            return "Unknown";
        }

        Map<String, String> routingStatusDescriptions = new HashMap<>();
        routingStatusDescriptions.put(FrameworkConstants.ROUTING_STATUS_PENDING, "Routing pending");
        routingStatusDescriptions.put(FrameworkConstants.ROUTING_STATUS_IN_PROGRESS, "Routing in progress");
        routingStatusDescriptions.put(FrameworkConstants.ROUTING_STATUS_COMPLETED, "Routing completed");
        routingStatusDescriptions.put(FrameworkConstants.ROUTING_STATUS_FAILED, "Routing failed");
        routingStatusDescriptions.put(FrameworkConstants.ROUTING_STATUS_CANCELLED, "Routing cancelled");

        return routingStatusDescriptions.getOrDefault(routingStatus, "Unknown routing status: " + routingStatus);
    }

    /**
     * Get queue status description
     */
    public static String getQueueStatusDescription(String queueStatus) {
        if (CommonUtil.isNullOrEmpty(queueStatus)) {
            return "Unknown";
        }

        Map<String, String> queueStatusDescriptions = new HashMap<>();
        queueStatusDescriptions.put(FrameworkConstants.QUEUE_STATUS_READY, "Ready for processing");
        queueStatusDescriptions.put(FrameworkConstants.QUEUE_STATUS_PROCESSING, "Currently processing");
        queueStatusDescriptions.put(FrameworkConstants.QUEUE_STATUS_COMPLETED, "Processing completed");
        queueStatusDescriptions.put(FrameworkConstants.QUEUE_STATUS_FAILED, "Processing failed");
        queueStatusDescriptions.put(FrameworkConstants.QUEUE_STATUS_RETRY, "Scheduled for retry");

        return queueStatusDescriptions.getOrDefault(queueStatus, "Unknown queue status: " + queueStatus);
    }

    /**
     * Get transaction type description
     */
    public static String getTransactionTypeDescription(String transactionType) {
        if (CommonUtil.isNullOrEmpty(transactionType)) {
            return "Unknown";
        }

        Map<String, String> transactionTypeDescriptions = new HashMap<>();
        transactionTypeDescriptions.put(FrameworkConstants.TRANSACTION_TYPE_TRANSFER, "Fund transfer");
        transactionTypeDescriptions.put(FrameworkConstants.TRANSACTION_TYPE_PAYMENT, "Payment");
        transactionTypeDescriptions.put(FrameworkConstants.TRANSACTION_TYPE_DEPOSIT, "Deposit");
        transactionTypeDescriptions.put(FrameworkConstants.TRANSACTION_TYPE_WITHDRAWAL, "Withdrawal");
        transactionTypeDescriptions.put(FrameworkConstants.TRANSACTION_TYPE_EXCHANGE, "Currency exchange");
        transactionTypeDescriptions.put(FrameworkConstants.TRANSACTION_TYPE_REFUND, "Refund");
        transactionTypeDescriptions.put(FrameworkConstants.TRANSACTION_TYPE_ADJUSTMENT, "Adjustment");
        transactionTypeDescriptions.put(FrameworkConstants.TRANSACTION_TYPE_FEE, "Fee transaction");

        return transactionTypeDescriptions.getOrDefault(transactionType,
                "Unknown transaction type: " + transactionType);
    }

    /**
     * Get channel description
     */
    public static String getChannelDescription(String channel) {
        if (CommonUtil.isNullOrEmpty(channel)) {
            return "Unknown";
        }

        Map<String, String> channelDescriptions = new HashMap<>();
        channelDescriptions.put(FrameworkConstants.CHANNEL_TELLER, "Teller");
        channelDescriptions.put(FrameworkConstants.CHANNEL_ATM, "ATM");
        channelDescriptions.put(FrameworkConstants.CHANNEL_INTERNET_BANKING, "Internet Banking");
        channelDescriptions.put(FrameworkConstants.CHANNEL_MOBILE_BANKING, "Mobile Banking");
        channelDescriptions.put(FrameworkConstants.CHANNEL_PHONE_BANKING, "Phone Banking");
        channelDescriptions.put(FrameworkConstants.CHANNEL_BRANCH, "Branch");
        channelDescriptions.put(FrameworkConstants.CHANNEL_API, "API");
        channelDescriptions.put(FrameworkConstants.CHANNEL_BATCH, "Batch");

        return channelDescriptions.getOrDefault(channel, "Unknown channel: " + channel);
    }

    /**
     * Get currency description
     */
    public static String getCurrencyDescription(String currency) {
        if (CommonUtil.isNullOrEmpty(currency)) {
            return "Unknown";
        }

        Map<String, String> currencyDescriptions = new HashMap<>();
        currencyDescriptions.put("USD", "US Dollar");
        currencyDescriptions.put("EUR", "Euro");
        currencyDescriptions.put("GBP", "British Pound");
        currencyDescriptions.put("JPY", "Japanese Yen");
        currencyDescriptions.put("KRW", "Korean Won");
        currencyDescriptions.put("CNY", "Chinese Yuan");
        currencyDescriptions.put("HKD", "Hong Kong Dollar");
        currencyDescriptions.put("SGD", "Singapore Dollar");
        currencyDescriptions.put("AUD", "Australian Dollar");
        currencyDescriptions.put("CAD", "Canadian Dollar");
        currencyDescriptions.put("CHF", "Swiss Franc");
        currencyDescriptions.put("SEK", "Swedish Krona");
        currencyDescriptions.put("NOK", "Norwegian Krone");
        currencyDescriptions.put("DKK", "Danish Krone");
        currencyDescriptions.put("NZD", "New Zealand Dollar");

        return currencyDescriptions.getOrDefault(currency, "Unknown currency: " + currency);
    }

    /**
     * Mask sensitive data in transaction
     */
    public static String maskSensitiveData(String data, String maskType) {
        if (CommonUtil.isNullOrEmpty(data)) {
            return data;
        }

        switch (maskType.toLowerCase()) {
            case "account":
                return maskAccountNumber(data);
            case "customer":
                return maskCustomerId(data);
            case "amount":
                return maskAmount(data);
            case "description":
                return maskDescription(data);
            default:
                return data;
        }
    }

    /**
     * Mask account number
     */
    public static String maskAccountNumber(String accountNumber) {
        if (CommonUtil.isNullOrEmpty(accountNumber) || accountNumber.length() < 4) {
            return accountNumber;
        }
        return accountNumber.substring(0, 4) + "****" + accountNumber.substring(accountNumber.length() - 4);
    }

    /**
     * Mask customer ID
     */
    public static String maskCustomerId(String customerId) {
        if (CommonUtil.isNullOrEmpty(customerId) || customerId.length() < 3) {
            return customerId;
        }
        return customerId.substring(0, 3) + "***" + customerId.substring(customerId.length() - 2);
    }

    /**
     * Mask amount
     */
    public static String maskAmount(String amount) {
        if (CommonUtil.isNullOrEmpty(amount)) {
            return amount;
        }
        return "***";
    }

    /**
     * Mask description
     */
    public static String maskDescription(String description) {
        if (CommonUtil.isNullOrEmpty(description)) {
            return description;
        }
        if (description.length() <= 10) {
            return "***";
        }
        return description.substring(0, 5) + "***" + description.substring(description.length() - 5);
    }

    /**
     * Create transaction summary
     */
    public static Map<String, Object> createTransactionSummary(Transaction transaction) {
        Map<String, Object> summary = new HashMap<>();

        if (transaction == null) {
            return summary;
        }

        summary.put("transactionId", transaction.getTransactionId());
        summary.put("transactionType", transaction.getTransactionType());
        summary.put("status", transaction.getStatus());
        summary.put("amount", transaction.getAmount());
        summary.put("currency", transaction.getCurrency());
        summary.put("customerId", maskSensitiveData(transaction.getCustomerId(), "customer"));
        summary.put("sourceAccount", maskSensitiveData(transaction.getSourceAccount(), "account"));
        summary.put("destinationAccount", maskSensitiveData(transaction.getDestinationAccount(), "account"));
        summary.put("channel", transaction.getChannel());
        summary.put("createdDateTime", formatDateTime(transaction.getCreatedDateTime()));
        summary.put("processingTimeMs", transaction.getProcessingTimeMs());

        return summary;
    }

    /**
     * Validate transaction for processing
     */
    public static boolean validateTransactionForProcessing(Transaction transaction) {
        if (transaction == null) {
            return false;
        }

        // Check required fields
        if (!isValidTransactionId(transaction.getTransactionId())) {
            logger.warn("Invalid transaction ID: {}", transaction.getTransactionId());
            return false;
        }

        if (!isValidAmount(transaction.getAmount())) {
            logger.warn("Invalid amount: {}", transaction.getAmount());
            return false;
        }

        if (!isValidCurrency(transaction.getCurrency())) {
            logger.warn("Invalid currency: {}", transaction.getCurrency());
            return false;
        }

        if (!isValidAccountNumber(transaction.getSourceAccount())) {
            logger.warn("Invalid source account: {}", transaction.getSourceAccount());
            return false;
        }

        if (!isValidAccountNumber(transaction.getDestinationAccount())) {
            logger.warn("Invalid destination account: {}", transaction.getDestinationAccount());
            return false;
        }

        if (!isValidCustomerId(transaction.getCustomerId())) {
            logger.warn("Invalid customer ID: {}", transaction.getCustomerId());
            return false;
        }

        return true;
    }

    /**
     * Check if transaction is high value
     */
    public static boolean isHighValueTransaction(Transaction transaction, BigDecimal threshold) {
        if (transaction == null || threshold == null) {
            return false;
        }
        return transaction.getAmount().compareTo(threshold) >= 0;
    }

    /**
     * Check if transaction is international
     */
    public static boolean isInternationalTransaction(Transaction transaction) {
        if (transaction == null) {
            return false;
        }

        String sourceCurrency = transaction.getCurrency();
        String destinationCurrency = transaction.getCurrency(); // Assuming same for now

        // This would typically involve checking if source and destination are in
        // different countries
        // For now, we'll use a simple currency-based check
        return !CommonUtil.isNullOrEmpty(sourceCurrency) &&
                !CommonUtil.isNullOrEmpty(destinationCurrency) &&
                !sourceCurrency.equals(destinationCurrency);
    }

    /**
     * Calculate transaction risk score
     */
    public static int calculateRiskScore(Transaction transaction) {
        if (transaction == null) {
            return 0;
        }

        int riskScore = 0;

        // Amount-based risk
        if (transaction.getAmount().compareTo(new BigDecimal("10000")) >= 0) {
            riskScore += 10;
        } else if (transaction.getAmount().compareTo(new BigDecimal("5000")) >= 0) {
            riskScore += 5;
        }

        // International transaction risk
        if (isInternationalTransaction(transaction)) {
            riskScore += 15;
        }

        // High-value transaction risk
        if (isHighValueTransaction(transaction, new BigDecimal("50000"))) {
            riskScore += 20;
        }

        // Channel-based risk
        if (FrameworkConstants.CHANNEL_API.equals(transaction.getChannel())) {
            riskScore += 5;
        }

        // Time-based risk (night time transactions)
        LocalDateTime createdTime = transaction.getCreatedDateTime();
        if (createdTime != null) {
            int hour = createdTime.getHour();
            if (hour >= 22 || hour <= 6) {
                riskScore += 5;
            }
        }

        return Math.min(riskScore, 100); // Cap at 100
    }

    /**
     * Get risk level description
     */
    public static String getRiskLevelDescription(int riskScore) {
        if (riskScore >= 80) {
            return "Very High Risk";
        } else if (riskScore >= 60) {
            return "High Risk";
        } else if (riskScore >= 40) {
            return "Medium Risk";
        } else if (riskScore >= 20) {
            return "Low Risk";
        } else {
            return "Very Low Risk";
        }
    }

    private TransactionHelper() {
        // Private constructor to prevent instantiation
    }
}