package com.banking.kdb.oversea.foundation.utility;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.foundation.constant.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Error Utility class for KDB Oversea Foundation
 * 
 * Provides error handling and error code management utilities.
 */
public class ERRORUtils {

    private static final FoundationLogger logger = FoundationLogger.getLogger(ERRORUtils.class);

    // Error code mappings
    private static final Map<String, String> ERROR_MESSAGES = new HashMap<>();
    private static final Map<String, String> ERROR_DESCRIPTIONS = new HashMap<>();

    static {
        initializeErrorMessages();
        initializeErrorDescriptions();
    }

    /**
     * Initialize error messages
     */
    private static void initializeErrorMessages() {
        ERROR_MESSAGES.put(Constants.ERROR_CODE_SUCCESS, "Success");
        ERROR_MESSAGES.put(Constants.ERROR_CODE_VALIDATION_ERROR, "Validation error");
        ERROR_MESSAGES.put(Constants.ERROR_CODE_NOT_FOUND, "Resource not found");
        ERROR_MESSAGES.put(Constants.ERROR_CODE_DUPLICATE, "Duplicate resource");
        ERROR_MESSAGES.put(Constants.ERROR_CODE_INSUFFICIENT_FUNDS, "Insufficient funds");
        ERROR_MESSAGES.put(Constants.ERROR_CODE_ACCOUNT_FROZEN, "Account is frozen");
        ERROR_MESSAGES.put(Constants.ERROR_CODE_TRANSACTION_LIMIT_EXCEEDED, "Transaction limit exceeded");
        ERROR_MESSAGES.put(Constants.ERROR_CODE_SYSTEM_ERROR, "System error");
    }

    /**
     * Initialize error descriptions
     */
    private static void initializeErrorDescriptions() {
        ERROR_DESCRIPTIONS.put(Constants.ERROR_CODE_SUCCESS, "Operation completed successfully");
        ERROR_DESCRIPTIONS.put(Constants.ERROR_CODE_VALIDATION_ERROR, "Input validation failed");
        ERROR_DESCRIPTIONS.put(Constants.ERROR_CODE_NOT_FOUND, "The requested resource was not found");
        ERROR_DESCRIPTIONS.put(Constants.ERROR_CODE_DUPLICATE, "A resource with the same identifier already exists");
        ERROR_DESCRIPTIONS.put(Constants.ERROR_CODE_INSUFFICIENT_FUNDS,
                "Account balance is insufficient for the transaction");
        ERROR_DESCRIPTIONS.put(Constants.ERROR_CODE_ACCOUNT_FROZEN,
                "Account is frozen and cannot perform transactions");
        ERROR_DESCRIPTIONS.put(Constants.ERROR_CODE_TRANSACTION_LIMIT_EXCEEDED,
                "Transaction amount exceeds the allowed limit");
        ERROR_DESCRIPTIONS.put(Constants.ERROR_CODE_SYSTEM_ERROR, "An unexpected system error occurred");
    }

    /**
     * Get error message by error code
     */
    public static String getErrorMessage(String errorCode) {
        return ERROR_MESSAGES.getOrDefault(errorCode, "Unknown error");
    }

    /**
     * Get error description by error code
     */
    public static String getErrorDescription(String errorCode) {
        return ERROR_DESCRIPTIONS.getOrDefault(errorCode, "Unknown error description");
    }

    /**
     * Create error response map
     */
    public static Map<String, Object> createErrorResponse(String errorCode) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("errorCode", errorCode);
        errorResponse.put("errorMessage", getErrorMessage(errorCode));
        errorResponse.put("errorDescription", getErrorDescription(errorCode));
        errorResponse.put("timestamp", CommonUtil.getCurrentTimestamp());
        return errorResponse;
    }

    /**
     * Create error response map with custom message
     */
    public static Map<String, Object> createErrorResponse(String errorCode, String customMessage) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("errorCode", errorCode);
        errorResponse.put("errorMessage", customMessage);
        errorResponse.put("errorDescription", getErrorDescription(errorCode));
        errorResponse.put("timestamp", CommonUtil.getCurrentTimestamp());
        return errorResponse;
    }

    /**
     * Create success response map
     */
    public static Map<String, Object> createSuccessResponse() {
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("errorCode", Constants.ERROR_CODE_SUCCESS);
        successResponse.put("errorMessage", getErrorMessage(Constants.ERROR_CODE_SUCCESS));
        successResponse.put("timestamp", CommonUtil.getCurrentTimestamp());
        return successResponse;
    }

    /**
     * Create success response map with data
     */
    public static Map<String, Object> createSuccessResponse(Object data) {
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("errorCode", Constants.ERROR_CODE_SUCCESS);
        successResponse.put("errorMessage", getErrorMessage(Constants.ERROR_CODE_SUCCESS));
        successResponse.put("data", data);
        successResponse.put("timestamp", CommonUtil.getCurrentTimestamp());
        return successResponse;
    }

    /**
     * Log error with context
     */
    public static void logError(String errorCode, String context, Exception e) {
        String message = String.format("Error [%s] in context [%s]: %s",
                errorCode, context, getErrorMessage(errorCode));
        logger.error(message, e);
    }

    /**
     * Log error without exception
     */
    public static void logError(String errorCode, String context) {
        String message = String.format("Error [%s] in context [%s]: %s",
                errorCode, context, getErrorMessage(errorCode));
        logger.error(message);
    }

    /**
     * Log warning with context
     */
    public static void logWarning(String errorCode, String context) {
        String message = String.format("Warning [%s] in context [%s]: %s",
                errorCode, context, getErrorMessage(errorCode));
        logger.warn(message);
    }

    /**
     * Check if error code represents success
     */
    public static boolean isSuccess(String errorCode) {
        return Constants.ERROR_CODE_SUCCESS.equals(errorCode);
    }

    /**
     * Check if error code represents failure
     */
    public static boolean isFailure(String errorCode) {
        return !isSuccess(errorCode);
    }

    /**
     * Check if error code represents validation error
     */
    public static boolean isValidationError(String errorCode) {
        return Constants.ERROR_CODE_VALIDATION_ERROR.equals(errorCode);
    }

    /**
     * Check if error code represents system error
     */
    public static boolean isSystemError(String errorCode) {
        return Constants.ERROR_CODE_SYSTEM_ERROR.equals(errorCode);
    }

    /**
     * Get HTTP status code for error code
     */
    public static int getHttpStatus(String errorCode) {
        switch (errorCode) {
            case Constants.ERROR_CODE_SUCCESS:
                return 200;
            case Constants.ERROR_CODE_VALIDATION_ERROR:
                return 400;
            case Constants.ERROR_CODE_NOT_FOUND:
                return 404;
            case Constants.ERROR_CODE_DUPLICATE:
                return 409;
            case Constants.ERROR_CODE_INSUFFICIENT_FUNDS:
            case Constants.ERROR_CODE_ACCOUNT_FROZEN:
            case Constants.ERROR_CODE_TRANSACTION_LIMIT_EXCEEDED:
                return 422;
            case Constants.ERROR_CODE_SYSTEM_ERROR:
            default:
                return 500;
        }
    }

    /**
     * Format error for logging
     */
    public static String formatErrorForLogging(String errorCode, String context, String details) {
        return String.format("[%s] %s - Context: %s, Details: %s",
                errorCode, getErrorMessage(errorCode), context, details);
    }

    /**
     * Format error for user display
     */
    public static String formatErrorForUser(String errorCode) {
        return String.format("%s: %s", errorCode, getErrorMessage(errorCode));
    }

    /**
     * Add custom error message
     */
    public static void addCustomErrorMessage(String errorCode, String message) {
        ERROR_MESSAGES.put(errorCode, message);
    }

    /**
     * Add custom error description
     */
    public static void addCustomErrorDescription(String errorCode, String description) {
        ERROR_DESCRIPTIONS.put(errorCode, description);
    }

    /**
     * Remove custom error message
     */
    public static void removeCustomErrorMessage(String errorCode) {
        ERROR_MESSAGES.remove(errorCode);
    }

    /**
     * Remove custom error description
     */
    public static void removeCustomErrorDescription(String errorCode) {
        ERROR_DESCRIPTIONS.remove(errorCode);
    }

    /**
     * Get all error codes
     */
    public static java.util.Set<String> getAllErrorCodes() {
        return ERROR_MESSAGES.keySet();
    }

    /**
     * Get all error messages
     */
    public static Map<String, String> getAllErrorMessages() {
        return new HashMap<>(ERROR_MESSAGES);
    }

    /**
     * Get all error descriptions
     */
    public static Map<String, String> getAllErrorDescriptions() {
        return new HashMap<>(ERROR_DESCRIPTIONS);
    }

    private ERRORUtils() {
        // Private constructor to prevent instantiation
    }
}