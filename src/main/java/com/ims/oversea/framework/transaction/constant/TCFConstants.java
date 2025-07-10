package com.ims.oversea.framework.transaction.constant;

/**
 * TCF (Transaction Control Framework) Constants
 */
public class TCFConstants {

    // Log levels
    public static final int DEBUG_LOG_LEVEL = 1;
    public static final int GENERAL_LOG_LEVEL = 2;
    public static final int WARNING_LOG_LEVEL = 3;
    public static final int ERROR_LOG_LEVEL = 4;
    public static final int FATAL_LOG_LEVEL = 5;

    // Transaction types
    public static final String TRANSACTION_TYPE_INPUT = "INPUT";
    public static final String TRANSACTION_TYPE_OUTPUT = "OUTPUT";
    public static final String TRANSACTION_TYPE_INTERNAL = "INTERNAL";

    // Transaction status
    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String STATUS_FAILED = "FAILED";
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_ERROR = "ERROR";

    // Event types
    public static final String EVENT_TYPE_REQUEST = "REQUEST";
    public static final String EVENT_TYPE_RESPONSE = "RESPONSE";
    public static final String EVENT_TYPE_NOTIFICATION = "NOTIFICATION";

    // Channel types
    public static final String CHANNEL_TYPE_WEB = "WEB";
    public static final String CHANNEL_TYPE_MOBILE = "MOBILE";
    public static final String CHANNEL_TYPE_BATCH = "BATCH";
    public static final String CHANNEL_TYPE_API = "API";

    // System codes
    public static final String SYSTEM_CODE_CORE = "COR";
    public static final String SYSTEM_CODE_IMS = "IMS";
    public static final String SYSTEM_CODE_KDB = "KDB";

    // Error codes
    public static final String ERROR_CODE_SUCCESS = "0000";
    public static final String ERROR_CODE_SYSTEM_ERROR = "9999";
    public static final String ERROR_CODE_VALIDATION_ERROR = "1001";
    public static final String ERROR_CODE_DATABASE_ERROR = "2001";
    public static final String ERROR_CODE_NETWORK_ERROR = "3001";

    // Default values
    public static final String DEFAULT_BANK_CODE = "001";
    public static final String DEFAULT_BRANCH_CODE = "000";
    public static final String DEFAULT_USER_ID = "SYSTEM";

    // Timeout values (in milliseconds)
    public static final long DEFAULT_TIMEOUT = 30000; // 30 seconds
    public static final long SHORT_TIMEOUT = 5000; // 5 seconds
    public static final long LONG_TIMEOUT = 120000; // 2 minutes

    // Database related
    public static final String DB_POOL_NAME = "jdbc/CosesPool";
    public static final String JNDI_CONTEXT = "java:comp/env";

    // Configuration keys
    public static final String CONFIG_KEY_LOG_LEVEL = "log.level";
    public static final String CONFIG_KEY_TIMEOUT = "timeout";
    public static final String CONFIG_KEY_DB_POOL = "db.pool";

    private TCFConstants() {
        // Prevent instantiation
    }
}