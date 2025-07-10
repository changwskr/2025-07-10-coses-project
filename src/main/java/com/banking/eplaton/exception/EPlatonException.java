package com.banking.eplaton.exception;

import com.banking.framework.exception.CosesAppException;

/**
 * EPlaton Exception
 * 
 * Custom exception for EPlaton framework errors.
 */
public class EPlatonException extends CosesAppException {

    private static final long serialVersionUID = 1L;

    public EPlatonException(String errorCode, String message) {
        super(errorCode, message);
    }

    public EPlatonException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    /**
     * Create EPlaton exception with predefined error codes
     */
    public static EPlatonException invalidEvent(String message) {
        return new EPlatonException("EPLATON001", "Invalid event: " + message);
    }

    public static EPlatonException actionNotFound(String actionName) {
        return new EPlatonException("EPLATON002", "Action not found: " + actionName);
    }

    public static EPlatonException actionInstantiationError(String actionName, Throwable cause) {
        return new EPlatonException("EPLATON003", "Error creating action instance: " + actionName, cause);
    }

    public static EPlatonException invalidActionType(String actionName) {
        return new EPlatonException("EPLATON004", "Invalid action type: " + actionName);
    }

    public static EPlatonException executionError(String message, Throwable cause) {
        return new EPlatonException("EPLATON005", "Execution error: " + message, cause);
    }

    public static EPlatonException timeoutError(String actionName, int timeoutSeconds) {
        return new EPlatonException("EPLATON006",
                "Action timeout: " + actionName + " exceeded " + timeoutSeconds + " seconds");
    }

    public static EPlatonException transactionError(String message, Throwable cause) {
        return new EPlatonException("EPLATON007", "Transaction error: " + message, cause);
    }
}