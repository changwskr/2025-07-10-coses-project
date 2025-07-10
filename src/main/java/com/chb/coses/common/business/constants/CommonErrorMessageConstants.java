package com.chb.coses.common.business.constants;

/**
 * Common Error Message Constants
 */
public class CommonErrorMessageConstants {

    // System error messages
    public static final String SYSTEM_ERROR = "SYSTEM_ERROR";
    public static final String DATABASE_ERROR = "DATABASE_ERROR";
    public static final String NETWORK_ERROR = "NETWORK_ERROR";
    public static final String TIMEOUT_ERROR = "TIMEOUT_ERROR";

    // Validation error messages
    public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
    public static final String REQUIRED_FIELD_MISSING = "REQUIRED_FIELD_MISSING";
    public static final String INVALID_FORMAT = "INVALID_FORMAT";
    public static final String INVALID_LENGTH = "INVALID_LENGTH";

    // Business error messages
    public static final String BUSINESS_ERROR = "BUSINESS_ERROR";
    public static final String INSUFFICIENT_BALANCE = "INSUFFICIENT_BALANCE";
    public static final String ACCOUNT_NOT_FOUND = "ACCOUNT_NOT_FOUND";
    public static final String CARD_NOT_FOUND = "CARD_NOT_FOUND";
    public static final String TRANSACTION_FAILED = "TRANSACTION_FAILED";

    // Authentication error messages
    public static final String AUTHENTICATION_ERROR = "AUTHENTICATION_ERROR";
    public static final String INVALID_PIN = "INVALID_PIN";
    public static final String CARD_BLOCKED = "CARD_BLOCKED";
    public static final String CARD_EXPIRED = "CARD_EXPIRED";

    private CommonErrorMessageConstants() {
        // Utility class - prevent instantiation
    }
}