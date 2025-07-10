package com.chb.coses.foundation.utility;

import java.util.UUID;

/**
 * Utility helper class for common operations
 */
public class UtilityHelper {

    /**
     * Generate a unique ID
     * 
     * @return unique ID string
     */
    public static String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Check if string is null or empty
     * 
     * @param str string to check
     * @return true if null or empty, false otherwise
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
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
     * Get current timestamp
     * 
     * @return current timestamp in milliseconds
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * Convert string to integer safely
     * 
     * @param str          string to convert
     * @param defaultValue default value if conversion fails
     * @return integer value or default value
     */
    public static int toInt(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Convert string to long safely
     * 
     * @param str          string to convert
     * @param defaultValue default value if conversion fails
     * @return long value or default value
     */
    public static long toLong(String str, long defaultValue) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private UtilityHelper() {
        // Utility class - prevent instantiation
    }
}