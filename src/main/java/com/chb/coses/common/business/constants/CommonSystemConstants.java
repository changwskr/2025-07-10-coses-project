package com.chb.coses.common.business.constants;

/**
 * Common System Constants
 */
public class CommonSystemConstants {

    // System status
    public static final String STATUS_ACTIVE = "A";
    public static final String STATUS_INACTIVE = "I";
    public static final String STATUS_DELETED = "D";
    public static final String STATUS_PENDING = "P";

    // Transaction status
    public static final String TRANSACTION_SUCCESS = "S";
    public static final String TRANSACTION_FAILURE = "F";
    public static final String TRANSACTION_PENDING = "P";
    public static final String TRANSACTION_CANCELLED = "C";

    // Card status
    public static final String CARD_STATUS_ACTIVE = "A";
    public static final String CARD_STATUS_BLOCKED = "B";
    public static final String CARD_STATUS_EXPIRED = "E";
    public static final String CARD_STATUS_LOST = "L";
    public static final String CARD_STATUS_STOLEN = "S";

    // Account status
    public static final String ACCOUNT_STATUS_ACTIVE = "A";
    public static final String ACCOUNT_STATUS_FROZEN = "F";
    public static final String ACCOUNT_STATUS_CLOSED = "C";

    // Default values
    public static final String DEFAULT_LANGUAGE = "ko";
    public static final String DEFAULT_COUNTRY = "KR";
    public static final String DEFAULT_CURRENCY = "KRW";
    public static final String DEFAULT_TIMEZONE = "Asia/Seoul";

    // Date formats
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_ISO = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    // Error codes
    public static final String ERROR_CODE_SUCCESS = "0000";
    public static final String ERROR_CODE_SYSTEM_ERROR = "9999";
    public static final String ERROR_CODE_VALIDATION_ERROR = "1001";
    public static final String ERROR_CODE_DATABASE_ERROR = "2001";
    public static final String ERROR_CODE_BUSINESS_ERROR = "3001";

    private CommonSystemConstants() {
        // Utility class - prevent instantiation
    }
}