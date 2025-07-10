package com.chb.coses.cashCard.business.constants;

/**
 * Cash Card Error Constants
 */
public class CashCardErrorConstants {

    // Error messages
    public static final String ERROR_CARD_NOT_FOUND = "카드를 찾을 수 없습니다.";
    public static final String ERROR_INSUFFICIENT_BALANCE = "잔액이 부족합니다.";
    public static final String ERROR_CARD_BLOCKED = "카드가 차단되었습니다.";
    public static final String ERROR_CARD_EXPIRED = "카드가 만료되었습니다.";
    public static final String ERROR_INVALID_PIN = "잘못된 PIN입니다.";
    public static final String ERROR_DAILY_LIMIT_EXCEEDED = "일일 한도를 초과했습니다.";
    public static final String ERROR_SYSTEM_ERROR = "시스템 오류가 발생했습니다.";
    public static final String ERROR_DATABASE_ERROR = "데이터베이스 오류가 발생했습니다.";
    public static final String ERROR_NETWORK_ERROR = "네트워크 오류가 발생했습니다.";
    public static final String ERROR_TIMEOUT_ERROR = "시간 초과 오류가 발생했습니다.";

    // Error codes
    public static final String ERROR_CODE_CARD_NOT_FOUND = "CARD_001";
    public static final String ERROR_CODE_INSUFFICIENT_BALANCE = "CARD_002";
    public static final String ERROR_CODE_CARD_BLOCKED = "CARD_003";
    public static final String ERROR_CODE_CARD_EXPIRED = "CARD_004";
    public static final String ERROR_CODE_INVALID_PIN = "CARD_005";
    public static final String ERROR_CODE_DAILY_LIMIT_EXCEEDED = "CARD_006";
    public static final String ERROR_CODE_SYSTEM_ERROR = "CARD_999";

    private CashCardErrorConstants() {
        // Utility class - prevent instantiation
    }
}