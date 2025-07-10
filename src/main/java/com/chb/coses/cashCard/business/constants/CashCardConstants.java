package com.chb.coses.cashCard.business.constants;

/**
 * Cash Card Constants
 */
public class CashCardConstants {

    // Card types
    public static final String CARD_TYPE_DEBIT = "DEBIT";
    public static final String CARD_TYPE_CREDIT = "CREDIT";
    public static final String CARD_TYPE_PREPAID = "PREPAID";
    public static final String CARD_TYPE_GIFT = "GIFT";

    // Card status
    public static final String CARD_STATUS_ACTIVE = "A";
    public static final String CARD_STATUS_INACTIVE = "I";
    public static final String CARD_STATUS_BLOCKED = "B";
    public static final String CARD_STATUS_EXPIRED = "E";
    public static final String CARD_STATUS_LOST = "L";
    public static final String CARD_STATUS_STOLEN = "S";

    // Transaction types
    public static final String TRANSACTION_TYPE_PURCHASE = "PURCHASE";
    public static final String TRANSACTION_TYPE_WITHDRAWAL = "WITHDRAWAL";
    public static final String TRANSACTION_TYPE_DEPOSIT = "DEPOSIT";
    public static final String TRANSACTION_TYPE_TRANSFER = "TRANSFER";
    public static final String TRANSACTION_TYPE_REFUND = "REFUND";

    // Transaction status
    public static final String TRANSACTION_STATUS_SUCCESS = "SUCCESS";
    public static final String TRANSACTION_STATUS_FAILED = "FAILED";
    public static final String TRANSACTION_STATUS_PENDING = "PENDING";
    public static final String TRANSACTION_STATUS_CANCELLED = "CANCELLED";

    // Error codes
    public static final String ERROR_CODE_CARD_NOT_FOUND = "CARD_001";
    public static final String ERROR_CODE_INSUFFICIENT_BALANCE = "CARD_002";
    public static final String ERROR_CODE_CARD_BLOCKED = "CARD_003";
    public static final String ERROR_CODE_CARD_EXPIRED = "CARD_004";
    public static final String ERROR_CODE_INVALID_PIN = "CARD_005";
    public static final String ERROR_CODE_DAILY_LIMIT_EXCEEDED = "CARD_006";

    // Limits
    public static final double DEFAULT_DAILY_LIMIT = 1000000.0;
    public static final double DEFAULT_SINGLE_TRANSACTION_LIMIT = 500000.0;
    public static final int DEFAULT_PIN_LENGTH = 4;

    private CashCardConstants() {
        // Utility class - prevent instantiation
    }
}