package com.skcc.oversea.eplatonframework.business.util;

import org.springframework.util.StringUtils;
import com.skcc.oversea.eplatonframework.transfer.EPlatonEvent;
import com.skcc.oversea.eplatonframework.transfer.TPSVCINFODTO;

/**
 * Validation Utility for SKCC Oversea
 * 
 * Provides common validation methods for business operations.
 */
public class ValidationUtil {

    /**
     * Validate EPlatonEvent
     */
    public static boolean isValidEvent(EPlatonEvent event) {
        if (event == null) {
            return false;
        }

        TPSVCINFODTO tpsvcinfo = event.getTPSVCINFODTO();
        if (tpsvcinfo == null) {
            return false;
        }

        return StringUtils.hasText(tpsvcinfo.getReqName());
    }

    /**
     * Validate request data
     */
    public static boolean isValidRequestData(Object requestData) {
        return requestData != null;
    }

    /**
     * Validate string parameter
     */
    public static boolean isValidString(String value) {
        return StringUtils.hasText(value);
    }

    /**
     * Validate numeric parameter
     */
    public static boolean isValidNumber(String value) {
        if (!StringUtils.hasText(value)) {
            return false;
        }

        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validate required fields
     */
    public static boolean hasRequiredFields(EPlatonEvent event, String... requiredFields) {
        if (!isValidEvent(event)) {
            return false;
        }

        TPSVCINFODTO tpsvcinfo = event.getTPSVCINFODTO();

        for (String field : requiredFields) {
            if (!StringUtils.hasText(tpsvcinfo.getReqName())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Validate business rule
     */
    public static boolean validateBusinessRule(boolean condition, String ruleName) {
        if (!condition) {
            throw new IllegalArgumentException("Business rule violation: " + ruleName);
        }
        return true;
    }

    /**
     * Validate date format
     */
    public static boolean isValidDateFormat(String dateStr, String format) {
        if (!StringUtils.hasText(dateStr)) {
            return false;
        }

        try {
            java.time.format.DateTimeFormatter.ofPattern(format).parse(dateStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Validate email format
     */
    public static boolean isValidEmail(String email) {
        if (!StringUtils.hasText(email)) {
            return false;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    /**
     * Validate phone number format
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (!StringUtils.hasText(phoneNumber)) {
            return false;
        }

        // Remove all non-digit characters
        String digitsOnly = phoneNumber.replaceAll("\\D", "");

        // Check if it's a valid phone number (7-15 digits)
        return digitsOnly.length() >= 7 && digitsOnly.length() <= 15;
    }

    /**
     * Validate amount
     */
    public static boolean isValidAmount(String amount) {
        if (!isValidNumber(amount)) {
            return false;
        }

        try {
            double value = Double.parseDouble(amount);
            return value >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validate account number format
     */
    public static boolean isValidAccountNumber(String accountNumber) {
        if (!StringUtils.hasText(accountNumber)) {
            return false;
        }

        // Remove spaces and dashes
        String cleanAccount = accountNumber.replaceAll("[\\s-]", "");

        // Check if it contains only digits and has reasonable length
        return cleanAccount.matches("\\d+") && cleanAccount.length() >= 8 && cleanAccount.length() <= 20;
    }
}