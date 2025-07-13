package com.skcc.oversea.foundation.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Log utility class
 * Replaces com.chb.coses.foundation.log.Log
 */
public class Log {

    /**
     * Default logger instance
     */
    public static final Logger VDCLogger = LoggerFactory.getLogger("VDC");

    /**
     * Business logger instance
     */
    public static final Logger BusinessLogger = LoggerFactory.getLogger("BUSINESS");

    /**
     * System logger instance
     */
    public static final Logger SystemLogger = LoggerFactory.getLogger("SYSTEM");

    /**
     * Error logger instance
     */
    public static final Logger ErrorLogger = LoggerFactory.getLogger("ERROR");

    /**
     * Debug logger instance
     */
    public static final Logger DebugLogger = LoggerFactory.getLogger("DEBUG");

    /**
     * Get logger for specific class
     */
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    /**
     * Get logger for specific name
     */
    public static Logger getLogger(String name) {
        return LoggerFactory.getLogger(name);
    }

    /**
     * Debug logging
     */
    public static void debug(String message) {
        VDCLogger.debug(message);
    }

    /**
     * Debug logging with throwable
     */
    public static void debug(String message, Throwable throwable) {
        VDCLogger.debug(message, throwable);
    }

    /**
     * Info logging
     */
    public static void info(String message) {
        VDCLogger.info(message);
    }

    /**
     * Info logging with throwable
     */
    public static void info(String message, Throwable throwable) {
        VDCLogger.info(message, throwable);
    }

    /**
     * Warn logging
     */
    public static void warn(String message) {
        VDCLogger.warn(message);
    }

    /**
     * Warn logging with throwable
     */
    public static void warn(String message, Throwable throwable) {
        VDCLogger.warn(message, throwable);
    }

    /**
     * Error logging
     */
    public static void error(String message) {
        VDCLogger.error(message);
    }

    /**
     * Error logging with throwable
     */
    public static void error(String message, Throwable throwable) {
        VDCLogger.error(message, throwable);
    }

    /**
     * Business logging
     */
    public static void business(String message) {
        BusinessLogger.info(message);
    }

    /**
     * System logging
     */
    public static void system(String message) {
        SystemLogger.info(message);
    }

    /**
     * Check if debug is enabled
     */
    public static boolean isDebugEnabled() {
        return VDCLogger.isDebugEnabled();
    }

    /**
     * Check if info is enabled
     */
    public static boolean isInfoEnabled() {
        return VDCLogger.isInfoEnabled();
    }

    /**
     * Check if warn is enabled
     */
    public static boolean isWarnEnabled() {
        return VDCLogger.isWarnEnabled();
    }

    /**
     * Check if error is enabled
     */
    public static boolean isErrorEnabled() {
        return VDCLogger.isErrorEnabled();
    }
}