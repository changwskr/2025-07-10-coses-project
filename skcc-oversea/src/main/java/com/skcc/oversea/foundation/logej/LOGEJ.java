package com.skcc.oversea.foundation.logej;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.skcc.oversea.foundation.constant.Constants;
import com.skcc.oversea.foundation.utility.CommonUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;

@Component
public class LOGEJ {

  private static LOGEJ instance;

  public static LOGEJ getInstance() {
    if (instance == null) {
      instance = new LOGEJ();
    }
    return instance;
  }

  private static final Logger logger = LoggerFactory.getLogger(LOGEJ.class);

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
  public static void startTransaction(String transactionId, String transactionType, String context) {
    transactionStartTimes.put(transactionId, System.currentTimeMillis());
    transactionContexts.put(transactionId, context);

    logger.info("[TXN_START] TransactionId: {}, Type: {}, Context: {}, Time: {}",
        transactionId, transactionType, context, getCurrentTime());
  }

  /**
   * End transaction logging
   */
  public static void endTransaction(String transactionId, String status, String message) {
    Long startTime = transactionStartTimes.get(transactionId);
    String context = transactionContexts.get(transactionId);

    if (startTime != null) {
      long duration = System.currentTimeMillis() - startTime;
      logger.info("[TXN_END] TransactionId: {}, Status: {}, Duration: {}ms, Context: {}, Message: {}, Time: {}",
          transactionId, status, duration, context, message, getCurrentTime());

      // Clean up
      transactionStartTimes.remove(transactionId);
      transactionContexts.remove(transactionId);
    }
  }

  /**
   * Log business event
   */
  public static void logBusinessEvent(String eventType, String eventId, String details) {
    logger.info("[BUSINESS_EVENT] EventType: {}, EventId: {}, Details: {}, Time: {}",
        eventType, eventId, details, getCurrentTime());
  }

  /**
   * Log system event
   */
  public static void logSystemEvent(String eventType, String eventId, String details) {
    logger.info("[SYSTEM_EVENT] EventType: {}, EventId: {}, Details: {}, Time: {}",
        eventType, eventId, details, getCurrentTime());
  }

  /**
   * Log security event
   */
  public static void logSecurityEvent(String eventType, String userId, String details) {
    logger.warn("[SECURITY_EVENT] EventType: {}, UserId: {}, Details: {}, Time: {}",
        eventType, userId, details, getCurrentTime());
  }

  /**
   * Log error with context
   */
  public static void logError(String errorCode, String errorMessage, String context, Throwable throwable) {
    logger.error("[ERROR] ErrorCode: {}, ErrorMessage: {}, Context: {}, Time: {}",
        errorCode, errorMessage, context, getCurrentTime(), throwable);
  }

  /**
   * Log performance metric
   */
  public static void logPerformance(String metricName, long duration, String context) {
    logger.info("[PERFORMANCE] Metric: {}, Duration: {}ms, Context: {}, Time: {}",
        metricName, duration, context, getCurrentTime());
  }

  /**
   * Log database operation
   */
  public static void logDatabaseOperation(String operation, String table, String criteria, long duration) {
    logger.debug("[DB_OPERATION] Operation: {}, Table: {}, Criteria: {}, Duration: {}ms, Time: {}",
        operation, table, criteria, duration, getCurrentTime());
  }

  /**
   * Log API call
   */
  public static void logApiCall(String method, String url, String requestId, long duration, int statusCode) {
    logger.info("[API_CALL] Method: {}, URL: {}, RequestId: {}, Duration: {}ms, StatusCode: {}, Time: {}",
        method, url, requestId, duration, statusCode, getCurrentTime());
  }

  /**
   * Log audit event
   */
  public static void logAudit(String transactionId, String operation, String status, String details) {
    logger.info("[AUDIT] TransactionId: {}, Operation: {}, Status: {}, Details: {}, Time: {}",
        transactionId, operation, status, details, getCurrentTime());
  }

  /**
   * Get current timestamp
   */
  private static String getCurrentTime() {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT_YYYY_MM_DD_HH_MM_SS));
  }

  /**
   * Get transaction counter
   */
  public static long getTransactionCounter(String transactionType) {
    return transactionCounters.computeIfAbsent(transactionType, k -> new AtomicLong(0)).incrementAndGet();
  }

  /**
   * Reset transaction counters
   */
  public static void resetTransactionCounters() {
    transactionCounters.clear();
    logger.info("[SYSTEM] Transaction counters reset");
  }

  /**
   * Get transaction statistics
   */
  public static String getTransactionStatistics() {
    StringBuilder stats = new StringBuilder();
    stats.append("Transaction Statistics:\n");
    transactionCounters.forEach((type, counter) -> stats.append(String.format("  %s: %d\n", type, counter.get())));
    return stats.toString();
  }

  // Legacy compatibility methods
  public static void printf(int mode, String systemname, String eventno, String key, String contentToWrite) {
    logger.info("[LEGACY] System: {}, Event: {}, Key: {}, Content: {}",
        systemname, eventno, key, contentToWrite);
  }

  public static void printf(int mode, Object event, String contentToWrite) {
    logger.info("[LEGACY] Event: {}, Content: {}", event, contentToWrite);
  }

  public static void txprintf(Object event, String contentToWrite) {
    logger.info("[LEGACY_TX] Event: {}, Content: {}", event, contentToWrite);
  }

  public static void eprintf(int mode, String systemname, String key, Exception pex) {
    logger.error("[LEGACY_ERROR] System: {}, Key: {}, Exception: {}",
        systemname, key, pex.getMessage(), pex);
  }

  public void info(String message) {
    // INFO 레벨 로그
    System.out.println("[INFO] " + message);
  }

  public void debug(String message) {
    // DEBUG 레벨 로그
    System.out.println("[DEBUG] " + message);
  }

  public void error(String message) {
    // ERROR 레벨 로그
    System.err.println("[ERROR] " + message);
  }

  public void warn(String message) {
    // WARN 레벨 로그
    System.out.println("[WARN] " + message);
  }
}
