package com.banking.kdb.oversea.foundation.constant;

/**
 * Constants for KDB Oversea Foundation
 * 
 * Contains common constants used across the application.
 */
public class Constants {

    // Customer related constants
    public static final String CUSTOMER_STATUS_ACTIVE = "ACTIVE";
    public static final String CUSTOMER_STATUS_INACTIVE = "INACTIVE";
    public static final String CUSTOMER_STATUS_SUSPENDED = "SUSPENDED";
    public static final String CUSTOMER_STATUS_CLOSED = "CLOSED";

    public static final String CUSTOMER_TYPE_INDIVIDUAL = "INDIVIDUAL";
    public static final String CUSTOMER_TYPE_CORPORATE = "CORPORATE";
    public static final String CUSTOMER_TYPE_PARTNERSHIP = "PARTNERSHIP";

    public static final String RISK_LEVEL_LOW = "LOW";
    public static final String RISK_LEVEL_MEDIUM = "MEDIUM";
    public static final String RISK_LEVEL_HIGH = "HIGH";
    public static final String RISK_LEVEL_VERY_HIGH = "VERY_HIGH";

    public static final String KYC_STATUS_PENDING = "PENDING";
    public static final String KYC_STATUS_INCOMPLETE = "INCOMPLETE";
    public static final String KYC_STATUS_COMPLETED = "COMPLETED";
    public static final String KYC_STATUS_REJECTED = "REJECTED";

    // Account related constants
    public static final String ACCOUNT_STATUS_ACTIVE = "ACTIVE";
    public static final String ACCOUNT_STATUS_INACTIVE = "INACTIVE";
    public static final String ACCOUNT_STATUS_FROZEN = "FROZEN";
    public static final String ACCOUNT_STATUS_CLOSED = "CLOSED";

    public static final String ACCOUNT_TYPE_SAVINGS = "SAVINGS";
    public static final String ACCOUNT_TYPE_CURRENT = "CURRENT";
    public static final String ACCOUNT_TYPE_FIXED_DEPOSIT = "FIXED_DEPOSIT";
    public static final String ACCOUNT_TYPE_CASH_CARD = "CASH_CARD";

    // Transaction related constants
    public static final String TRANSACTION_STATUS_PENDING = "PENDING";
    public static final String TRANSACTION_STATUS_COMPLETED = "COMPLETED";
    public static final String TRANSACTION_STATUS_FAILED = "FAILED";
    public static final String TRANSACTION_STATUS_CANCELLED = "CANCELLED";

    public static final String TRANSACTION_TYPE_DEPOSIT = "DEPOSIT";
    public static final String TRANSACTION_TYPE_WITHDRAWAL = "WITHDRAWAL";
    public static final String TRANSACTION_TYPE_TRANSFER = "TRANSFER";
    public static final String TRANSACTION_TYPE_PAYMENT = "PAYMENT";

    // Currency constants
    public static final String CURRENCY_USD = "USD";
    public static final String CURRENCY_EUR = "EUR";
    public static final String CURRENCY_JPY = "JPY";
    public static final String CURRENCY_KRW = "KRW";
    public static final String CURRENCY_CNY = "CNY";

    // Country constants
    public static final String COUNTRY_KOREA = "KOREA";
    public static final String COUNTRY_USA = "USA";
    public static final String COUNTRY_JAPAN = "JAPAN";
    public static final String COUNTRY_CHINA = "CHINA";
    public static final String COUNTRY_GERMANY = "GERMANY";
    public static final String COUNTRY_FRANCE = "FRANCE";
    public static final String COUNTRY_UK = "UK";

    // Nationality constants
    public static final String NATIONALITY_KOREAN = "KOREAN";
    public static final String NATIONALITY_AMERICAN = "AMERICAN";
    public static final String NATIONALITY_JAPANESE = "JAPANESE";
    public static final String NATIONALITY_CHINESE = "CHINESE";
    public static final String NATIONALITY_GERMAN = "GERMAN";
    public static final String NATIONALITY_FRENCH = "FRENCH";
    public static final String NATIONALITY_BRITISH = "BRITISH";

    // ID Type constants
    public static final String ID_TYPE_PASSPORT = "PASSPORT";
    public static final String ID_TYPE_NATIONAL_ID = "NATIONAL_ID";
    public static final String ID_TYPE_DRIVERS_LICENSE = "DRIVERS_LICENSE";
    public static final String ID_TYPE_RESIDENCE_CARD = "RESIDENCE_CARD";

    // Session related constants
    public static final String SESSION_STATUS_ACTIVE = "ACTIVE";
    public static final String SESSION_STATUS_EXPIRED = "EXPIRED";
    public static final String SESSION_STATUS_TERMINATED = "TERMINATED";

    // Teller related constants
    public static final String TELLER_STATUS_ACTIVE = "ACTIVE";
    public static final String TELLER_STATUS_INACTIVE = "INACTIVE";
    public static final String TELLER_STATUS_ON_BREAK = "ON_BREAK";

    // Cash Card related constants
    public static final String CASH_CARD_STATUS_ACTIVE = "ACTIVE";
    public static final String CASH_CARD_STATUS_INACTIVE = "INACTIVE";
    public static final String CASH_CARD_STATUS_LOST = "LOST";
    public static final String CASH_CARD_STATUS_STOLEN = "STOLEN";
    public static final String CASH_CARD_STATUS_EXPIRED = "EXPIRED";

    public static final String CASH_CARD_TYPE_DEBIT = "DEBIT";
    public static final String CASH_CARD_TYPE_CREDIT = "CREDIT";
    public static final String CASH_CARD_TYPE_PREPAID = "PREPAID";

    // Deposit related constants
    public static final String DEPOSIT_TYPE_SAVINGS = "SAVINGS";
    public static final String DEPOSIT_TYPE_FIXED = "FIXED";
    public static final String DEPOSIT_TYPE_CURRENT = "CURRENT";

    public static final String DEPOSIT_STATUS_ACTIVE = "ACTIVE";
    public static final String DEPOSIT_STATUS_MATURED = "MATURED";
    public static final String DEPOSIT_STATUS_CLOSED = "CLOSED";

    // Interest rate constants
    public static final double DEFAULT_SAVINGS_RATE = 0.02; // 2%
    public static final double DEFAULT_FIXED_DEPOSIT_RATE = 0.035; // 3.5%
    public static final double DEFAULT_CURRENT_RATE = 0.01; // 1%

    // Transaction limits
    public static final double DAILY_WITHDRAWAL_LIMIT = 10000.0;
    public static final double DAILY_TRANSFER_LIMIT = 50000.0;
    public static final double MONTHLY_TRANSACTION_LIMIT = 100000.0;

    // Date format constants
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_DD_MM_YYYY = "dd/MM/yyyy";

    // File path constants
    public static final String LOG_DIRECTORY = "logs";
    public static final String CONFIG_DIRECTORY = "config";
    public static final String TEMP_DIRECTORY = "temp";

    // Database constants
    public static final String DB_POOL_INITIAL_SIZE = "5";
    public static final String DB_POOL_MAX_SIZE = "20";
    public static final String DB_POOL_MIN_IDLE = "5";

    // Cache constants
    public static final int CACHE_TTL_SECONDS = 3600; // 1 hour
    public static final int CACHE_MAX_SIZE = 1000;

    // Security constants
    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int PASSWORD_MAX_LENGTH = 20;
    public static final int SESSION_TIMEOUT_MINUTES = 30;
    public static final int MAX_LOGIN_ATTEMPTS = 3;

    // API constants
    public static final String API_VERSION_V1 = "v1";
    public static final String API_BASE_PATH = "/api";
    public static final String API_TIMEOUT_SECONDS = "30";

    // Error codes
    public static final String ERROR_CODE_SUCCESS = "0000";
    public static final String ERROR_CODE_VALIDATION_ERROR = "1001";
    public static final String ERROR_CODE_NOT_FOUND = "1002";
    public static final String ERROR_CODE_DUPLICATE = "1003";
    public static final String ERROR_CODE_INSUFFICIENT_FUNDS = "2001";
    public static final String ERROR_CODE_ACCOUNT_FROZEN = "2002";
    public static final String ERROR_CODE_TRANSACTION_LIMIT_EXCEEDED = "2003";
    public static final String ERROR_CODE_SYSTEM_ERROR = "9999";

    // Success messages
    public static final String SUCCESS_MESSAGE_CUSTOMER_CREATED = "Customer created successfully";
    public static final String SUCCESS_MESSAGE_CUSTOMER_UPDATED = "Customer updated successfully";
    public static final String SUCCESS_MESSAGE_ACCOUNT_CREATED = "Account created successfully";
    public static final String SUCCESS_MESSAGE_TRANSACTION_COMPLETED = "Transaction completed successfully";

    // Error messages
    public static final String ERROR_MESSAGE_CUSTOMER_NOT_FOUND = "Customer not found";
    public static final String ERROR_MESSAGE_ACCOUNT_NOT_FOUND = "Account not found";
    public static final String ERROR_MESSAGE_INSUFFICIENT_FUNDS = "Insufficient funds";
    public static final String ERROR_MESSAGE_TRANSACTION_LIMIT_EXCEEDED = "Transaction limit exceeded";
    public static final String ERROR_MESSAGE_SYSTEM_ERROR = "System error occurred";

    private Constants() {
        // Private constructor to prevent instantiation
    }
}