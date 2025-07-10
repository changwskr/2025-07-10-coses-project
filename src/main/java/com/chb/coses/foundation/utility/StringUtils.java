package com.chb.coses.foundation.utility;

/**
 * String utility class for common string operations
 */
public class StringUtils {

    /**
     * Check if string is null or empty
     * 
     * @param str string to check
     * @return true if null or empty, false otherwise
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Check if string is not null and not empty
     * 
     * @param str string to check
     * @return true if not null and not empty, false otherwise
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * Convert string to camel case
     * 
     * @param str string to convert
     * @return camel case string
     */
    public static String toCamelCase(String str) {
        if (isEmpty(str)) {
            return str;
        }

        StringBuilder result = new StringBuilder();
        boolean nextUpper = false;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '_' || c == '-') {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    result.append(Character.toUpperCase(c));
                    nextUpper = false;
                } else {
                    result.append(Character.toLowerCase(c));
                }
            }
        }

        return result.toString();
    }

    /**
     * Convert string to snake case
     * 
     * @param str string to convert
     * @return snake case string
     */
    public static String toSnakeCase(String str) {
        if (isEmpty(str)) {
            return str;
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0) {
                    result.append('_');
                }
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    /**
     * Capitalize first letter
     * 
     * @param str string to capitalize
     * @return capitalized string
     */
    public static String capitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }

        return Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
    }

    /**
     * Uncapitalize first letter
     * 
     * @param str string to uncapitalize
     * @return uncapitalized string
     */
    public static String uncapitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }

        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * Reverse string
     * 
     * @param str string to reverse
     * @return reversed string
     */
    public static String reverse(String str) {
        if (isEmpty(str)) {
            return str;
        }

        return new StringBuilder(str).reverse().toString();
    }

    /**
     * Truncate string to specified length
     * 
     * @param str       string to truncate
     * @param maxLength maximum length
     * @return truncated string
     */
    public static String truncate(String str, int maxLength) {
        if (isEmpty(str) || str.length() <= maxLength) {
            return str;
        }

        return str.substring(0, maxLength);
    }

    private StringUtils() {
        // Utility class - prevent instantiation
    }
}