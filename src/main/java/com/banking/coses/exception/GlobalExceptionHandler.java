package com.banking.coses.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global Exception Handler for COSES Framework
 * 
 * Provides centralized exception handling for all COSES operations.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handle CosesAppException
     */
    @ExceptionHandler(CosesAppException.class)
    public ResponseEntity<Map<String, Object>> handleCosesAppException(
            CosesAppException ex, WebRequest request) {

        logger.error("COSES Application Exception: {}", ex.getMessage(), ex);

        Map<String, Object> errorResponse = createErrorResponse(
                ex.getErrorCode(),
                ex.getErrorMessage(),
                ex.getHttpStatus(),
                request);

        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }

    /**
     * Handle IllegalArgumentException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {

        logger.error("Illegal Argument Exception: {}", ex.getMessage(), ex);

        Map<String, Object> errorResponse = createErrorResponse(
                "EINV002",
                "Invalid argument: " + ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                request);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * Handle NullPointerException
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String, Object>> handleNullPointerException(
            NullPointerException ex, WebRequest request) {

        logger.error("Null Pointer Exception: {}", ex.getMessage(), ex);

        Map<String, Object> errorResponse = createErrorResponse(
                "ENULL001",
                "Null reference encountered",
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);

        return ResponseEntity.internalServerError().body(errorResponse);
    }

    /**
     * Handle generic Exception
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(
            Exception ex, WebRequest request) {

        logger.error("Generic Exception: {}", ex.getMessage(), ex);

        Map<String, Object> errorResponse = createErrorResponse(
                "EUNK001",
                "Unexpected error occurred: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);

        return ResponseEntity.internalServerError().body(errorResponse);
    }

    /**
     * Create error response
     */
    private Map<String, Object> createErrorResponse(
            String errorCode,
            String message,
            HttpStatus status,
            WebRequest request) {

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("errorCode", errorCode);
        errorResponse.put("message", message);
        errorResponse.put("status", status.value());
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("path", request.getDescription(false));

        return errorResponse;
    }
}