package com.skcc.oversea.common.business.constants;

public class BusinessConstants {

    // Status constants
    public static final String STATUS_ACTIVE = "A";
    public static final String STATUS_INACTIVE = "I";
    public static final String STATUS_PENDING = "P";
    public static final String STATUS_DELETED = "D";

    // Transaction types
    public static final String TX_TYPE_CREDIT = "CR";
    public static final String TX_TYPE_DEBIT = "DB";
    public static final String TX_TYPE_TRANSFER = "TR";

    // Card types
    public static final String CARD_TYPE_DEBIT = "DEBIT";
    public static final String CARD_TYPE_CREDIT = "CREDIT";
    public static final String CARD_TYPE_PREPAID = "PREPAID";

    // Currency codes
    public static final String CURRENCY_USD = "USD";
    public static final String CURRENCY_EUR = "EUR";
    public static final String CURRENCY_KRW = "KRW";

    // Error codes
    public static final String ERROR_INVALID_ACCOUNT = "E001";
    public static final String ERROR_INSUFFICIENT_FUNDS = "E002";
    public static final String ERROR_INVALID_CARD = "E003";
    public static final String ERROR_TRANSACTION_FAILED = "E004";

    // Success codes
    public static final String SUCCESS_TRANSACTION = "S001";
    public static final String SUCCESS_ACCOUNT_CREATED = "S002";
    public static final String SUCCESS_CARD_ISSUED = "S003";
}