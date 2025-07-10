package com.banking.foundation.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * String utility class for common string operations
 * 
 * Provides comprehensive string manipulation and validation utilities.
 * This replaces the legacy StringUtils with enhanced Spring Boot features.
 */
@Component
public class StringUtils {

    private static final Logger logger = LoggerFactory.getLogger(StringUtils.class);

    // Common patterns
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^[+]?[0-9]{10,15}$");
    private static final Pattern ALPHANUMERIC_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9]+$");
    private static final Pattern NUMERIC_PATTERN = Pattern.compile(
            "^[0-9]+$");

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
     * Check if string is blank (null, empty, or whitespace only)
     * 
     * @param str string to check
     * @return true if blank, false otherwise
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Check if string is not blank
     * 
     * @param str string to check
     * @return true if not blank, false otherwise
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
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
     * Convert string to kebab case
     * 
     * @param str string to convert
     * @return kebab case string
     */
    public static String toKebabCase(String str) {
        if (isEmpty(str)) {
            return str;
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0) {
                    result.append('-');
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

    /**
     * Truncate string with ellipsis
     * 
     * @param str       string to truncate
     * @param maxLength maximum length
     * @return truncated string with ellipsis
     */
    public static String truncateWithEllipsis(String str, int maxLength) {
        if (isEmpty(str) || str.length() <= maxLength) {
            return str;
        }

        if (maxLength <= 3) {
            return "...";
        }

        return str.substring(0, maxLength - 3) + "...";
    }

    /**
     * Check if string is valid email
     * 
     * @param email email to validate
     * @return true if valid email, false otherwise
     */
    public static boolean isValidEmail(String email) {
        return isNotEmpty(email) && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Check if string is valid phone number
     * 
     * @param phone phone number to validate
     * @return true if valid phone number, false otherwise
     */
    public static boolean isValidPhone(String phone) {
        return isNotEmpty(phone) && PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * Check if string is alphanumeric
     * 
     * @param str string to check
     * @return true if alphanumeric, false otherwise
     */
    public static boolean isAlphanumeric(String str) {
        return isNotEmpty(str) && ALPHANUMERIC_PATTERN.matcher(str).matches();
    }

    /**
     * Check if string is numeric
     * 
     * @param str string to check
     * @return true if numeric, false otherwise
     */
    public static boolean isNumeric(String str) {
        return isNotEmpty(str) && NUMERIC_PATTERN.matcher(str).matches();
    }

    /**
     * Remove all whitespace from string
     * 
     * @param str string to process
     * @return string without whitespace
     */
    public static String removeWhitespace(String str) {
        if (isEmpty(str)) {
            return str;
        }

        return str.replaceAll("\\s+", "");
    }

    /**
     * Normalize whitespace (replace multiple spaces with single space)
     * 
     * @param str string to normalize
     * @return normalized string
     */
    public static String normalizeWhitespace(String str) {
        if (isEmpty(str)) {
            return str;
        }

        return str.replaceAll("\\s+", " ").trim();
    }

    /**
     * Count occurrences of substring
     * 
     * @param str    string to search in
     * @param subStr substring to count
     * @return number of occurrences
     */
    public static int countOccurrences(String str, String subStr) {
        if (isEmpty(str) || isEmpty(subStr)) {
            return 0;
        }

        int count = 0;
        int lastIndex = 0;

        while (lastIndex != -1) {
            lastIndex = str.indexOf(subStr, lastIndex);
            if (lastIndex != -1) {
                count++;
                lastIndex += subStr.length();
            }
        }

        return count;
    }

    /**
     * Generate random string
     * 
     * @param length length of random string
     * @return random string
     */
    public static String generateRandomString(int length) {
        if (length <= 0) {
            return "";
        }

        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            result.append(chars.charAt(index));
        }

        return result.toString();
    }

    /**
     * Generate random alphanumeric string
     * 
     * @param length length of random string
     * @return random alphanumeric string
     */
    public static String generateRandomAlphanumeric(int length) {
        if (length <= 0) {
            return "";
        }

        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            result.append(chars.charAt(index));
        }

        return result.toString();
    }

    /**
     * Generate random numeric string
     * 
     * @param length length of random string
     * @return random numeric string
     */
    public static String generateRandomNumeric(int length) {
        if (length <= 0) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            result.append((int) (Math.random() * 10));
        }

        return result.toString();
    }

    private StringUtils() {
        // Utility class - prevent instantiation
    }
}