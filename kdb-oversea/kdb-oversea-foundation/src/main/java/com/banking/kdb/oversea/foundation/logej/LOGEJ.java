package com.banking.kdb.oversea.foundation.logej;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.foundation.constant.Constants;
import com.banking.kdb.oversea.foundation.utility.CommonUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Enhanced Logging Utility for KDB Oversea Foundation
 * 
 * Provides enhanced logging capabilities with transaction tracking,
 * performance monitoring, and structured logging.
 */
public class LOGEJ {

    private static final FoundationLogger logger = FoundationLogger.getLogger(LOGEJ.class);

    private static final ConcurrentHashMap<String, AtomicLong> transactionCounters = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Long> transactionStartTimes = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, String> transactionContexts = new ConcurrentHashMap<>();

    // Log levels
    public static final String LEVEL_TRACE = "TRACE";
    public static final String LEVEL_DEBUG = "DEBUG";
    public static final String LEVEL_INFO = "INFO";
    public static final String LEVEL_WARN = "WARN";
    public static final String LEVEL_ERROR = "ERROR";
    public static final String LEVEL_FATAL = "FATAL";

    // Transaction types
    public static final String TXN_TYPE_CUSTOMER = "CUSTOMER";
    public static final String TXN_TYPE_ACCOUNT = "ACCOUNT";
    public static final String TXN_TYPE_TRANSACTION = "TRANSACTION";
    public static final String TXN_TYPE_CASH_CARD = "CASH_CARD";
    public static final String TXN_TYPE_DEPOSIT = "DEPOSIT";
    public static final String TXN_TYPE_TELLER = "TELLER";

    /**
     * Start transaction logging
     */
    public static String startTransaction(String transactionType, String transactionId, String context) {
        String key = transactionType + "_" + transactionId;

        transactionCounters.computeIfAbsent(key, k -> new AtomicLong(0));
        transactionStartTimes.put(key, System.currentTimeMillis());
        transactionContexts.put(key, context);

        String message = String.format("Transaction START - Type: %s, ID: %s, Context: %s",
                transactionType, transactionId, context);

        logInfo(message, "TRANSACTION_START", transactionType, transactionId);

        return key;
    }

    /**
     * End transaction logging
     */
    public static void endTransaction(String transactionType, String transactionId, String status) {
        String key = transactionType + "_" + transactionId;

        Long startTime = transactionStartTimes.remove(key);
        String context = transactionContexts.remove(key);
        AtomicLong counter = transactionCounters.get(key);

        if (startTime != null) {
            long duration = System.currentTimeMillis() - startTime;
            long count = counter != null ? counter.get() : 0;

            String message = String.format(
                    "Transaction END - Type: %s, ID: %s, Status: %s, Duration: %dms, Operations: %d",
                    transactionType, transactionId, status, duration, count);

            logInfo(message, "TRANSACTION_END", transactionType, transactionId);

            // Log performance metrics
            if (duration > 1000) { // Log slow transactions
                logWarn(String.format("Slow transaction detected - Duration: %dms, Type: %s, ID: %s",
                        duration, transactionType, transactionId), "PERFORMANCE", transactionType, transactionId);
            }
        }
    }

    /**
     * Log transaction step
     */
    public static void logTransactionStep(String transactionType, String transactionId, String step, String details) {
        String key = transactionType + "_" + transactionId;
        AtomicLong counter = transactionCounters.get(key);
        if (counter != null) {
            counter.incrementAndGet();
        }

        String message = String.format("Transaction STEP - Type: %s, ID: %s, Step: %s, Details: %s",
                transactionType, transactionId, step, details);

        logDebug(message, "TRANSACTION_STEP", transactionType, transactionId);
    }

    /**
     * Log customer operation
     */
    public static void logCustomerOperation(String operation, String customerId, String details) {
        String message = String.format("Customer Operation - Operation: %s, Customer ID: %s, Details: %s",
                operation, customerId, details);

        logInfo(message, "CUSTOMER_OPERATION", TXN_TYPE_CUSTOMER, customerId);
    }

    /**
     * Log account operation
     */
    public static void logAccountOperation(String operation, String accountNumber, String details) {
        String message = String.format("Account Operation - Operation: %s, Account: %s, Details: %s",
                operation, accountNumber, details);

        logInfo(message, "ACCOUNT_OPERATION", TXN_TYPE_ACCOUNT, accountNumber);
    }

    /**
     * Log transaction operation
     */
    public static void logTransactionOperation(String operation, String transactionId, String details) {
        String message = String.format("Transaction Operation - Operation: %s, Transaction ID: %s, Details: %s",
                operation, transactionId, details);

        logInfo(message, "TRANSACTION_OPERATION", TXN_TYPE_TRANSACTION, transactionId);
    }

    /**
     * Log cash card operation
     */
    public static void logCashCardOperation(String operation, String cardNumber, String details) {
        String maskedCardNumber = CommonUtil.maskSensitiveData(cardNumber, 4);
        String message = String.format("Cash Card Operation - Operation: %s, Card: %s, Details: %s",
                operation, maskedCardNumber, details);

        logInfo(message, "CASH_CARD_OPERATION", TXN_TYPE_CASH_CARD, maskedCardNumber);
    }

    /**
     * Log deposit operation
     */
    public static void logDepositOperation(String operation, String accountNumber, String details) {
        String message = String.format("Deposit Operation - Operation: %s, Account: %s, Details: %s",
                operation, accountNumber, details);

        logInfo(message, "DEPOSIT_OPERATION", TXN_TYPE_DEPOSIT, accountNumber);
    }

    /**
     * Log teller operation
     */
    public static void logTellerOperation(String operation, String tellerId, String details) {
        String message = String.format("Teller Operation - Operation: %s, Teller ID: %s, Details: %s",
                operation, tellerId, details);

        logInfo(message, "TELLER_OPERATION", TXN_TYPE_TELLER, tellerId);
    }

    /**
     * Log security event
     */
    public static void logSecurityEvent(String event, String userId, String details) {
        String message = String.format("Security Event - Event: %s, User ID: %s, Details: %s",
                event, userId, details);

        logWarn(message, "SECURITY_EVENT", "SECURITY", userId);
    }

    /**
     * Log authentication event
     */
    public static void logAuthenticationEvent(String event, String userId, String result) {
        String message = String.format("Authentication Event - Event: %s, User ID: %s, Result: %s",
                event, userId, result);

        if ("SUCCESS".equals(result)) {
            logInfo(message, "AUTH_SUCCESS", "AUTHENTICATION", userId);
        } else {
            logWarn(message, "AUTH_FAILURE", "AUTHENTICATION", userId);
        }
    }

    /**
     * Log authorization event
     */
    public static void logAuthorizationEvent(String event, String userId, String resource, String result) {
        String message = String.format("Authorization Event - Event: %s, User ID: %s, Resource: %s, Result: %s",
                event, userId, resource, result);

        if ("GRANTED".equals(result)) {
            logInfo(message, "AUTHZ_SUCCESS", "AUTHORIZATION", userId);
        } else {
            logWarn(message, "AUTHZ_DENIED", "AUTHORIZATION", userId);
        }
    }

    /**
     * Log performance metric
     */
    public static void logPerformanceMetric(String metric, String value, String unit) {
        String message = String.format("Performance Metric - Metric: %s, Value: %s, Unit: %s",
                metric, value, unit);

        logInfo(message, "PERFORMANCE_METRIC", "PERFORMANCE", metric);
    }

    /**
     * Log business metric
     */
    public static void logBusinessMetric(String metric, String value, String context) {
        String message = String.format("Business Metric - Metric: %s, Value: %s, Context: %s",
                metric, value, context);

        logInfo(message, "BUSINESS_METRIC", "BUSINESS", metric);
    }

    /**
     * Log system health
     */
    public static void logSystemHealth(String component, String status, String details) {
        String message = String.format("System Health - Component: %s, Status: %s, Details: %s",
                component, status, details);

        if ("HEALTHY".equals(status)) {
            logInfo(message, "SYSTEM_HEALTH", "SYSTEM", component);
        } else {
            logWarn(message, "SYSTEM_ISSUE", "SYSTEM", component);
        }
    }

    /**
     * Log data access
     */
    public static void logDataAccess(String operation, String table, String criteria, String result) {
        String message = String.format("Data Access - Operation: %s, Table: %s, Criteria: %s, Result: %s",
                operation, table, criteria, result);

        logDebug(message, "DATA_ACCESS", "DATABASE", table);
    }

    /**
     * Log API call
     */
    public static void logApiCall(String method, String endpoint, String parameters, String result) {
        String message = String.format("API Call - Method: %s, Endpoint: %s, Parameters: %s, Result: %s",
                method, endpoint, parameters, result);

        logInfo(message, "API_CALL", "API", endpoint);
    }

    /**
     * Log external service call
     */
    public static void logExternalServiceCall(String service, String operation, String parameters, String result) {
        String message = String.format("External Service Call - Service: %s, Operation: %s, Parameters: %s, Result: %s",
                service, operation, parameters, result);

        logInfo(message, "EXTERNAL_SERVICE", "EXTERNAL", service);
    }

    /**
     * Enhanced info logging
     */
    public static void logInfo(String message, String category, String subCategory, String identifier) {
        String formattedMessage = formatLogMessage(LEVEL_INFO, message, category, subCategory, identifier);
        logger.info(formattedMessage);
    }

    /**
     * Enhanced debug logging
     */
    public static void logDebug(String message, String category, String subCategory, String identifier) {
        String formattedMessage = formatLogMessage(LEVEL_DEBUG, message, category, subCategory, identifier);
        logger.debug(formattedMessage);
    }

    /**
     * Enhanced warn logging
     */
    public static void logWarn(String message, String category, String subCategory, String identifier) {
        String formattedMessage = formatLogMessage(LEVEL_WARN, message, category, subCategory, identifier);
        logger.warn(formattedMessage);
    }

    /**
     * Enhanced error logging
     */
    public static void logError(String message, String category, String subCategory, String identifier,
            Throwable throwable) {
        String formattedMessage = formatLogMessage(LEVEL_ERROR, message, category, subCategory, identifier);
        logger.error(formattedMessage, throwable);
    }

    /**
     * Enhanced error logging without throwable
     */
    public static void logError(String message, String category, String subCategory, String identifier) {
        String formattedMessage = formatLogMessage(LEVEL_ERROR, message, category, subCategory, identifier);
        logger.error(formattedMessage);
    }

    /**
     * Format log message with structured information
     */
    private static String formatLogMessage(String level, String message, String category, String subCategory,
            String identifier) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
        String threadName = Thread.currentThread().getName();

        return String.format("[%s] [%s] [%s] [%s] [%s] [%s] %s",
                timestamp, level, category, subCategory, identifier, threadName, message);
    }

    /**
     * Get transaction statistics
     */
    public static String getTransactionStatistics() {
        StringBuilder stats = new StringBuilder();
        stats.append("Transaction Statistics:\n");

        transactionCounters.forEach((key, counter) -> {
            Long startTime = transactionStartTimes.get(key);
            String context = transactionContexts.get(key);

            if (startTime != null) {
                long duration = System.currentTimeMillis() - startTime;
                stats.append(String.format("  %s: Count=%d, Duration=%dms, Context=%s\n",
                        key, counter.get(), duration, context));
            }
        });

        return stats.toString();
    }

    /**
     * Clear transaction tracking data
     */
    public static void clearTransactionData() {
        transactionCounters.clear();
        transactionStartTimes.clear();
        transactionContexts.clear();
    }

    private LOGEJ() {
        // Private constructor to prevent instantiation
    }
}