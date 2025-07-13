package com.skcc.oversea.eplatonframework.business.constant;

/**
 * Business Constants for SKCC Oversea
 * 
 * Defines common constants used across business operations.
 */
public class BusinessConstants {

    // Status Constants
    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String STATUS_ERROR = "ERROR";
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_CANCELLED = "CANCELLED";

    // Error Code Prefixes
    public static final String ERROR_PREFIX_VALIDATION = "EVAL";
    public static final String ERROR_PREFIX_BUSINESS = "EBUS";
    public static final String ERROR_PREFIX_SYSTEM = "ESYS";
    public static final String ERROR_PREFIX_DATABASE = "EDB";
    public static final String ERROR_PREFIX_NETWORK = "ENET";

    // Common Error Codes
    public static final String ERROR_INVALID_REQUEST = "EVAL001";
    public static final String ERROR_MISSING_REQUIRED_FIELD = "EVAL002";
    public static final String ERROR_INVALID_FORMAT = "EVAL003";
    public static final String ERROR_BUSINESS_RULE_VIOLATION = "EBUS001";
    public static final String ERROR_SYSTEM_ERROR = "ESYS001";
    public static final String ERROR_DATABASE_ERROR = "EDB001";
    public static final String ERROR_NETWORK_ERROR = "ENET001";

    // Transaction Types
    public static final String TXN_TYPE_CREATE = "CREATE";
    public static final String TXN_TYPE_UPDATE = "UPDATE";
    public static final String TXN_TYPE_DELETE = "DELETE";
    public static final String TXN_TYPE_READ = "READ";
    public static final String TXN_TYPE_TRANSFER = "TRANSFER";
    public static final String TXN_TYPE_WITHDRAW = "WITHDRAW";
    public static final String TXN_TYPE_DEPOSIT = "DEPOSIT";

    // Card Status
    public static final String CARD_STATUS_ACTIVE = "ACTIVE";
    public static final String CARD_STATUS_BLOCKED = "BLOCKED";
    public static final String CARD_STATUS_EXPIRED = "EXPIRED";
    public static final String CARD_STATUS_CANCELLED = "CANCELLED";

    // Deposit Types
    public static final String DEPOSIT_TYPE_SAVINGS = "SAVINGS";
    public static final String DEPOSIT_TYPE_CURRENT = "CURRENT";
    public static final String DEPOSIT_TYPE_FIXED = "FIXED";
    public static final String DEPOSIT_TYPE_RECURRING = "RECURRING";

    // User Types
    public static final String USER_TYPE_ADMIN = "ADMIN";
    public static final String USER_TYPE_TELLER = "TELLER";
    public static final String USER_TYPE_CUSTOMER = "CUSTOMER";
    public static final String USER_TYPE_SYSTEM = "SYSTEM";

    // Session Status
    public static final String SESSION_STATUS_ACTIVE = "ACTIVE";
    public static final String SESSION_STATUS_EXPIRED = "EXPIRED";
    public static final String SESSION_STATUS_LOGGED_OUT = "LOGGED_OUT";

    // Audit Action Types
    public static final String AUDIT_ACTION_LOGIN = "LOGIN";
    public static final String AUDIT_ACTION_LOGOUT = "LOGOUT";
    public static final String AUDIT_ACTION_CREATE = "CREATE";
    public static final String AUDIT_ACTION_UPDATE = "UPDATE";
    public static final String AUDIT_ACTION_DELETE = "DELETE";
    public static final String AUDIT_ACTION_VIEW = "VIEW";

    // Configuration Keys
    public static final String CONFIG_MAX_TRANSACTION_AMOUNT = "MAX_TRANSACTION_AMOUNT";
    public static final String CONFIG_SESSION_TIMEOUT = "SESSION_TIMEOUT";
    public static final String CONFIG_MAX_LOGIN_ATTEMPTS = "MAX_LOGIN_ATTEMPTS";
    public static final String CONFIG_SYSTEM_MAINTENANCE_MODE = "SYSTEM_MAINTENANCE_MODE";

    // Date Formats
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT_YYYYMMDD_HHMMSS = "yyyyMMddHHmmss";
    public static final String DATETIME_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    // Currency Codes
    public static final String CURRENCY_KRW = "KRW";
    public static final String CURRENCY_USD = "USD";
    public static final String CURRENCY_EUR = "EUR";
    public static final String CURRENCY_JPY = "JPY";

    // Language Codes
    public static final String LANGUAGE_KO = "ko";
    public static final String LANGUAGE_EN = "en";
    public static final String LANGUAGE_JA = "ja";
    public static final String LANGUAGE_ZH = "zh";

    // Default Values
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int DEFAULT_MAX_PAGE_SIZE = 100;
    public static final String DEFAULT_LANGUAGE = LANGUAGE_KO;
    public static final String DEFAULT_CURRENCY = CURRENCY_KRW;

    // Cache Keys
    public static final String CACHE_KEY_USER_SESSION = "USER_SESSION";
    public static final String CACHE_KEY_SYSTEM_CONFIG = "SYSTEM_CONFIG";
    public static final String CACHE_KEY_REFERENCE_DATA = "REFERENCE_DATA";

    // Timeout Values (in seconds)
    public static final int TIMEOUT_SESSION = 1800; // 30 minutes
    public static final int TIMEOUT_TRANSACTION = 300; // 5 minutes
    public static final int TIMEOUT_DATABASE = 30; // 30 seconds
    public static final int TIMEOUT_NETWORK = 60; // 60 seconds

    // Private constructor to prevent instantiation
    private BusinessConstants() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
}