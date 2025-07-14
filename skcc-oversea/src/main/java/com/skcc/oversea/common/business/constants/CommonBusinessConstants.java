package com.skcc.oversea.common.business.constants;

public class CommonBusinessConstants {

    // Common status constants
    public static final String STATUS_ACTIVE = "A";
    public static final String STATUS_INACTIVE = "I";
    public static final String STATUS_PENDING = "P";
    public static final String STATUS_DELETED = "D";
    public static final String STATUS_COMPLETED = "C";
    public static final String STATUS_FAILED = "F";
    public static final String STATUS_CANCELLED = "X";

    // Common transaction types
    public static final String TX_TYPE_CREDIT = "CR";
    public static final String TX_TYPE_DEBIT = "DB";
    public static final String TX_TYPE_TRANSFER = "TR";
    public static final String TX_TYPE_PAYMENT = "PAY";
    public static final String TX_TYPE_REFUND = "REF";

    // Common currency codes
    public static final String CURRENCY_USD = "USD";
    public static final String CURRENCY_EUR = "EUR";
    public static final String CURRENCY_KRW = "KRW";
    public static final String CURRENCY_JPY = "JPY";
    public static final String CURRENCY_CNY = "CNY";

    // Common error codes
    public static final String ERROR_INVALID_ACCOUNT = "E001";
    public static final String ERROR_INSUFFICIENT_FUNDS = "E002";
    public static final String ERROR_INVALID_CARD = "E003";
    public static final String ERROR_TRANSACTION_FAILED = "E004";
    public static final String ERROR_SYSTEM_ERROR = "E999";

    // Common success codes
    public static final String SUCCESS_TRANSACTION = "S001";
    public static final String SUCCESS_ACCOUNT_CREATED = "S002";
    public static final String SUCCESS_CARD_ISSUED = "S003";

    // Common card types
    public static final String CARD_TYPE_DEBIT = "DEBIT";
    public static final String CARD_TYPE_CREDIT = "CREDIT";
    public static final String CARD_TYPE_PREPAID = "PREPAID";

    // Common yes/no constants
    public static final String YES = "Y";
    public static final String NO = "N";

    // Common default values
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int DEFAULT_PAGE_NUMBER = 1;
    public static final int MAX_PAGE_SIZE = 1000;
}