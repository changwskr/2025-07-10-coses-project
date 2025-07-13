package com.skcc.oversea.framework.constant;

/**
 * Framework Constants for SKCC Oversea
 * 
 * Defines framework-specific constants used throughout the SKCC Oversea system.
 */
public class FrameworkConstants {

    // Framework Constants
    public static final String FRAMEWORK_NAME = "SKCC_OVERSEA_FRAMEWORK";
    public static final String FRAMEWORK_VERSION = "1.0.0";

    // Transaction Framework Constants
    public static final String TCF_PREFIX = "TCF";
    public static final String STF_PREFIX = "STF";
    public static final String ETF_PREFIX = "ETF";
    public static final String BTF_PREFIX = "BTF";

    // Transaction Status
    public static final String TXN_STATUS_INITIATED = "INITIATED";
    public static final String TXN_STATUS_PROCESSING = "PROCESSING";
    public static final String TXN_STATUS_COMPLETED = "COMPLETED";
    public static final String TXN_STATUS_FAILED = "FAILED";
    public static final String TXN_STATUS_ROLLBACK = "ROLLBACK";

    // Transaction Types
    public static final String TXN_TYPE_READ = "READ";
    public static final String TXN_TYPE_WRITE = "WRITE";
    public static final String TXN_TYPE_UPDATE = "UPDATE";
    public static final String TXN_TYPE_DELETE = "DELETE";

    // Error Codes
    public static final String ERROR_CODE_FRAMEWORK_ERROR = "FWK001";
    public static final String ERROR_CODE_TRANSACTION_ERROR = "FWK002";
    public static final String ERROR_CODE_VALIDATION_ERROR = "FWK003";
    public static final String ERROR_CODE_TIMEOUT_ERROR = "FWK004";
    public static final String ERROR_CODE_ROLLBACK_ERROR = "FWK005";

    // Timeout Constants
    public static final int DEFAULT_TRANSACTION_TIMEOUT = 30000; // 30 seconds
    public static final int DEFAULT_QUERY_TIMEOUT = 10000; // 10 seconds
    public static final int DEFAULT_CONNECTION_TIMEOUT = 5000; // 5 seconds

    // Retry Constants
    public static final int DEFAULT_MAX_RETRY_ATTEMPTS = 3;
    public static final int DEFAULT_RETRY_DELAY = 1000; // 1 second

    // Cache Constants
    public static final String CACHE_TRANSACTION = "transaction";
    public static final String CACHE_SESSION = "session";
    public static final int CACHE_TTL_TRANSACTION = 300; // 5 minutes
    public static final int CACHE_TTL_SESSION = 3600; // 1 hour

    // Logging Constants
    public static final String LOG_PREFIX_FRAMEWORK = "[FRAMEWORK]";
    public static final String LOG_PREFIX_TRANSACTION = "[TRANSACTION]";
    public static final String LOG_PREFIX_VALIDATION = "[VALIDATION]";
    public static final String LOG_PREFIX_CACHE = "[CACHE]";

    // Validation Constants
    public static final int MAX_TRANSACTION_ID_LENGTH = 50;
    public static final int MAX_SESSION_ID_LENGTH = 100;
    public static final int MAX_ERROR_MESSAGE_LENGTH = 500;

    // Session Constants
    public static final String SESSION_ATTRIBUTE_USER_ID = "userId";
    public static final String SESSION_ATTRIBUTE_USER_ROLE = "userRole";
    public static final String SESSION_ATTRIBUTE_TRANSACTION_ID = "transactionId";
    public static final String SESSION_ATTRIBUTE_TIMESTAMP = "timestamp";

    // Security Constants
    public static final String SECURITY_ROLE_ADMIN = "ADMIN";
    public static final String SECURITY_ROLE_USER = "USER";
    public static final String SECURITY_ROLE_GUEST = "GUEST";

    // Performance Constants
    public static final int SLOW_QUERY_THRESHOLD = 5000; // 5 seconds
    public static final int SLOW_TRANSACTION_THRESHOLD = 10000; // 10 seconds

    // Monitoring Constants
    public static final String METRIC_TRANSACTION_COUNT = "transaction.count";
    public static final String METRIC_TRANSACTION_DURATION = "transaction.duration";
    public static final String METRIC_ERROR_COUNT = "error.count";
    public static final String METRIC_CACHE_HIT_RATE = "cache.hit.rate";

    private FrameworkConstants() {
        // Private constructor to prevent instantiation
    }
}