package com.banking.kdb.oversea.foundation.convert;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.foundation.utility.CommonUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Base64;
import java.util.regex.Pattern;

/**
 * Data Converter utility for KDB Oversea Foundation
 * 
 * Provides data conversion utilities for various data types.
 */
public class DataConverter {

    private static final FoundationLogger logger = FoundationLogger.getLogger(DataConverter.class);

    // Date format patterns
    private static final String DATE_PATTERN_YYYY_MM_DD = "yyyy-MM-dd";
    private static final String DATE_PATTERN_DD_MM_YYYY = "dd/MM/yyyy";
    private static final String DATE_PATTERN_MM_DD_YYYY = "MM/dd/yyyy";
    private static final String DATETIME_PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    private static final String DATETIME_PATTERN_ISO = "yyyy-MM-dd'T'HH:mm:ss";

    // Currency patterns
    private static final Pattern CURRENCY_PATTERN = Pattern.compile("^[0-9,]+(\\.[0-9]{1,2})?$");
    private static final Pattern PERCENTAGE_PATTERN = Pattern.compile("^[0-9]+(\\.[0-9]+)?%?$");

    /**
     * Convert string to BigDecimal (currency format)
     */
    public static BigDecimal stringToBigDecimal(String value) {
        if (CommonUtil.isNullOrEmpty(value)) {
            return BigDecimal.ZERO;
        }

        try {
            // Remove currency symbols and commas
            String cleaned = value.replaceAll("[$,€£¥₩]", "").replaceAll(",", "");
            return new BigDecimal(cleaned.trim());
        } catch (NumberFormatException e) {
            logger.error("Error converting string to BigDecimal: " + value, e);
            return BigDecimal.ZERO;
        }
    }

    /**
     * Convert string to BigDecimal with validation
     */
    public static BigDecimal stringToBigDecimal(String value, BigDecimal minValue, BigDecimal maxValue) {
        BigDecimal result = stringToBigDecimal(value);

        if (result.compareTo(minValue) < 0) {
            logger.warn("Value {} is below minimum {}", result, minValue);
            return minValue;
        }

        if (result.compareTo(maxValue) > 0) {
            logger.warn("Value {} is above maximum {}", result, maxValue);
            return maxValue;
        }

        return result;
    }

    /**
     * Convert BigDecimal to currency string
     */
    public static String bigDecimalToCurrencyString(BigDecimal value) {
        if (value == null) {
            return "0.00";
        }

        return String.format("%,.2f", value);
    }

    /**
     * Convert BigDecimal to currency string with symbol
     */
    public static String bigDecimalToCurrencyString(BigDecimal value, String currencySymbol) {
        if (value == null) {
            return currencySymbol + "0.00";
        }

        return currencySymbol + String.format("%,.2f", value);
    }

    /**
     * Convert string to percentage
     */
    public static BigDecimal stringToPercentage(String value) {
        if (CommonUtil.isNullOrEmpty(value)) {
            return BigDecimal.ZERO;
        }

        try {
            String cleaned = value.replace("%", "").trim();
            BigDecimal result = new BigDecimal(cleaned);

            // Convert to decimal (e.g., 5.5% -> 0.055)
            return result.divide(BigDecimal.valueOf(100), 4, java.math.RoundingMode.HALF_UP);
        } catch (NumberFormatException e) {
            logger.error("Error converting string to percentage: " + value, e);
            return BigDecimal.ZERO;
        }
    }

    /**
     * Convert percentage to string
     */
    public static String percentageToString(BigDecimal value) {
        if (value == null) {
            return "0%";
        }

        BigDecimal percentage = value.multiply(BigDecimal.valueOf(100));
        return String.format("%.2f%%", percentage);
    }

    /**
     * Convert string to LocalDate
     */
    public static LocalDate stringToLocalDate(String value) {
        if (CommonUtil.isNullOrEmpty(value)) {
            return null;
        }

        // Try different date patterns
        String[] patterns = { DATE_PATTERN_YYYY_MM_DD, DATE_PATTERN_DD_MM_YYYY, DATE_PATTERN_MM_DD_YYYY };

        for (String pattern : patterns) {
            try {
                return LocalDate.parse(value.trim(), DateTimeFormatter.ofPattern(pattern));
            } catch (DateTimeParseException e) {
                // Continue to next pattern
            }
        }

        logger.error("Error converting string to LocalDate: " + value);
        return null;
    }

    /**
     * Convert LocalDate to string
     */
    public static String localDateToString(LocalDate date) {
        if (date == null) {
            return "";
        }

        return date.format(DateTimeFormatter.ofPattern(DATE_PATTERN_YYYY_MM_DD));
    }

    /**
     * Convert LocalDate to string with custom pattern
     */
    public static String localDateToString(LocalDate date, String pattern) {
        if (date == null) {
            return "";
        }

        try {
            return date.format(DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            logger.error("Error formatting date with pattern: " + pattern, e);
            return localDateToString(date);
        }
    }

    /**
     * Convert string to LocalDateTime
     */
    public static LocalDateTime stringToLocalDateTime(String value) {
        if (CommonUtil.isNullOrEmpty(value)) {
            return null;
        }

        // Try different datetime patterns
        String[] patterns = { DATETIME_PATTERN_YYYY_MM_DD_HH_MM_SS, DATETIME_PATTERN_ISO };

        for (String pattern : patterns) {
            try {
                return LocalDateTime.parse(value.trim(), DateTimeFormatter.ofPattern(pattern));
            } catch (DateTimeParseException e) {
                // Continue to next pattern
            }
        }

        logger.error("Error converting string to LocalDateTime: " + value);
        return null;
    }

    /**
     * Convert LocalDateTime to string
     */
    public static String localDateTimeToString(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }

        return dateTime.format(DateTimeFormatter.ofPattern(DATETIME_PATTERN_YYYY_MM_DD_HH_MM_SS));
    }

    /**
     * Convert LocalDateTime to string with custom pattern
     */
    public static String localDateTimeToString(LocalDateTime dateTime, String pattern) {
        if (dateTime == null) {
            return "";
        }

        try {
            return dateTime.format(DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            logger.error("Error formatting datetime with pattern: " + pattern, e);
            return localDateTimeToString(dateTime);
        }
    }

    /**
     * Convert string to Integer
     */
    public static Integer stringToInteger(String value) {
        if (CommonUtil.isNullOrEmpty(value)) {
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
     * Convert string to Integer with default value
     */
    public static Integer stringToInteger(String value, Integer defaultValue) {
        Integer result = stringToInteger(value);
        return result != null ? result : defaultValue;
    }

    /**
     * Convert string to Long
     */
    public static Long stringToLong(String value) {
        if (CommonUtil.isNullOrEmpty(value)) {
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
     * Convert string to Long with default value
     */
    public static Long stringToLong(String value, Long defaultValue) {
        Long result = stringToLong(value);
        return result != null ? result : defaultValue;
    }

    /**
     * Convert string to Double
     */
    public static Double stringToDouble(String value) {
        if (CommonUtil.isNullOrEmpty(value)) {
            return null;
        }

        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            logger.error("Error converting string to Double: " + value, e);
            return null;
        }
    }

    /**
     * Convert string to Double with default value
     */
    public static Double stringToDouble(String value, Double defaultValue) {
        Double result = stringToDouble(value);
        return result != null ? result : defaultValue;
    }

    /**
     * Convert string to Boolean
     */
    public static Boolean stringToBoolean(String value) {
        if (CommonUtil.isNullOrEmpty(value)) {
            return null;
        }

        String lowerValue = value.trim().toLowerCase();

        if ("true".equals(lowerValue) || "yes".equals(lowerValue) || "1".equals(lowerValue) || "y".equals(lowerValue)) {
            return true;
        } else if ("false".equals(lowerValue) || "no".equals(lowerValue) || "0".equals(lowerValue)
                || "n".equals(lowerValue)) {
            return false;
        }

        logger.error("Error converting string to Boolean: " + value);
        return null;
    }

    /**
     * Convert Boolean to string
     */
    public static String booleanToString(Boolean value) {
        if (value == null) {
            return "";
        }

        return value ? "true" : "false";
    }

    /**
     * Convert string to Base64
     */
    public static String stringToBase64(String value) {
        if (CommonUtil.isNullOrEmpty(value)) {
            return "";
        }

        try {
            return Base64.getEncoder().encodeToString(value.getBytes());
        } catch (Exception e) {
            logger.error("Error converting string to Base64: " + value, e);
            return "";
        }
    }

    /**
     * Convert Base64 to string
     */
    public static String base64ToString(String value) {
        if (CommonUtil.isNullOrEmpty(value)) {
            return "";
        }

        try {
            byte[] decodedBytes = Base64.getDecoder().decode(value);
            return new String(decodedBytes);
        } catch (Exception e) {
            logger.error("Error converting Base64 to string: " + value, e);
            return "";
        }
    }

    /**
     * Convert string to uppercase
     */
    public static String toUpperCase(String value) {
        if (CommonUtil.isNullOrEmpty(value)) {
            return value;
        }

        return value.trim().toUpperCase();
    }

    /**
     * Convert string to lowercase
     */
    public static String toLowerCase(String value) {
        if (CommonUtil.isNullOrEmpty(value)) {
            return value;
        }

        return value.trim().toLowerCase();
    }

    /**
     * Convert string to title case
     */
    public static String toTitleCase(String value) {
        if (CommonUtil.isNullOrEmpty(value)) {
            return value;
        }

        String[] words = value.trim().toLowerCase().split("\\s+");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                result.append(" ");
            }
            if (words[i].length() > 0) {
                result.append(Character.toUpperCase(words[i].charAt(0)))
                        .append(words[i].substring(1));
            }
        }

        return result.toString();
    }

    /**
     * Convert string to camel case
     */
    public static String toCamelCase(String value) {
        if (CommonUtil.isNullOrEmpty(value)) {
            return value;
        }

        String[] words = value.trim().toLowerCase().split("[\\s_-]+");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            if (words[i].length() > 0) {
                if (i == 0) {
                    result.append(words[i]);
                } else {
                    result.append(Character.toUpperCase(words[i].charAt(0)))
                            .append(words[i].substring(1));
                }
            }
        }

        return result.toString();
    }

    /**
     * Convert string to snake case
     */
    public static String toSnakeCase(String value) {
        if (CommonUtil.isNullOrEmpty(value)) {
            return value;
        }

        return value.trim().toLowerCase().replaceAll("[\\s-]+", "_");
    }

    /**
     * Convert string to kebab case
     */
    public static String toKebabCase(String value) {
        if (CommonUtil.isNullOrEmpty(value)) {
            return value;
        }

        return value.trim().toLowerCase().replaceAll("[\\s_]+", "-");
    }

    /**
     * Validate currency format
     */
    public static boolean isValidCurrency(String value) {
        if (CommonUtil.isNullOrEmpty(value)) {
            return false;
        }

        return CURRENCY_PATTERN.matcher(value.trim()).matches();
    }

    /**
     * Validate percentage format
     */
    public static boolean isValidPercentage(String value) {
        if (CommonUtil.isNullOrEmpty(value)) {
            return false;
        }

        return PERCENTAGE_PATTERN.matcher(value.trim()).matches();
    }

    /**
     * Validate date format
     */
    public static boolean isValidDate(String value) {
        return stringToLocalDate(value) != null;
    }

    /**
     * Validate datetime format
     */
    public static boolean isValidDateTime(String value) {
        return stringToLocalDateTime(value) != null;
    }

    /**
     * Validate numeric format
     */
    public static boolean isValidNumeric(String value) {
        if (CommonUtil.isNullOrEmpty(value)) {
            return false;
        }

        try {
            Double.parseDouble(value.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validate integer format
     */
    public static boolean isValidInteger(String value) {
        if (CommonUtil.isNullOrEmpty(value)) {
            return false;
        }

        try {
            Integer.parseInt(value.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private DataConverter() {
        // Private constructor to prevent instantiation
    }
}