package com.skcc.oversea.framework.constants;

/**
 * Framework constants
 * Replaces com.chb.coses.framework.constants.Constants
 */
public class Constants {

    // System constants
    public static final String SYSTEM_NAME = "SKCC_OVERSEA";
    public static final String VERSION = "1.0.0";

    // Status constants
    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String STATUS_FAILURE = "FAILURE";
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_INACTIVE = "INACTIVE";

    // Error codes
    public static final String ERROR_CODE_SUCCESS = "0000";
    public static final String ERROR_CODE_SYSTEM_ERROR = "9999";
    public static final String ERROR_CODE_VALIDATION_ERROR = "1001";
    public static final String ERROR_CODE_BUSINESS_ERROR = "2001";
    public static final String ERROR_CODE_DATABASE_ERROR = "3001";

    // Transaction constants
    public static final String TRANSACTION_TYPE_DEBIT = "DEBIT";
    public static final String TRANSACTION_TYPE_CREDIT = "CREDIT";
    public static final String TRANSACTION_TYPE_TRANSFER = "TRANSFER";

    // Account constants
    public static final String ACCOUNT_TYPE_SAVINGS = "SAVINGS";
    public static final String ACCOUNT_TYPE_CURRENT = "CURRENT";
    public static final String ACCOUNT_TYPE_FIXED = "FIXED";
    public static final String ACCOUNT_TYPE_INVESTMENT = "INVESTMENT";

    // Card constants
    public static final String CARD_TYPE_DEBIT = "DEBIT";
    public static final String CARD_TYPE_CREDIT = "CREDIT";
    public static final String CARD_TYPE_PREPAID = "PREPAID";

    // Currency constants
    public static final String CURRENCY_KRW = "KRW";
    public static final String CURRENCY_USD = "USD";
    public static final String CURRENCY_EUR = "EUR";
    public static final String CURRENCY_JPY = "JPY";

    // Channel constants
    public static final String CHANNEL_TYPE_BRANCH = "BRANCH";
    public static final String CHANNEL_TYPE_ATM = "ATM";
    public static final String CHANNEL_TYPE_INTERNET = "INTERNET";
    public static final String CHANNEL_TYPE_MOBILE = "MOBILE";
    public static final String CHANNEL_TYPE_PHONE = "PHONE";

    // Date format constants
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String TIME_FORMAT_HHMMSS = "HHmmss";
    public static final String TIME_FORMAT_HH_MM_SS = "HH:mm:ss";
    public static final String DATETIME_FORMAT_YYYYMMDD_HHMMSS = "yyyyMMddHHmmss";

    // Database constants
    public static final String DB_DRIVER_MYSQL = "com.mysql.cj.jdbc.Driver";
    public static final String DB_DRIVER_POSTGRESQL = "org.postgresql.Driver";
    public static final String DB_DRIVER_ORACLE = "oracle.jdbc.driver.OracleDriver";

    // Logging constants
    public static final String LOG_LEVEL_DEBUG = "DEBUG";
    public static final String LOG_LEVEL_INFO = "INFO";
    public static final String LOG_LEVEL_WARN = "WARN";
    public static final String LOG_LEVEL_ERROR = "ERROR";

    // Security constants
    public static final String ENCRYPTION_ALGORITHM_AES = "AES";
    public static final String ENCRYPTION_ALGORITHM_DES = "DES";
    public static final String HASH_ALGORITHM_SHA256 = "SHA-256";
    public static final String HASH_ALGORITHM_MD5 = "MD5";

    // File constants
    public static final String FILE_ENCODING_UTF8 = "UTF-8";
    public static final String FILE_ENCODING_EUC_KR = "EUC-KR";
    public static final int MAX_FILE_SIZE = 10485760; // 10MB

    // Network constants
    public static final int DEFAULT_TIMEOUT = 30000; // 30 seconds
    public static final int DEFAULT_CONNECTION_TIMEOUT = 10000; // 10 seconds
    public static final int DEFAULT_READ_TIMEOUT = 30000; // 30 seconds

    // Cache constants
    public static final int CACHE_DEFAULT_TTL = 3600; // 1 hour
    public static final int CACHE_SHORT_TTL = 300; // 5 minutes
    public static final int CACHE_LONG_TTL = 86400; // 24 hours

    // Pagination constants
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 1000;

    // Validation constants
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 20;
    public static final int MIN_USERNAME_LENGTH = 3;
    public static final int MAX_USERNAME_LENGTH = 20;

    // Business constants
    public static final String BUSINESS_DATE_FORMAT = "yyyyMMdd";
    public static final String BUSINESS_TIME_FORMAT = "HHmmss";
    public static final String BUSINESS_DATETIME_FORMAT = "yyyyMMddHHmmss";

    // API constants
    public static final String API_VERSION_V1 = "v1";
    public static final String API_VERSION_V2 = "v2";
    public static final String API_CONTENT_TYPE_JSON = "application/json";
    public static final String API_CONTENT_TYPE_XML = "application/xml";

    // Message constants
    public static final String MESSAGE_SUCCESS = "Success";
    public static final String MESSAGE_FAILURE = "Failure";
    public static final String MESSAGE_VALIDATION_ERROR = "Validation error";
    public static final String MESSAGE_SYSTEM_ERROR = "System error";
    public static final String MESSAGE_BUSINESS_ERROR = "Business error";

    // Default values
    public static final String DEFAULT_LANGUAGE = "ko";
    public static final String DEFAULT_COUNTRY = "KR";
    public static final String DEFAULT_TIMEZONE = "Asia/Seoul";
    public static final String DEFAULT_CURRENCY = "KRW";

    // Private constructor to prevent instantiation
    private Constants() {
        throw new UnsupportedOperationException("Constants class cannot be instantiated");
    }
}