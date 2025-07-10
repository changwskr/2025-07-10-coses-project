package com.banking.foundation.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Foundation Logger for enhanced logging capabilities
 * 
 * Provides comprehensive logging functionality with structured logging,
 * performance monitoring, and audit trail capabilities.
 * This replaces the legacy Log class with Spring Boot features.
 */
@Component
public class FoundationLogger {

    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private final Logger logger;
    private final String loggerName;
    private final String correlationId;

    /**
     * Constructor with default logger
     */
    public FoundationLogger() {
        this(FoundationLogger.class);
    }

    /**
     * Constructor with specified class
     * 
     * @param clazz class for logger
     */
    public FoundationLogger(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
        this.loggerName = clazz.getSimpleName();
        this.correlationId = UUID.randomUUID().toString().substring(0, 8);
    }

    /**
     * Constructor with specified logger name
     * 
     * @param loggerName name of the logger
     */
    public FoundationLogger(String loggerName) {
        this.logger = LoggerFactory.getLogger(loggerName);
        this.loggerName = loggerName;
        this.correlationId = UUID.randomUUID().toString().substring(0, 8);
    }

    /**
     * Log debug message
     * 
     * @param message message to log
     */
    public void debug(String message) {
        if (logger.isDebugEnabled()) {
            logger.debug(formatMessage("DEBUG", message));
        }
    }

    /**
     * Log debug message with parameters
     * 
     * @param message message to log
     * @param args    parameters
     */
    public void debug(String message, Object... args) {
        if (logger.isDebugEnabled()) {
            logger.debug(formatMessage("DEBUG", message), args);
        }
    }

    /**
     * Log info message
     * 
     * @param message message to log
     */
    public void info(String message) {
        logger.info(formatMessage("INFO", message));
    }

    /**
     * Log info message with parameters
     * 
     * @param message message to log
     * @param args    parameters
     */
    public void info(String message, Object... args) {
        logger.info(formatMessage("INFO", message), args);
    }

    /**
     * Log warning message
     * 
     * @param message message to log
     */
    public void warn(String message) {
        logger.warn(formatMessage("WARN", message));
    }

    /**
     * Log warning message with parameters
     * 
     * @param message message to log
     * @param args    parameters
     */
    public void warn(String message, Object... args) {
        logger.warn(formatMessage("WARN", message), args);
    }

    /**
     * Log warning message with exception
     * 
     * @param message   message to log
     * @param throwable exception
     */
    public void warn(String message, Throwable throwable) {
        logger.warn(formatMessage("WARN", message), throwable);
    }

    /**
     * Log error message
     * 
     * @param message message to log
     */
    public void error(String message) {
        logger.error(formatMessage("ERROR", message));
    }

    /**
     * Log error message with parameters
     * 
     * @param message message to log
     * @param args    parameters
     */
    public void error(String message, Object... args) {
        logger.error(formatMessage("ERROR", message), args);
    }

    /**
     * Log error message with exception
     * 
     * @param message   message to log
     * @param throwable exception
     */
    public void error(String message, Throwable throwable) {
        logger.error(formatMessage("ERROR", message), throwable);
    }

    /**
     * Log trace message
     * 
     * @param message message to log
     */
    public void trace(String message) {
        if (logger.isTraceEnabled()) {
            logger.trace(formatMessage("TRACE", message));
        }
    }

    /**
     * Log trace message with parameters
     * 
     * @param message message to log
     * @param args    parameters
     */
    public void trace(String message, Object... args) {
        if (logger.isTraceEnabled()) {
            logger.trace(formatMessage("TRACE", message), args);
        }
    }

    /**
     * Log method entry
     * 
     * @param methodName method name
     */
    public void methodEntry(String methodName) {
        if (logger.isDebugEnabled()) {
            logger.debug(formatMessage("DEBUG", "Method Entry: " + methodName));
        }
    }

    /**
     * Log method exit
     * 
     * @param methodName method name
     */
    public void methodExit(String methodName) {
        if (logger.isDebugEnabled()) {
            logger.debug(formatMessage("DEBUG", "Method Exit: " + methodName));
        }
    }

    /**
     * Log method exit with result
     * 
     * @param methodName method name
     * @param result     result object
     */
    public void methodExit(String methodName, Object result) {
        if (logger.isDebugEnabled()) {
            logger.debug(formatMessage("DEBUG", "Method Exit: " + methodName + " - Result: " + result));
        }
    }

    /**
     * Log performance measurement
     * 
     * @param operation operation name
     * @param startTime start time in milliseconds
     */
    public void logPerformance(String operation, long startTime) {
        long duration = System.currentTimeMillis() - startTime;
        logger.info(formatMessage("INFO", "Performance: " + operation + " - Duration: " + duration + "ms"));
    }

    /**
     * Log audit trail
     * 
     * @param action  action performed
     * @param userId  user ID
     * @param details additional details
     */
    public void audit(String action, String userId, String details) {
        logger.info(formatMessage("AUDIT", "Action: " + action + " | User: " + userId + " | Details: " + details));
    }

    /**
     * Log security event
     * 
     * @param event     security event
     * @param userId    user ID
     * @param ipAddress IP address
     */
    public void security(String event, String userId, String ipAddress) {
        logger.warn(formatMessage("SECURITY", "Event: " + event + " | User: " + userId + " | IP: " + ipAddress));
    }

    /**
     * Log business event
     * 
     * @param event business event
     * @param data  event data
     */
    public void business(String event, Object data) {
        logger.info(formatMessage("BUSINESS", "Event: " + event + " | Data: " + data));
    }

    /**
     * Format message with metadata
     * 
     * @param level   log level
     * @param message message to format
     * @return formatted message
     */
    private String formatMessage(String level, String message) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        return String.format("[%s] [%s] [%s] [%s] %s",
                timestamp, level, loggerName, correlationId, message);
    }

    /**
     * Get correlation ID
     * 
     * @return correlation ID
     */
    public String getCorrelationId() {
        return correlationId;
    }

    /**
     * Get logger name
     * 
     * @return logger name
     */
    public String getLoggerName() {
        return loggerName;
    }

    /**
     * Check if debug is enabled
     * 
     * @return true if debug enabled, false otherwise
     */
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    /**
     * Check if trace is enabled
     * 
     * @return true if trace enabled, false otherwise
     */
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    /**
     * Get underlying logger
     * 
     * @return underlying logger
     */
    public Logger getLogger() {
        return logger;
    }
}