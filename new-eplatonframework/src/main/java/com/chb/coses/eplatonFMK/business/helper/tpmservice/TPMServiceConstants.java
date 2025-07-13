package com.chb.coses.eplatonFMK.business.helper.tpmservice;

/**
 * TPM Service Constants
 * Spring 기반으로 전환된 TPM 서비스 상수 클래스
 * 
 * @author unascribed
 * @version 1.0
 */
public class TPMServiceConstants {

    // 서비스 상태
    public static final String STATUS_UP = "UP";
    public static final String STATUS_DOWN = "DOWN";
    public static final String STATUS_DEGRADED = "DEGRADED";

    // 에러 코드
    public static final String ERROR_CODE_SUCCESS = "0000";
    public static final String ERROR_CODE_SYSTEM_ERROR = "9999";
    public static final String ERROR_CODE_INVALID_PARAMETER = "1001";
    public static final String ERROR_CODE_DATABASE_ERROR = "2001";
    public static final String ERROR_CODE_BUSINESS_ERROR = "3001";
    public static final String ERROR_CODE_TPM_ERROR = "4001";
    public static final String ERROR_CODE_SESSION_ERROR = "5001";
    public static final String ERROR_CODE_PERMISSION_ERROR = "6001";
    public static final String ERROR_CODE_CACHE_ERROR = "7001";
    public static final String ERROR_CODE_AUDIT_ERROR = "8001";

    // 에러 메시지
    public static final String ERROR_MSG_SUCCESS = "정상 처리되었습니다.";
    public static final String ERROR_MSG_SYSTEM_ERROR = "시스템 오류가 발생했습니다.";
    public static final String ERROR_MSG_INVALID_PARAMETER = "잘못된 파라미터입니다.";
    public static final String ERROR_MSG_DATABASE_ERROR = "데이터베이스 오류가 발생했습니다.";
    public static final String ERROR_MSG_BUSINESS_ERROR = "비즈니스 오류가 발생했습니다.";
    public static final String ERROR_MSG_TPM_ERROR = "TPM 서비스 오류가 발생했습니다.";
    public static final String ERROR_MSG_SESSION_ERROR = "세션 오류가 발생했습니다.";
    public static final String ERROR_MSG_PERMISSION_ERROR = "권한 오류가 발생했습니다.";
    public static final String ERROR_MSG_CACHE_ERROR = "캐시 오류가 발생했습니다.";
    public static final String ERROR_MSG_AUDIT_ERROR = "감사 로그 오류가 발생했습니다.";

    // 시스템 이름
    public static final String SYSTEM_CASH_CARD = "CASH CARD";
    public static final String SYSTEM_CASHCARD = "CASHCARD";
    public static final String SYSTEM_DEPOSIT = "DEPOSIT";
    public static final String SYSTEM_COMMON = "COMMON";
    public static final String SYSTEM_TELLER = "TELLER";
    public static final String SYSTEM_FOUNDATION = "FOUNDATION";
    public static final String SYSTEM_FRAMEWORK = "FRAMEWORK";

    // 오퍼레이션 이름
    public static final String OPERATION_COMMO1000 = "COMMO1000";
    public static final String OPERATION_DED0021000 = "DED0021000";
    public static final String OPERATION_CARD001000 = "CARD001000";
    public static final String OPERATION_TELL001000 = "TELL001000";

    // 액션 이름
    public static final String ACTION_CASH_CARD_BIZ = "com.chb.coses.eplatonFMK.business.delegate.action.CashCardBizAction";
    public static final String ACTION_DEPOSIT_BIZ = "com.chb.coses.eplatonFMK.business.delegate.action.DepositBizAction";
    public static final String ACTION_COMMON_BIZ = "com.chb.coses.eplatonFMK.business.delegate.action.CommonBizAction";
    public static final String ACTION_TELLER_BIZ = "com.chb.coses.eplatonFMK.business.delegate.action.TellerBizAction";

    // 채널 타입
    public static final String CHANNEL_TYPE_TELLER = "TELLER";
    public static final String CHANNEL_TYPE_ATM = "ATM";
    public static final String CHANNEL_TYPE_INTERNET = "INTERNET";
    public static final String CHANNEL_TYPE_MOBILE = "MOBILE";
    public static final String CHANNEL_TYPE_CALL_CENTER = "CALL_CENTER";

    // 터미널 타입
    public static final String TERMINAL_TYPE_PC = "PC";
    public static final String TERMINAL_TYPE_MOBILE = "MOBILE";
    public static final String TERMINAL_TYPE_TABLET = "TABLET";
    public static final String TERMINAL_TYPE_ATM = "ATM";
    public static final String TERMINAL_TYPE_POS = "POS";

    // 국가 코드
    public static final String NATION_KR = "KR";
    public static final String NATION_US = "US";
    public static final String NATION_JP = "JP";
    public static final String NATION_CN = "CN";

    // 지역 코드
    public static final String REGION_CODE_SEOUL = "11";
    public static final String REGION_CODE_BUSAN = "21";
    public static final String REGION_CODE_DAEGU = "22";
    public static final String REGION_CODE_INCHEON = "23";
    public static final String REGION_CODE_GWANGJU = "24";
    public static final String REGION_CODE_DAEJEON = "25";
    public static final String REGION_CODE_ULSAN = "26";

    // 시간대
    public static final String TIMEZONE_KST = "GMT+09:00";
    public static final String TIMEZONE_EST = "GMT-05:00";
    public static final String TIMEZONE_PST = "GMT-08:00";
    public static final String TIMEZONE_JST = "GMT+09:00";
    public static final String TIMEZONE_CST = "GMT+08:00";

    // 통화 코드
    public static final String CURRENCY_KRW = "KRW";
    public static final String CURRENCY_USD = "USD";
    public static final String CURRENCY_JPY = "JPY";
    public static final String CURRENCY_CNY = "CNY";
    public static final String CURRENCY_EUR = "EUR";

    // 사용자 레벨
    public static final int USER_LEVEL_ADMIN = 1;
    public static final int USER_LEVEL_MANAGER = 2;
    public static final int USER_LEVEL_TELLER = 3;
    public static final int USER_LEVEL_CUSTOMER = 4;

    // 트랜잭션 클래스
    public static final String TRANSACTION_CLASS_NORMAL = "NORMAL";
    public static final String TRANSACTION_CLASS_CLOSING = "CLOSING";
    public static final String TRANSACTION_CLASS_OPENING = "OPENING";

    // TPFQ (TPM Function Queue)
    public static final String TPFQ_NORMAL = "100";
    public static final String TPFQ_BATCH = "200";
    public static final String TPFQ_REAL_TIME = "300";

    // 로그 레벨
    public static final String LOG_LEVEL_TRACE = "TRACE";
    public static final String LOG_LEVEL_DEBUG = "DEBUG";
    public static final String LOG_LEVEL_INFO = "INFO";
    public static final String LOG_LEVEL_WARN = "WARN";
    public static final String LOG_LEVEL_ERROR = "ERROR";
    public static final String LOG_LEVEL_FATAL = "FATAL";

    // 보안 관련
    public static final String SECURITY_ALGORITHM_SHA256 = "SHA-256";
    public static final String SECURITY_ALGORITHM_MD5 = "MD5";
    public static final int SECURITY_SESSION_TIMEOUT = 1800000; // 30분
    public static final int SECURITY_MAX_FAILED_ATTEMPTS = 5;

    // 캐시 관련
    public static final long CACHE_DEFAULT_TTL = 300000; // 5분
    public static final int CACHE_MAX_SIZE = 10000;
    public static final long CACHE_CLEANUP_INTERVAL = 60000; // 1분

    // 모니터링 관련
    public static final long MONITORING_INTERVAL = 300000; // 5분
    public static final long MONITORING_PERFORMANCE_INTERVAL = 60000; // 1분
    public static final long MONITORING_RESOURCE_INTERVAL = 30000; // 30초

    // 감사 관련
    public static final int AUDIT_MAX_LOGS = 10000;
    public static final String AUDIT_ACTION_LOGIN = "LOGIN";
    public static final String AUDIT_ACTION_LOGOUT = "LOGOUT";
    public static final String AUDIT_ACTION_QUERY = "QUERY";
    public static final String AUDIT_ACTION_INSERT = "INSERT";
    public static final String AUDIT_ACTION_UPDATE = "UPDATE";
    public static final String AUDIT_ACTION_DELETE = "DELETE";

    // 상태 관련
    public static final String AUDIT_STATUS_SUCCESS = "SUCCESS";
    public static final String AUDIT_STATUS_FAILURE = "FAILURE";
    public static final String AUDIT_STATUS_PENDING = "PENDING";

    // 메트릭 관련
    public static final String METRIC_TOTAL_REQUESTS = "totalRequests";
    public static final String METRIC_SUCCESSFUL_REQUESTS = "successfulRequests";
    public static final String METRIC_FAILED_REQUESTS = "failedRequests";
    public static final String METRIC_AVG_PROCESSING_TIME = "avgProcessingTime";
    public static final String METRIC_SUCCESS_RATE = "successRate";

    // 헬스 체크 관련
    public static final double HEALTH_SUCCESS_RATE_THRESHOLD = 90.0;
    public static final double HEALTH_PROCESSING_TIME_THRESHOLD = 10000.0; // 10초
    public static final double HEALTH_MEMORY_USAGE_THRESHOLD = 90.0;

    // 파일 경로
    public static final String LOG_FILE_PATH = "/home/coses/log/outlog/";
    public static final String CONFIG_FILE_PATH = "/home/coses/env/";
    public static final String BACKUP_FILE_PATH = "/home/coses/backup/";

    // 데이터베이스 관련
    public static final String DB_DRIVER_POSTGRESQL = "org.postgresql.Driver";
    public static final String DB_DRIVER_ORACLE = "oracle.jdbc.driver.OracleDriver";
    public static final String DB_DRIVER_MYSQL = "com.mysql.cj.jdbc.Driver";

    // 인코딩
    public static final String ENCODING_UTF8 = "UTF-8";
    public static final String ENCODING_EUC_KR = "EUC-KR";
    public static final String ENCODING_ISO_8859_1 = "ISO-8859-1";

    // 날짜 포맷
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String TIME_FORMAT_HHMMSS = "HHmmss";
    public static final String TIME_FORMAT_HH_MM_SS = "HH:mm:ss";
    public static final String DATETIME_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String DATETIME_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    // 숫자 포맷
    public static final String NUMBER_FORMAT_DECIMAL = "#,##0.00";
    public static final String NUMBER_FORMAT_INTEGER = "#,##0";
    public static final String NUMBER_FORMAT_PERCENT = "#,##0.00%";

    // HTTP 상태 코드
    public static final int HTTP_OK = 200;
    public static final int HTTP_CREATED = 201;
    public static final int HTTP_BAD_REQUEST = 400;
    public static final int HTTP_UNAUTHORIZED = 401;
    public static final int HTTP_FORBIDDEN = 403;
    public static final int HTTP_NOT_FOUND = 404;
    public static final int HTTP_INTERNAL_SERVER_ERROR = 500;
    public static final int HTTP_SERVICE_UNAVAILABLE = 503;
}