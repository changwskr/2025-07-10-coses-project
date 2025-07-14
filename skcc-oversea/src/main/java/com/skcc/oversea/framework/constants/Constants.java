package com.skcc.oversea.framework.constants;

import java.math.BigDecimal;

/**
 * Framework constants
 * Replaces com.chb.coses.framework.constants.Constants
 */
public class Constants {

    // Numeric constants
    public static final BigDecimal ZERO = BigDecimal.ZERO;
    public static final BigDecimal ONE = BigDecimal.ONE;

    // String constants
    public static final String BLANK = "";
    public static final String SPACE = " ";
    public static final String COMMA = ",";
    public static final String DOT = ".";
    public static final String COLON = ":";
    public static final String SEMICOLON = ";";
    public static final String EQUALS = "=";
    public static final String PIPE = "|";
    public static final String UNDERSCORE = "_";
    public static final String DASH = "-";
    public static final String SLASH = "/";
    public static final String BACKSLASH = "\\";
    public static final String ASTERISK = "*";
    public static final String QUESTION_MARK = "?";
    public static final String EXCLAMATION_MARK = "!";
    public static final String AT_SIGN = "@";
    public static final String HASH = "#";
    public static final String DOLLAR = "$";
    public static final String PERCENT = "%";
    public static final String CARET = "^";
    public static final String AMPERSAND = "&";
    public static final String PLUS = "+";
    public static final String TILDE = "~";
    public static final String BACKTICK = "`";
    public static final String QUOTE = "'";
    public static final String DOUBLE_QUOTE = "\"";
    public static final String LEFT_PARENTHESIS = "(";
    public static final String RIGHT_PARENTHESIS = ")";
    public static final String LEFT_BRACKET = "[";
    public static final String RIGHT_BRACKET = "]";
    public static final String LEFT_BRACE = "{";
    public static final String RIGHT_BRACE = "}";
    public static final String LEFT_ANGLE = "<";
    public static final String RIGHT_ANGLE = ">";

    // Line separator
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String NEW_LINE = "\n";
    public static final String CARRIAGE_RETURN = "\r";
    public static final String TAB = "\t";

    // Date format constants
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_FORMAT_MM_DD_YYYY = "MM/dd/yyyy";
    public static final String DATE_FORMAT_DD_MM_YYYY = "dd/MM/yyyy";
    public static final String TIME_FORMAT_HH_MM_SS = "HH:mm:ss";
    public static final String TIME_FORMAT_HHMMSS = "HHmmss";
    public static final String DATETIME_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_FORMAT_YYYYMMDD_HHMMSS = "yyyyMMdd HHmmss";

    // Transaction status constants
    public static final String TXN_STATUS_SUCCESS = "S";
    public static final String TXN_STATUS_FAILED = "F";
    public static final String TXN_STATUS_PENDING = "P";
    public static final String TXN_STATUS_CANCELLED = "C";
    public static final String TXN_STATUS_TIMEOUT = "T";

    // Status constants
    public static final String STATUS_ACTIVE = "A";
    public static final String STATUS_INACTIVE = "I";
    public static final String STATUS_DELETED = "D";
    public static final String STATUS_PENDING = "P";
    public static final String STATUS_COMPLETED = "C";
    public static final String STATUS_FAILED = "F";
    public static final String STATUS_CANCELLED = "X";

    // Yes/No constants
    public static final String YES = "Y";
    public static final String NO = "N";

    // Currency constants
    public static final String CURRENCY_USD = "USD";
    public static final String CURRENCY_EUR = "EUR";
    public static final String CURRENCY_KRW = "KRW";
    public static final String CURRENCY_JPY = "JPY";
    public static final String CURRENCY_CNY = "CNY";

    // Card type constants
    public static final String CARD_TYPE_DEBIT = "DEBIT";
    public static final String CARD_TYPE_CREDIT = "CREDIT";
    public static final String CARD_TYPE_PREPAID = "PREPAID";

    // Transaction type constants
    public static final String TX_TYPE_CREDIT = "CR";
    public static final String TX_TYPE_DEBIT = "DB";
    public static final String TX_TYPE_TRANSFER = "TR";
    public static final String TX_TYPE_PAYMENT = "PAY";
    public static final String TX_TYPE_REFUND = "REF";

    // Error code constants
    public static final String ERROR_INVALID_ACCOUNT = "E001";
    public static final String ERROR_INSUFFICIENT_FUNDS = "E002";
    public static final String ERROR_INVALID_CARD = "E003";
    public static final String ERROR_TRANSACTION_FAILED = "E004";
    public static final String ERROR_SYSTEM_ERROR = "E999";

    // Success code constants
    public static final String SUCCESS_TRANSACTION = "S001";
    public static final String SUCCESS_ACCOUNT_CREATED = "S002";
    public static final String SUCCESS_CARD_ISSUED = "S003";

    // Default values
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int DEFAULT_PAGE_NUMBER = 1;
    public static final int MAX_PAGE_SIZE = 1000;

    // Time constants
    public static final long MILLISECONDS_PER_SECOND = 1000L;
    public static final long SECONDS_PER_MINUTE = 60L;
    public static final long MINUTES_PER_HOUR = 60L;
    public static final long HOURS_PER_DAY = 24L;
    public static final long DAYS_PER_WEEK = 7L;
    public static final long DAYS_PER_MONTH = 30L;
    public static final long DAYS_PER_YEAR = 365L;

    // File size constants
    public static final long BYTES_PER_KB = 1024L;
    public static final long BYTES_PER_MB = 1024L * 1024L;
    public static final long BYTES_PER_GB = 1024L * 1024L * 1024L;

    // Encoding constants
    public static final String ENCODING_UTF8 = "UTF-8";
    public static final String ENCODING_ISO8859_1 = "ISO-8859-1";
    public static final String ENCODING_EUC_KR = "EUC-KR";

    // HTTP constants
    public static final String HTTP_METHOD_GET = "GET";
    public static final String HTTP_METHOD_POST = "POST";
    public static final String HTTP_METHOD_PUT = "PUT";
    public static final String HTTP_METHOD_DELETE = "DELETE";

    // Content type constants
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_XML = "application/xml";
    public static final String CONTENT_TYPE_TEXT = "text/plain";
    public static final String CONTENT_TYPE_HTML = "text/html";
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";

    // Database constants
    public static final String DB_TYPE_ORACLE = "ORACLE";
    public static final String DB_TYPE_MYSQL = "MYSQL";
    public static final String DB_TYPE_POSTGRESQL = "POSTGRESQL";
    public static final String DB_TYPE_MSSQL = "MSSQL";

    // Log level constants
    public static final String LOG_LEVEL_TRACE = "TRACE";
    public static final String LOG_LEVEL_DEBUG = "DEBUG";
    public static final String LOG_LEVEL_INFO = "INFO";
    public static final String LOG_LEVEL_WARN = "WARN";
    public static final String LOG_LEVEL_ERROR = "ERROR";
    public static final String LOG_LEVEL_FATAL = "FATAL";

    // System constants
    public static final String SYSTEM_NAME = "SKCC_OVERSEA";
    public static final String VERSION = "1.0.0";

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

    // Channel constants
    public static final String CHANNEL_TYPE_BRANCH = "BRANCH";
    public static final String CHANNEL_TYPE_ATM = "ATM";
    public static final String CHANNEL_TYPE_INTERNET = "INTERNET";
    public static final String CHANNEL_TYPE_MOBILE = "MOBILE";
    public static final String CHANNEL_TYPE_PHONE = "PHONE";

    // Logging constants (duplicates removed)

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

    // Pagination constants (duplicates removed)

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