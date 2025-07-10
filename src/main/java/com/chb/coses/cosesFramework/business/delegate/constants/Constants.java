package com.chb.coses.cosesFramework.business.delegate.constants;

/**
 * Constants for Coses Framework
 */
public class Constants {

    // Transaction constants
    public static final String TRANSACTION_SUCCESS = "SUCCESS";
    public static final String TRANSACTION_FAILURE = "FAILURE";
    public static final String TRANSACTION_PENDING = "PENDING";

    // Status constants
    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_INACTIVE = "INACTIVE";
    public static final String STATUS_DELETED = "DELETED";

    // Error codes
    public static final String ERROR_CODE_SUCCESS = "0000";
    public static final String ERROR_CODE_SYSTEM_ERROR = "9999";
    public static final String ERROR_CODE_VALIDATION_ERROR = "1001";
    public static final String ERROR_CODE_DATABASE_ERROR = "2001";

    // Default values
    public static final String DEFAULT_LANGUAGE = "ko";
    public static final String DEFAULT_TIMEZONE = "Asia/Seoul";
    public static final String DEFAULT_ENCODING = "UTF-8";

    // Date formats
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_ISO = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    private Constants() {
        // Utility class - prevent instantiation
    }
}