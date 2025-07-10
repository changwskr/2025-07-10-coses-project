package com.banking.foundation.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Base Helper class for foundation components
 * 
 * Provides common functionality for all foundation components.
 * This replaces the legacy BaseHelper with Spring Boot features.
 */
@Component
public class BaseHelper {

    private static final Logger logger = LoggerFactory.getLogger(BaseHelper.class);

    /**
     * Generate unique identifier
     * 
     * @return unique identifier
     */
    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generate short unique identifier
     * 
     * @return short unique identifier
     */
    public static String generateShortId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    /**
     * Check if object is null
     * 
     * @param obj object to check
     * @return true if null, false otherwise
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * Check if object is not null
     * 
     * @param obj object to check
     * @return true if not null, false otherwise
     */
    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    /**
     * Get object class name safely
     * 
     * @param obj object to get class name for
     * @return class name or "null" if object is null
     */
    public static String getClassName(Object obj) {
        return obj != null ? obj.getClass().getSimpleName() : "null";
    }

    /**
     * Get object class name with package safely
     * 
     * @param obj object to get class name for
     * @return full class name or "null" if object is null
     */
    public static String getFullClassName(Object obj) {
        return obj != null ? obj.getClass().getName() : "null";
    }

    /**
     * Log object information
     * 
     * @param obj object to log
     */
    public static void logObjectInfo(Object obj) {
        if (obj != null) {
            logger.info("Object: {} - Class: {}", obj, obj.getClass().getName());
        } else {
            logger.info("Object: null");
        }
    }

    /**
     * Log object information with custom message
     * 
     * @param message custom message
     * @param obj     object to log
     */
    public static void logObjectInfo(String message, Object obj) {
        if (obj != null) {
            logger.info("{} - Object: {} - Class: {}", message, obj, obj.getClass().getName());
        } else {
            logger.info("{} - Object: null", message);
        }
    }

    /**
     * Get system information
     * 
     * @return system information map
     */
    public static java.util.Map<String, String> getSystemInfo() {
        java.util.Map<String, String> info = new java.util.HashMap<>();

        info.put("java.version", System.getProperty("java.version"));
        info.put("java.vendor", System.getProperty("java.vendor"));
        info.put("os.name", System.getProperty("os.name"));
        info.put("os.version", System.getProperty("os.version"));
        info.put("user.name", System.getProperty("user.name"));
        info.put("user.dir", System.getProperty("user.dir"));

        return info;
    }

    /**
     * Get memory information
     * 
     * @return memory information map
     */
    public static java.util.Map<String, Long> getMemoryInfo() {
        java.util.Map<String, Long> info = new java.util.HashMap<>();

        Runtime runtime = Runtime.getRuntime();
        info.put("totalMemory", runtime.totalMemory());
        info.put("freeMemory", runtime.freeMemory());
        info.put("usedMemory", runtime.totalMemory() - runtime.freeMemory());
        info.put("maxMemory", runtime.maxMemory());

        return info;
    }

    /**
     * Format memory size in human readable format
     * 
     * @param bytes memory size in bytes
     * @return formatted memory size
     */
    public static String formatMemorySize(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1024 * 1024) {
            return String.format("%.2f KB", bytes / 1024.0);
        } else if (bytes < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", bytes / (1024.0 * 1024.0));
        } else {
            return String.format("%.2f GB", bytes / (1024.0 * 1024.0 * 1024.0));
        }
    }

    /**
     * Get current timestamp
     * 
     * @return current timestamp in milliseconds
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * Get current timestamp as string
     * 
     * @return current timestamp as ISO string
     */
    public static String getCurrentTimestampString() {
        return java.time.Instant.now().toString();
    }

    /**
     * Sleep for specified milliseconds
     * 
     * @param milliseconds milliseconds to sleep
     */
    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Sleep interrupted", e);
        }
    }

    /**
     * Get thread information
     * 
     * @return thread information map
     */
    public static java.util.Map<String, Object> getThreadInfo() {
        java.util.Map<String, Object> info = new java.util.HashMap<>();

        Thread currentThread = Thread.currentThread();
        info.put("threadId", currentThread.getId());
        info.put("threadName", currentThread.getName());
        info.put("threadPriority", currentThread.getPriority());
        info.put("threadState", currentThread.getState());
        info.put("isDaemon", currentThread.isDaemon());

        return info;
    }
}