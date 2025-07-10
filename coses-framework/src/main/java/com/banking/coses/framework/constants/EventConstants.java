package com.banking.coses.framework.constants;

/**
 * Event constants for the COSES Framework
 * 
 * Provides constants related to event processing and management.
 */
public class EventConstants {

    // Event Types
    public static final String EVENT_TYPE_BUSINESS = "BUSINESS";
    public static final String EVENT_TYPE_SYSTEM = "SYSTEM";
    public static final String EVENT_TYPE_AUDIT = "AUDIT";
    public static final String EVENT_TYPE_ERROR = "ERROR";
    public static final String EVENT_TYPE_WARNING = "WARNING";
    public static final String EVENT_TYPE_INFO = "INFO";
    public static final String EVENT_TYPE_DEBUG = "DEBUG";

    // Event Status
    public static final String EVENT_STATUS_PENDING = "PENDING";
    public static final String EVENT_STATUS_PROCESSING = "PROCESSING";
    public static final String EVENT_STATUS_SUCCESS = "SUCCESS";
    public static final String EVENT_STATUS_FAILED = "FAILED";
    public static final String EVENT_STATUS_CANCELLED = "CANCELLED";
    public static final String EVENT_STATUS_TIMEOUT = "TIMEOUT";
    public static final String EVENT_STATUS_RETRY = "RETRY";

    // Event Priority
    public static final String EVENT_PRIORITY_CRITICAL = "CRITICAL";
    public static final String EVENT_PRIORITY_HIGH = "HIGH";
    public static final String EVENT_PRIORITY_NORMAL = "NORMAL";
    public static final String EVENT_PRIORITY_LOW = "LOW";
    public static final String EVENT_PRIORITY_MINIMAL = "MINIMAL";

    // Event Actions
    public static final String EVENT_ACTION_CREATE = "CREATE";
    public static final String EVENT_ACTION_READ = "READ";
    public static final String EVENT_ACTION_UPDATE = "UPDATE";
    public static final String EVENT_ACTION_DELETE = "DELETE";
    public static final String EVENT_ACTION_SEARCH = "SEARCH";
    public static final String EVENT_ACTION_VALIDATE = "VALIDATE";
    public static final String EVENT_ACTION_PROCESS = "PROCESS";
    public static final String EVENT_ACTION_APPROVE = "APPROVE";
    public static final String EVENT_ACTION_REJECT = "REJECT";
    public static final String EVENT_ACTION_CANCEL = "CANCEL";
    public static final String EVENT_ACTION_SUSPEND = "SUSPEND";
    public static final String EVENT_ACTION_RESUME = "RESUME";

    // Event Categories
    public static final String EVENT_CATEGORY_USER = "USER";
    public static final String EVENT_CATEGORY_SYSTEM = "SYSTEM";
    public static final String EVENT_CATEGORY_BATCH = "BATCH";
    public static final String EVENT_CATEGORY_REALTIME = "REALTIME";
    public static final String EVENT_CATEGORY_SCHEDULED = "SCHEDULED";
    public static final String EVENT_CATEGORY_MANUAL = "MANUAL";

    // Event Sources
    public static final String EVENT_SOURCE_WEB = "WEB";
    public static final String EVENT_SOURCE_API = "API";
    public static final String EVENT_SOURCE_BATCH = "BATCH";
    public static final String EVENT_SOURCE_SCHEDULER = "SCHEDULER";
    public static final String EVENT_SOURCE_MANUAL = "MANUAL";
    public static final String EVENT_SOURCE_SYSTEM = "SYSTEM";
    public static final String EVENT_SOURCE_EXTERNAL = "EXTERNAL";

    // Event Targets
    public static final String EVENT_TARGET_DATABASE = "DATABASE";
    public static final String EVENT_TARGET_FILE = "FILE";
    public static final String EVENT_TARGET_QUEUE = "QUEUE";
    public static final String EVENT_TARGET_EMAIL = "EMAIL";
    public static final String EVENT_TARGET_SMS = "SMS";
    public static final String EVENT_TARGET_API = "API";
    public static final String EVENT_TARGET_WEBHOOK = "WEBHOOK";

    // Event Error Codes
    public static final String EVENT_ERROR_VALIDATION = "EVENT_VALIDATION_ERROR";
    public static final String EVENT_ERROR_PROCESSING = "EVENT_PROCESSING_ERROR";
    public static final String EVENT_ERROR_TIMEOUT = "EVENT_TIMEOUT_ERROR";
    public static final String EVENT_ERROR_SYSTEM = "EVENT_SYSTEM_ERROR";
    public static final String EVENT_ERROR_BUSINESS = "EVENT_BUSINESS_ERROR";
    public static final String EVENT_ERROR_EXTERNAL = "EVENT_EXTERNAL_ERROR";

    // Event Timeout Values (in milliseconds)
    public static final long EVENT_TIMEOUT_SHORT = 5000L; // 5 seconds
    public static final long EVENT_TIMEOUT_NORMAL = 30000L; // 30 seconds
    public static final long EVENT_TIMEOUT_LONG = 300000L; // 5 minutes
    public static final long EVENT_TIMEOUT_EXTENDED = 1800000L; // 30 minutes

    // Event Retry Constants
    public static final int EVENT_MAX_RETRIES = 3;
    public static final long EVENT_RETRY_DELAY = 1000L; // 1 second
    public static final long EVENT_RETRY_BACKOFF = 2000L; // 2 seconds

    // Event Queue Constants
    public static final String EVENT_QUEUE_DEFAULT = "default";
    public static final String EVENT_QUEUE_HIGH_PRIORITY = "high-priority";
    public static final String EVENT_QUEUE_LOW_PRIORITY = "low-priority";
    public static final String EVENT_QUEUE_ERROR = "error";
    public static final String EVENT_QUEUE_RETRY = "retry";

    // Event Batch Constants
    public static final int EVENT_BATCH_SIZE_DEFAULT = 100;
    public static final int EVENT_BATCH_SIZE_SMALL = 10;
    public static final int EVENT_BATCH_SIZE_LARGE = 1000;
    public static final long EVENT_BATCH_TIMEOUT = 60000L; // 1 minute

    // Event Logging Constants
    public static final String EVENT_LOG_LEVEL_INFO = "INFO";
    public static final String EVENT_LOG_LEVEL_WARN = "WARN";
    public static final String EVENT_LOG_LEVEL_ERROR = "ERROR";
    public static final String EVENT_LOG_LEVEL_DEBUG = "DEBUG";

    // Event Correlation Constants
    public static final String EVENT_CORRELATION_ID_HEADER = "X-Correlation-ID";
    public static final String EVENT_TRACE_ID_HEADER = "X-Trace-ID";
    public static final String EVENT_SPAN_ID_HEADER = "X-Span-ID";

    // Event Metadata Keys
    public static final String EVENT_METADATA_USER_ID = "userId";
    public static final String EVENT_METADATA_SESSION_ID = "sessionId";
    public static final String EVENT_METADATA_REQUEST_ID = "requestId";
    public static final String EVENT_METADATA_CLIENT_IP = "clientIp";
    public static final String EVENT_METADATA_USER_AGENT = "userAgent";
    public static final String EVENT_METADATA_TENANT_ID = "tenantId";
    public static final String EVENT_METADATA_ENVIRONMENT = "environment";

    private EventConstants() {
        // Utility class - prevent instantiation
    }
}