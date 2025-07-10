package com.banking.kdb.oversea.framework.constant;

/**
 * Framework Constants for KDB Oversea Framework
 * 
 * Contains framework-specific constants used across the application.
 */
public class FrameworkConstants {

    // Transaction Framework constants
    public static final String TRANSACTION_STATUS_INITIATED = "INITIATED";
    public static final String TRANSACTION_STATUS_PROCESSING = "PROCESSING";
    public static final String TRANSACTION_STATUS_COMPLETED = "COMPLETED";
    public static final String TRANSACTION_STATUS_FAILED = "FAILED";
    public static final String TRANSACTION_STATUS_CANCELLED = "CANCELLED";
    public static final String TRANSACTION_STATUS_PENDING = "PENDING";
    public static final String TRANSACTION_STATUS_AUTHORIZED = "AUTHORIZED";
    public static final String TRANSACTION_STATUS_DECLINED = "DECLINED";

    public static final String TRANSACTION_TYPE_DEBIT = "DEBIT";
    public static final String TRANSACTION_TYPE_CREDIT = "CREDIT";
    public static final String TRANSACTION_TYPE_TRANSFER = "TRANSFER";
    public static final String TRANSACTION_TYPE_PAYMENT = "PAYMENT";
    public static final String TRANSACTION_TYPE_WITHDRAWAL = "WITHDRAWAL";
    public static final String TRANSACTION_TYPE_DEPOSIT = "DEPOSIT";

    public static final String TRANSACTION_CHANNEL_BRANCH = "BRANCH";
    public static final String TRANSACTION_CHANNEL_ATM = "ATM";
    public static final String TRANSACTION_CHANNEL_ONLINE = "ONLINE";
    public static final String TRANSACTION_CHANNEL_MOBILE = "MOBILE";
    public static final String TRANSACTION_CHANNEL_POS = "POS";

    // TCF (Transaction Control Framework) constants
    public static final String TCF_STATUS_ACTIVE = "ACTIVE";
    public static final String TCF_STATUS_INACTIVE = "INACTIVE";
    public static final String TCF_STATUS_SUSPENDED = "SUSPENDED";

    public static final String TCF_TYPE_ACCOUNT_LIMIT = "ACCOUNT_LIMIT";
    public static final String TCF_TYPE_DAILY_LIMIT = "DAILY_LIMIT";
    public static final String TCF_TYPE_MONTHLY_LIMIT = "MONTHLY_LIMIT";
    public static final String TCF_TYPE_TRANSACTION_LIMIT = "TRANSACTION_LIMIT";

    // TPM (Transaction Processing Monitor) constants
    public static final String TPM_STATUS_RUNNING = "RUNNING";
    public static final String TPM_STATUS_STOPPED = "STOPPED";
    public static final String TPM_STATUS_PAUSED = "PAUSED";

    public static final String TPM_MODE_SYNC = "SYNC";
    public static final String TPM_MODE_ASYNC = "ASYNC";
    public static final String TPM_MODE_BATCH = "BATCH";

    // Blocking constants
    public static final String BLOCKING_STATUS_ACTIVE = "ACTIVE";
    public static final String BLOCKING_STATUS_INACTIVE = "INACTIVE";
    public static final String BLOCKING_STATUS_EXPIRED = "EXPIRED";

    public static final String BLOCKING_TYPE_ACCOUNT = "ACCOUNT";
    public static final String BLOCKING_TYPE_CARD = "CARD";
    public static final String BLOCKING_TYPE_CUSTOMER = "CUSTOMER";
    public static final String BLOCKING_TYPE_TRANSACTION = "TRANSACTION";

    public static final String BLOCKING_REASON_FRAUD = "FRAUD";
    public static final String BLOCKING_REASON_COMPLIANCE = "COMPLIANCE";
    public static final String BLOCKING_REASON_OPERATIONAL = "OPERATIONAL";
    public static final String BLOCKING_REASON_CUSTOMER_REQUEST = "CUSTOMER_REQUEST";

    // Transfer constants
    public static final String TRANSFER_STATUS_INITIATED = "INITIATED";
    public static final String TRANSFER_STATUS_PROCESSING = "PROCESSING";
    public static final String TRANSFER_STATUS_COMPLETED = "COMPLETED";
    public static final String TRANSFER_STATUS_FAILED = "FAILED";
    public static final String TRANSFER_STATUS_CANCELLED = "CANCELLED";

    public static final String TRANSFER_TYPE_INTERNAL = "INTERNAL";
    public static final String TRANSFER_TYPE_EXTERNAL = "EXTERNAL";
    public static final String TRANSFER_TYPE_INTERNATIONAL = "INTERNATIONAL";
    public static final String TRANSFER_TYPE_DOMESTIC = "DOMESTIC";

    public static final String TRANSFER_METHOD_EFT = "EFT";
    public static final String TRANSFER_METHOD_WIRE = "WIRE";
    public static final String TRANSFER_METHOD_ACH = "ACH";
    public static final String TRANSFER_METHOD_SWIFT = "SWIFT";

    // Authorization constants
    public static final String AUTHORIZATION_STATUS_PENDING = "PENDING";
    public static final String AUTHORIZATION_STATUS_APPROVED = "APPROVED";
    public static final String AUTHORIZATION_STATUS_DECLINED = "DECLINED";
    public static final String AUTHORIZATION_STATUS_REQUIRED = "REQUIRED";

    public static final String AUTHORIZATION_TYPE_SINGLE = "SINGLE";
    public static final String AUTHORIZATION_TYPE_DUAL = "DUAL";
    public static final String AUTHORIZATION_TYPE_MULTI = "MULTI";

    public static final String AUTHORIZATION_LEVEL_LOW = "LOW";
    public static final String AUTHORIZATION_LEVEL_MEDIUM = "MEDIUM";
    public static final String AUTHORIZATION_LEVEL_HIGH = "HIGH";

    // Validation constants
    public static final String VALIDATION_STATUS_PASSED = "PASSED";
    public static final String VALIDATION_STATUS_FAILED = "FAILED";
    public static final String VALIDATION_STATUS_WARNING = "WARNING";

    public static final String VALIDATION_TYPE_AMOUNT = "AMOUNT";
    public static final String VALIDATION_TYPE_FREQUENCY = "FREQUENCY";
    public static final String VALIDATION_TYPE_LIMIT = "LIMIT";
    public static final String VALIDATION_TYPE_COMPLIANCE = "COMPLIANCE";

    // Routing constants
    public static final String ROUTING_STATUS_ACTIVE = "ACTIVE";
    public static final String ROUTING_STATUS_INACTIVE = "INACTIVE";

    public static final String ROUTING_TYPE_DIRECT = "DIRECT";
    public static final String ROUTING_TYPE_ROUND_ROBIN = "ROUND_ROBIN";
    public static final String ROUTING_TYPE_LOAD_BALANCED = "LOAD_BALANCED";
    public static final String ROUTING_TYPE_PRIORITY = "PRIORITY";

    // Queue constants
    public static final String QUEUE_STATUS_ACTIVE = "ACTIVE";
    public static final String QUEUE_STATUS_INACTIVE = "INACTIVE";
    public static final String QUEUE_STATUS_PAUSED = "PAUSED";

    public static final String QUEUE_TYPE_HIGH_PRIORITY = "HIGH_PRIORITY";
    public static final String QUEUE_TYPE_NORMAL = "NORMAL";
    public static final String QUEUE_TYPE_LOW_PRIORITY = "LOW_PRIORITY";
    public static final String QUEUE_TYPE_BATCH = "BATCH";

    // Error codes
    public static final String ERROR_CODE_TRANSACTION_FAILED = "TXN_001";
    public static final String ERROR_CODE_INSUFFICIENT_FUNDS = "TXN_002";
    public static final String ERROR_CODE_ACCOUNT_BLOCKED = "TXN_003";
    public static final String ERROR_CODE_LIMIT_EXCEEDED = "TXN_004";
    public static final String ERROR_CODE_INVALID_AMOUNT = "TXN_005";
    public static final String ERROR_CODE_DUPLICATE_TRANSACTION = "TXN_006";
    public static final String ERROR_CODE_SYSTEM_ERROR = "TXN_007";
    public static final String ERROR_CODE_TIMEOUT = "TXN_008";
    public static final String ERROR_CODE_AUTHORIZATION_REQUIRED = "TXN_009";
    public static final String ERROR_CODE_VALIDATION_FAILED = "TXN_010";

    // Success codes
    public static final String SUCCESS_CODE_TRANSACTION_COMPLETED = "TXN_100";
    public static final String SUCCESS_CODE_TRANSACTION_AUTHORIZED = "TXN_101";
    public static final String SUCCESS_CODE_TRANSACTION_PROCESSED = "TXN_102";

    // Timeout constants
    public static final int DEFAULT_TRANSACTION_TIMEOUT_SECONDS = 30;
    public static final int DEFAULT_AUTHORIZATION_TIMEOUT_SECONDS = 60;
    public static final int DEFAULT_SYSTEM_TIMEOUT_SECONDS = 120;

    // Retry constants
    public static final int DEFAULT_MAX_RETRY_ATTEMPTS = 3;
    public static final long DEFAULT_RETRY_DELAY_MS = 1000;
    public static final long DEFAULT_BACKOFF_MULTIPLIER = 2;

    // Batch processing constants
    public static final int DEFAULT_BATCH_SIZE = 100;
    public static final int DEFAULT_BATCH_TIMEOUT_SECONDS = 300;
    public static final String BATCH_STATUS_PENDING = "PENDING";
    public static final String BATCH_STATUS_PROCESSING = "PROCESSING";
    public static final String BATCH_STATUS_COMPLETED = "COMPLETED";
    public static final String BATCH_STATUS_FAILED = "FAILED";

    // Audit constants
    public static final String AUDIT_ACTION_CREATE = "CREATE";
    public static final String AUDIT_ACTION_UPDATE = "UPDATE";
    public static final String AUDIT_ACTION_DELETE = "DELETE";
    public static final String AUDIT_ACTION_VIEW = "VIEW";
    public static final String AUDIT_ACTION_AUTHORIZE = "AUTHORIZE";
    public static final String AUDIT_ACTION_DECLINE = "DECLINE";

    // Notification constants
    public static final String NOTIFICATION_TYPE_EMAIL = "EMAIL";
    public static final String NOTIFICATION_TYPE_SMS = "SMS";
    public static final String NOTIFICATION_TYPE_PUSH = "PUSH";
    public static final String NOTIFICATION_TYPE_IN_APP = "IN_APP";

    public static final String NOTIFICATION_STATUS_PENDING = "PENDING";
    public static final String NOTIFICATION_STATUS_SENT = "SENT";
    public static final String NOTIFICATION_STATUS_DELIVERED = "DELIVERED";
    public static final String NOTIFICATION_STATUS_FAILED = "FAILED";

    // Cache constants
    public static final String CACHE_KEY_TRANSACTION = "transaction";
    public static final String CACHE_KEY_ACCOUNT = "account";
    public static final String CACHE_KEY_CUSTOMER = "customer";
    public static final String CACHE_KEY_LIMITS = "limits";
    public static final String CACHE_KEY_ROUTING = "routing";

    public static final int CACHE_TTL_TRANSACTION_SECONDS = 300; // 5 minutes
    public static final int CACHE_TTL_ACCOUNT_SECONDS = 3600; // 1 hour
    public static final int CACHE_TTL_CUSTOMER_SECONDS = 7200; // 2 hours
    public static final int CACHE_TTL_LIMITS_SECONDS = 1800; // 30 minutes
    public static final int CACHE_TTL_ROUTING_SECONDS = 86400; // 24 hours

    // Database constants
    public static final String DB_POOL_NAME_TRANSACTION = "transaction-pool";
    public static final String DB_POOL_NAME_REPORTING = "reporting-pool";
    public static final String DB_POOL_NAME_AUDIT = "audit-pool";

    // Thread pool constants
    public static final String THREAD_POOL_TRANSACTION = "transaction-executor";
    public static final String THREAD_POOL_NOTIFICATION = "notification-executor";
    public static final String THREAD_POOL_BATCH = "batch-executor";

    public static final int THREAD_POOL_CORE_SIZE = 10;
    public static final int THREAD_POOL_MAX_SIZE = 50;
    public static final int THREAD_POOL_QUEUE_SIZE = 100;

    // API constants
    public static final String API_VERSION_V1 = "v1";
    public static final String API_BASE_PATH = "/api";
    public static final String API_TRANSACTION_PATH = "/transactions";
    public static final String API_TRANSFER_PATH = "/transfers";
    public static final String API_AUTHORIZATION_PATH = "/authorizations";

    // Security constants
    public static final String SECURITY_ROLE_TRANSACTION_USER = "TRANSACTION_USER";
    public static final String SECURITY_ROLE_TRANSACTION_ADMIN = "TRANSACTION_ADMIN";
    public static final String SECURITY_ROLE_AUTHORIZER = "AUTHORIZER";
    public static final String SECURITY_ROLE_AUDITOR = "AUDITOR";

    // Monitoring constants
    public static final String METRIC_TRANSACTION_COUNT = "transaction.count";
    public static final String METRIC_TRANSACTION_DURATION = "transaction.duration";
    public static final String METRIC_TRANSACTION_SUCCESS_RATE = "transaction.success.rate";
    public static final String METRIC_TRANSACTION_FAILURE_RATE = "transaction.failure.rate";

    public static final String METRIC_TRANSFER_COUNT = "transfer.count";
    public static final String METRIC_TRANSFER_DURATION = "transfer.duration";
    public static final String METRIC_TRANSFER_SUCCESS_RATE = "transfer.success.rate";

    public static final String METRIC_AUTHORIZATION_COUNT = "authorization.count";
    public static final String METRIC_AUTHORIZATION_DURATION = "authorization.duration";
    public static final String METRIC_AUTHORIZATION_APPROVAL_RATE = "authorization.approval.rate";

    private FrameworkConstants() {
        // Private constructor to prevent instantiation
    }
}