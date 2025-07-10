package com.chb.coses.framework.constants;

/**
 * Event Constants for the EPlaton Framework
 */
public class EventConstants {

    public static final String EVENT_SUCCESS = "SUCCESS";
    public static final String EVENT_FAILURE = "FAILURE";
    public static final String EVENT_ERROR = "ERROR";

    public static final String EVENT_TYPE_REQUEST = "REQUEST";
    public static final String EVENT_TYPE_RESPONSE = "RESPONSE";
    public static final String EVENT_TYPE_NOTIFICATION = "NOTIFICATION";

    public static final String EVENT_STATUS_PENDING = "PENDING";
    public static final String EVENT_STATUS_PROCESSING = "PROCESSING";
    public static final String EVENT_STATUS_COMPLETED = "COMPLETED";
    public static final String EVENT_STATUS_FAILED = "FAILED";

    private EventConstants() {
        // Utility class - prevent instantiation
    }
}