package com.chb.coses.foundation.log;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Log helper class for logging operations
 */
public class LogHelper {

    private static final Logger logger = Logger.getLogger(LogHelper.class.getName());

    /**
     * Log debug message
     * 
     * @param message message to log
     */
    public static void debug(String message) {
        logger.fine(message);
    }

    /**
     * Log info message
     * 
     * @param message message to log
     */
    public static void info(String message) {
        logger.info(message);
    }

    /**
     * Log warning message
     * 
     * @param message message to log
     */
    public static void warn(String message) {
        logger.warning(message);
    }

    /**
     * Log error message
     * 
     * @param message message to log
     */
    public static void error(String message) {
        logger.severe(message);
    }

    /**
     * Log error message with exception
     * 
     * @param message   message to log
     * @param throwable exception
     */
    public static void error(String message, Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }

    /**
     * Log message with specified level
     * 
     * @param level   log level
     * @param message message to log
     */
    public static void log(Level level, String message) {
        logger.log(level, message);
    }

    /**
     * Log message with specified level and exception
     * 
     * @param level     log level
     * @param message   message to log
     * @param throwable exception
     */
    public static void log(Level level, String message, Throwable throwable) {
        logger.log(level, message, throwable);
    }

    /**
     * Get logger instance
     * 
     * @return logger instance
     */
    public static Logger getLogger() {
        return logger;
    }

    private LogHelper() {
        // Utility class - prevent instantiation
    }
}