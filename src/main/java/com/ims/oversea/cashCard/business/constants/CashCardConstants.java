package com.ims.oversea.cashCard.business.constants;

import java.math.BigDecimal;

public class CashCardConstants
{

    public CashCardConstants()
    {
    }

    /**
     * System Parameter Code
     */
    public static final String SYS_PARAM_SETUP = "SETUP";
    public static final String DAILY_LIMIT_INFO = "CARDLIMIT";
    public static final String DAILY_FEE_LIMIT_INFO = "CARDFEELIM";
    public static final String DAILY_ACCUM_INFO = "CARDACCUM";

    /**
     * Bank Type
     */
    public static final String BANK_TYPE = "5";

    /**
     * Incident Code
     */
    public static final String NORMAL = "00";
    public static final String LOST_STOLEN = "01";
    public static final String STOP_REQUEST = "02";
    public static final String HOLD = "03";
    public static final String OTHER = "09";

    /**
     * CashCard Status
     */
    public static final String NORMAL_STATUS = "00";
    public static final String PROBLEMED_STATUS = "66";
    public static final String CLOSED_STATUS = "99";

    /**
     * HotCard Status
     */
    public static final String APPLIED = "00";
    public static final String RELEASED = "99";

    /**
     * Card Type
     */
    public static final String CASH_CARD = "1";
    public static final String DEBIT_CARD = "2";
    public static final String CREDIT_CARD = "3";

    /**
     * Waive Fee
     */
    public static final String FEE_CHARGE = "0";
    public static final String FEE_WAIVE = "1";

    /**
     * Boolean Constants
     */
    public static final String BOOLEAN_TRUE = "Y";
    public static final String BOOLEAN_FALSE = "N";

    /**
     * Single Withdraw Limit
     */
    public static final BigDecimal SINGLE_WITHDRAW_LIMIT = new BigDecimal("2000000");
    public static final BigDecimal SINGLE_TRANSFER_LIMIT = new BigDecimal("10000000");

    /**
     * Order By Method
     */
    public static final String ORDER_BY_ASC = "10";
    public static final String ORDER_BY_DESC = "20";
}