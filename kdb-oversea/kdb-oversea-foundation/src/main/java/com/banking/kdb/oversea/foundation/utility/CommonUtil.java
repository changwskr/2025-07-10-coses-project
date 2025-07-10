package com.banking.kdb.oversea.foundation.utility;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.foundation.constant.Constants;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Common Utility class for KDB Oversea Foundation
 * 
 * Provides common utility methods used across the application.
 */
public class CommonUtil {

    private static final FoundationLogger logger = FoundationLogger.getLogger(CommonUtil.class);

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final String PHONE_PATTERN = "^[0-9\\-\\+\\s\\(\\)]+$";
    private static final String ALPHA_NUMERIC_PATTERN = "^[a-zA-Z0-9]+$";
    private static final String NUMERIC_PATTERN = "^[0-9]+$";
    private static final String ALPHA_PATTERN = "^[a-zA-Z\\s]+$";

    private static final Random random = new SecureRandom();
    private static final DecimalFormat currencyFormatter = new DecimalFormat("#,##0.00");
    private static final DecimalFormat numberFormatter = new DecimalFormat("#,##0");

    /**
     * Generate unique ID
     */
    public static String generateUniqueId() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**
     * Generate customer ID
     */
    public static String generateCustomerId() {
        return "CUST" + System.currentTimeMillis() % 1000000;
    }

    /**
     * Generate account number
     */
    public static String generateAccountNumber() {
        return "ACC" + System.currentTimeMillis() % 1000000000;
    }

    /**
     * Generate transaction ID
     */
    public static String generateTransactionId() {
        return "TXN" + System.currentTimeMillis() % 1000000000;
    }

    /**
     * Generate random number within range
     */
    public static int generateRandomNumber(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * Generate random string
     */
    public static String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    /**
     * Generate random numeric string
     */
    public static String generateRandomNumericString(int length) {
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    /**
     * Validate email format
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return Pattern.matches(EMAIL_PATTERN, email.trim());
    }

    /**
     * Validate phone number format
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }
        return Pattern.matches(PHONE_PATTERN, phoneNumber.trim());
    }

    /**
     * Validate alphanumeric string
     */
    public static boolean isAlphaNumeric(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        return Pattern.matches(ALPHA_NUMERIC_PATTERN, str.trim());
    }

    /**
     * Validate numeric string
     */
    public static boolean isNumeric(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        return Pattern.matches(NUMERIC_PATTERN, str.trim());
    }

    /**
     * Validate alphabetic string
     */
    public static boolean isAlpha(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        return Pattern.matches(ALPHA_PATTERN, str.trim());
    }

    /**
     * Validate password strength
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < Constants.PASSWORD_MIN_LENGTH ||
                password.length() > Constants.PASSWORD_MAX_LENGTH) {
            return false;
        }

        // Check for at least one uppercase, one lowercase, one digit, and one special
        // character
        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");

        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    /**
     * Hash password using SHA-256
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error hashing password", e);
            return null;
        }
    }

    /**
     * Format currency amount
     */
    public static String formatCurrency(BigDecimal amount) {
        if (amount == null) {
            return "0.00";
        }
        return currencyFormatter.format(amount);
    }

    /**
     * Format currency amount with currency code
     */
    public static String formatCurrency(BigDecimal amount, String currencyCode) {
        if (amount == null) {
            return "0.00 " + currencyCode;
        }
        return currencyFormatter.format(amount) + " " + currencyCode;
    }

    /**
     * Format number
     */
    public static String formatNumber(BigDecimal number) {
        if (number == null) {
            return "0";
        }
        return numberFormatter.format(number);
    }

    /**
     * Round amount to 2 decimal places
     */
    public static BigDecimal roundAmount(BigDecimal amount) {
        if (amount == null) {
            return BigDecimal.ZERO;
        }
        return amount.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calculate interest amount
     */
    public static BigDecimal calculateInterest(BigDecimal principal, double rate, int days) {
        if (principal == null || principal.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal rateDecimal = BigDecimal.valueOf(rate);
        BigDecimal daysDecimal = BigDecimal.valueOf(days);
        BigDecimal yearDays = BigDecimal.valueOf(365);

        return principal.multiply(rateDecimal).multiply(daysDecimal)
                .divide(yearDays, 2, RoundingMode.HALF_UP);
    }

    /**
     * Calculate compound interest
     */
    public static BigDecimal calculateCompoundInterest(BigDecimal principal, double rate, int years,
            int compoundingFrequency) {
        if (principal == null || principal.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        double ratePerPeriod = rate / compoundingFrequency;
        int totalPeriods = years * compoundingFrequency;

        double multiplier = Math.pow(1 + ratePerPeriod, totalPeriods);
        BigDecimal result = principal.multiply(BigDecimal.valueOf(multiplier));

        return result.subtract(principal).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Format date to string
     */
    public static String formatDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_YYYY_MM_DD));
    }

    /**
     * Format date to string with custom pattern
     */
    public static String formatDate(LocalDate date, String pattern) {
        if (date == null) {
            return "";
        }
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Format datetime to string
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
    }

    /**
     * Parse date from string
     */
    public static LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(dateStr.trim(), DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_YYYY_MM_DD));
        } catch (Exception e) {
            logger.error("Error parsing date: " + dateStr, e);
            return null;
        }
    }

    /**
     * Parse datetime from string
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDateTime.parse(dateTimeStr.trim(),
                    DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
        } catch (Exception e) {
            logger.error("Error parsing datetime: " + dateTimeStr, e);
            return null;
        }
    }

    /**
     * Check if string is null or empty
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Check if string is not null and not empty
     */
    public static boolean isNotNullOrEmpty(String str) {
        return !isNullOrEmpty(str);
    }

    /**
     * Get default value if null
     */
    public static String getDefaultIfNull(String value, String defaultValue) {
        return isNullOrEmpty(value) ? defaultValue : value;
    }

    /**
     * Get default value if null for BigDecimal
     */
    public static BigDecimal getDefaultIfNull(BigDecimal value, BigDecimal defaultValue) {
        return value == null ? defaultValue : value;
    }

    /**
     * Truncate string to specified length
     */
    public static String truncateString(String str, int maxLength) {
        if (str == null) {
            return null;
        }
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength);
    }

    /**
     * Mask sensitive data (e.g., credit card number)
     */
    public static String maskSensitiveData(String data, int visibleChars) {
        if (data == null || data.length() <= visibleChars) {
            return data;
        }

        int maskLength = data.length() - visibleChars;
        String mask = "*".repeat(maskLength);
        return mask + data.substring(maskLength);
    }

    /**
     * Mask email address
     */
    public static String maskEmail(String email) {
        if (!isValidEmail(email)) {
            return email;
        }

        String[] parts = email.split("@");
        if (parts.length != 2) {
            return email;
        }

        String username = parts[0];
        String domain = parts[1];

        if (username.length() <= 2) {
            return email;
        }

        String maskedUsername = username.charAt(0) + "*".repeat(username.length() - 2)
                + username.charAt(username.length() - 1);
        return maskedUsername + "@" + domain;
    }

    /**
     * Mask phone number
     */
    public static String maskPhoneNumber(String phoneNumber) {
        if (isNullOrEmpty(phoneNumber) || phoneNumber.length() < 4) {
            return phoneNumber;
        }

        String cleaned = phoneNumber.replaceAll("[^0-9]", "");
        if (cleaned.length() < 4) {
            return phoneNumber;
        }

        return cleaned.substring(0, 2) + "*".repeat(cleaned.length() - 4) + cleaned.substring(cleaned.length() - 2);
    }

    /**
     * Validate amount is positive
     */
    public static boolean isValidAmount(BigDecimal amount) {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Validate amount is not negative
     */
    public static boolean isNonNegativeAmount(BigDecimal amount) {
        return amount != null && amount.compareTo(BigDecimal.ZERO) >= 0;
    }

    /**
     * Check if amount exceeds limit
     */
    public static boolean exceedsLimit(BigDecimal amount, BigDecimal limit) {
        if (amount == null || limit == null) {
            return false;
        }
        return amount.compareTo(limit) > 0;
    }

    /**
     * Calculate percentage
     */
    public static BigDecimal calculatePercentage(BigDecimal amount, BigDecimal total) {
        if (amount == null || total == null || total.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return amount.multiply(BigDecimal.valueOf(100))
                .divide(total, 2, RoundingMode.HALF_UP);
    }

    /**
     * Get current timestamp as string
     */
    public static String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
    }

    /**
     * Get current date as string
     */
    public static String getCurrentDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_YYYY_MM_DD));
    }

    /**
     * Check if date is in the past
     */
    public static boolean isDateInPast(LocalDate date) {
        return date != null && date.isBefore(LocalDate.now());
    }

    /**
     * Check if date is in the future
     */
    public static boolean isDateInFuture(LocalDate date) {
        return date != null && date.isAfter(LocalDate.now());
    }

    /**
     * Check if date is today
     */
    public static boolean isDateToday(LocalDate date) {
        return date != null && date.equals(LocalDate.now());
    }

    /**
     * Get days between two dates
     */
    public static long getDaysBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        return java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
    }

    /**
     * Convert string to BigDecimal safely
     */
    public static BigDecimal toBigDecimal(String value) {
        if (isNullOrEmpty(value)) {
            return BigDecimal.ZERO;
        }
        try {
            return new BigDecimal(value.trim());
        } catch (NumberFormatException e) {
            logger.error("Error converting string to BigDecimal: " + value, e);
            return BigDecimal.ZERO;
        }
    }

    /**
     * Convert string to Integer safely
     */
    public static Integer toInteger(String value) {
        if (isNullOrEmpty(value)) {
            return null;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            logger.error("Error converting string to Integer: " + value, e);
            return null;
        }
    }

    /**
     * Convert string to Long safely
     */
    public static Long toLong(String value) {
        if (isNullOrEmpty(value)) {
            return null;
        }
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            logger.error("Error converting string to Long: " + value, e);
            return null;
        }
    }

    /**
     * Convert string to Double safely
     */
    public static Double toDouble(String value) {
        if (isNullOrEmpty(value)) {
            return null;
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            logger.error("Error converting string to Double: " + value, e);
            return null;
        }
    }

    private CommonUtil() {
        // Private constructor to prevent instantiation
    }
}