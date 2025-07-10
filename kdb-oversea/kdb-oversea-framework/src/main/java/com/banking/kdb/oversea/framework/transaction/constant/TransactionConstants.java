package com.banking.kdb.oversea.framework.transaction.constant;

/**
 * Transaction Constants for KDB Oversea Framework
 * 
 * Contains transaction-specific constants used across the framework.
 */
public class TransactionConstants {

    // Transaction processing constants
    public static final String PROCESSING_MODE_SYNC = "SYNC";
    public static final String PROCESSING_MODE_ASYNC = "ASYNC";
    public static final String PROCESSING_MODE_BATCH = "BATCH";

    public static final String PROCESSING_PRIORITY_HIGH = "HIGH";
    public static final String PROCESSING_PRIORITY_NORMAL = "NORMAL";
    public static final String PROCESSING_PRIORITY_LOW = "LOW";

    // Transaction validation constants
    public static final String VALIDATION_RULE_AMOUNT_LIMIT = "AMOUNT_LIMIT";
    public static final String VALIDATION_RULE_FREQUENCY_LIMIT = "FREQUENCY_LIMIT";
    public static final String VALIDATION_RULE_ACCOUNT_STATUS = "ACCOUNT_STATUS";
    public static final String VALIDATION_RULE_CUSTOMER_STATUS = "CUSTOMER_STATUS";
    public static final String VALIDATION_RULE_COMPLIANCE = "COMPLIANCE";
    public static final String VALIDATION_RULE_FRAUD = "FRAUD";

    public static final String VALIDATION_RESULT_PASS = "PASS";
    public static final String VALIDATION_RESULT_FAIL = "FAIL";
    public static final String VALIDATION_RESULT_WARNING = "WARNING";
    public static final String VALIDATION_RESULT_REVIEW = "REVIEW";

    // Transaction routing constants
    public static final String ROUTING_ENGINE_DEFAULT = "DEFAULT";
    public static final String ROUTING_ENGINE_LOAD_BALANCED = "LOAD_BALANCED";
    public static final String ROUTING_ENGINE_PRIORITY = "PRIORITY";
    public static final String ROUTING_ENGINE_ROUND_ROBIN = "ROUND_ROBIN";

    public static final String ROUTING_DESTINATION_INTERNAL = "INTERNAL";
    public static final String ROUTING_DESTINATION_EXTERNAL = "EXTERNAL";
    public static final String ROUTING_DESTINATION_CORRESPONDENT = "CORRESPONDENT";

    // Transaction queue constants
    public static final String QUEUE_NAME_HIGH_PRIORITY = "high-priority-queue";
    public static final String QUEUE_NAME_NORMAL = "normal-queue";
    public static final String QUEUE_NAME_LOW_PRIORITY = "low-priority-queue";
    public static final String QUEUE_NAME_BATCH = "batch-queue";
    public static final String QUEUE_NAME_RETRY = "retry-queue";
    public static final String QUEUE_NAME_DLQ = "dead-letter-queue";

    public static final String QUEUE_STATUS_READY = "READY";
    public static final String QUEUE_STATUS_PROCESSING = "PROCESSING";
    public static final String QUEUE_STATUS_COMPLETED = "COMPLETED";
    public static final String QUEUE_STATUS_FAILED = "FAILED";
    public static final String QUEUE_STATUS_RETRY = "RETRY";

    // Transaction retry constants
    public static final int DEFAULT_RETRY_DELAY_MS = 1000;
    public static final int DEFAULT_MAX_RETRY_ATTEMPTS = 3;
    public static final double DEFAULT_BACKOFF_MULTIPLIER = 2.0;

    public static final String RETRY_STRATEGY_IMMEDIATE = "IMMEDIATE";
    public static final String RETRY_STRATEGY_FIXED_DELAY = "FIXED_DELAY";
    public static final String RETRY_STRATEGY_EXPONENTIAL_BACKOFF = "EXPONENTIAL_BACKOFF";

    // Transaction timeout constants
    public static final int DEFAULT_TRANSACTION_TIMEOUT_SECONDS = 30;
    public static final int DEFAULT_AUTHORIZATION_TIMEOUT_SECONDS = 60;
    public static final int DEFAULT_VALIDATION_TIMEOUT_SECONDS = 10;
    public static final int DEFAULT_ROUTING_TIMEOUT_SECONDS = 5;

    // Transaction monitoring constants
    public static final String METRIC_TRANSACTION_COUNT = "transaction.count";
    public static final String METRIC_TRANSACTION_DURATION = "transaction.duration";
    public static final String METRIC_TRANSACTION_SUCCESS_RATE = "transaction.success.rate";
    public static final String METRIC_TRANSACTION_FAILURE_RATE = "transaction.failure.rate";
    public static final String METRIC_TRANSACTION_QUEUE_SIZE = "transaction.queue.size";
    public static final String METRIC_TRANSACTION_PROCESSING_TIME = "transaction.processing.time";

    // Transaction audit constants
    public static final String AUDIT_EVENT_TRANSACTION_CREATED = "TRANSACTION_CREATED";
    public static final String AUDIT_EVENT_TRANSACTION_UPDATED = "TRANSACTION_UPDATED";
    public static final String AUDIT_EVENT_TRANSACTION_PROCESSED = "TRANSACTION_PROCESSED";
    public static final String AUDIT_EVENT_TRANSACTION_COMPLETED = "TRANSACTION_COMPLETED";
    public static final String AUDIT_EVENT_TRANSACTION_FAILED = "TRANSACTION_FAILED";
    public static final String AUDIT_EVENT_TRANSACTION_CANCELLED = "TRANSACTION_CANCELLED";

    // Transaction notification constants
    public static final String NOTIFICATION_TYPE_TRANSACTION_INITIATED = "TRANSACTION_INITIATED";
    public static final String NOTIFICATION_TYPE_TRANSACTION_COMPLETED = "TRANSACTION_COMPLETED";
    public static final String NOTIFICATION_TYPE_TRANSACTION_FAILED = "TRANSACTION_FAILED";
    public static final String NOTIFICATION_TYPE_TRANSACTION_AUTHORIZATION_REQUIRED = "TRANSACTION_AUTHORIZATION_REQUIRED";

    // Transaction cache constants
    public static final String CACHE_KEY_TRANSACTION = "transaction";
    public static final String CACHE_KEY_TRANSACTION_STATUS = "transaction.status";
    public static final String CACHE_KEY_TRANSACTION_LIMITS = "transaction.limits";
    public static final String CACHE_KEY_TRANSACTION_ROUTING = "transaction.routing";

    public static final int CACHE_TTL_TRANSACTION_SECONDS = 300; // 5 minutes
    public static final int CACHE_TTL_TRANSACTION_STATUS_SECONDS = 60; // 1 minute
    public static final int CACHE_TTL_TRANSACTION_LIMITS_SECONDS = 3600; // 1 hour
    public static final int CACHE_TTL_TRANSACTION_ROUTING_SECONDS = 1800; // 30 minutes

    // Transaction security constants
    public static final String SECURITY_ROLE_TRANSACTION_USER = "TRANSACTION_USER";
    public static final String SECURITY_ROLE_TRANSACTION_ADMIN = "TRANSACTION_ADMIN";
    public static final String SECURITY_ROLE_TRANSACTION_AUTHORIZER = "TRANSACTION_AUTHORIZER";
    public static final String SECURITY_ROLE_TRANSACTION_AUDITOR = "TRANSACTION_AUDITOR";

    // Transaction compliance constants
    public static final String COMPLIANCE_CHECK_AML = "AML";
    public static final String COMPLIANCE_CHECK_KYC = "KYC";
    public static final String COMPLIANCE_CHECK_SANCTIONS = "SANCTIONS";
    public static final String COMPLIANCE_CHECK_PEP = "PEP";

    public static final String COMPLIANCE_STATUS_PASS = "PASS";
    public static final String COMPLIANCE_STATUS_FAIL = "FAIL";
    public static final String COMPLIANCE_STATUS_REVIEW = "REVIEW";
    public static final String COMPLIANCE_STATUS_PENDING = "PENDING";

    // Transaction fraud detection constants
    public static final String FRAUD_CHECK_RISK_SCORE = "RISK_SCORE";
    public static final String FRAUD_CHECK_PATTERN_ANALYSIS = "PATTERN_ANALYSIS";
    public static final String FRAUD_CHECK_BEHAVIORAL_ANALYSIS = "BEHAVIORAL_ANALYSIS";
    public static final String FRAUD_CHECK_DEVICE_FINGERPRINTING = "DEVICE_FINGERPRINTING";

    public static final String FRAUD_STATUS_LOW_RISK = "LOW_RISK";
    public static final String FRAUD_STATUS_MEDIUM_RISK = "MEDIUM_RISK";
    public static final String FRAUD_STATUS_HIGH_RISK = "HIGH_RISK";
    public static final String FRAUD_STATUS_BLOCKED = "BLOCKED";

    // Transaction settlement constants
    public static final String SETTLEMENT_STATUS_PENDING = "PENDING";
    public static final String SETTLEMENT_STATUS_IN_PROGRESS = "IN_PROGRESS";
    public static final String SETTLEMENT_STATUS_COMPLETED = "COMPLETED";
    public static final String SETTLEMENT_STATUS_FAILED = "FAILED";
    public static final String SETTLEMENT_STATUS_CANCELLED = "CANCELLED";

    public static final String SETTLEMENT_METHOD_REAL_TIME = "REAL_TIME";
    public static final String SETTLEMENT_METHOD_BATCH = "BATCH";
    public static final String SETTLEMENT_METHOD_NET = "NET";

    // Transaction reconciliation constants
    public static final String RECONCILIATION_STATUS_PENDING = "PENDING";
    public static final String RECONCILIATION_STATUS_MATCHED = "MATCHED";
    public static final String RECONCILIATION_STATUS_UNMATCHED = "UNMATCHED";
    public static final String RECONCILIATION_STATUS_EXCEPTION = "EXCEPTION";

    // Transaction reporting constants
    public static final String REPORT_TYPE_DAILY = "DAILY";
    public static final String REPORT_TYPE_WEEKLY = "WEEKLY";
    public static final String REPORT_TYPE_MONTHLY = "MONTHLY";
    public static final String REPORT_TYPE_QUARTERLY = "QUARTERLY";
    public static final String REPORT_TYPE_YEARLY = "YEARLY";

    public static final String REPORT_FORMAT_CSV = "CSV";
    public static final String REPORT_FORMAT_XML = "XML";
    public static final String REPORT_FORMAT_JSON = "JSON";
    public static final String REPORT_FORMAT_PDF = "PDF";

    // Transaction workflow constants
    public static final String WORKFLOW_STEP_INITIATION = "INITIATION";
    public static final String WORKFLOW_STEP_VALIDATION = "VALIDATION";
    public static final String WORKFLOW_STEP_AUTHORIZATION = "AUTHORIZATION";
    public static final String WORKFLOW_STEP_PROCESSING = "PROCESSING";
    public static final String WORKFLOW_STEP_SETTLEMENT = "SETTLEMENT";
    public static final String WORKFLOW_STEP_COMPLETION = "COMPLETION";

    public static final String WORKFLOW_STATUS_ACTIVE = "ACTIVE";
    public static final String WORKFLOW_STATUS_COMPLETED = "COMPLETED";
    public static final String WORKFLOW_STATUS_CANCELLED = "CANCELLED";
    public static final String WORKFLOW_STATUS_SUSPENDED = "SUSPENDED";

    private TransactionConstants() {
        // Private constructor to prevent instantiation
    }
}