package com.banking.framework.exception;

import com.banking.framework.transfer.CosesCommonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global Exception Handler for Coses Framework (Spring Boot Version)
 * 
 * Provides centralized exception handling for all framework components.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handle CosesAppException
     */
    @ExceptionHandler(CosesAppException.class)
    public ResponseEntity<CosesCommonDTO> handleCosesAppException(CosesAppException ex, WebRequest request) {
        logger.error("CosesAppException occurred: {}", ex.getMessage(), ex);

        CosesCommonDTO errorResponse = new CosesCommonDTO();
        errorResponse.markError(ex.getErrorCode(), ex.getErrorMessage());
        errorResponse.setDetails(ex.getDetails());
        errorResponse.setSource(ex.getSource());

        HttpStatus status = ex.getHttpStatus() != null ? ex.getHttpStatus() : HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(errorResponse, status);
    }

    /**
     * Handle validation exceptions
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CosesCommonDTO> handleValidationException(ConstraintViolationException ex,
            WebRequest request) {
        logger.error("Validation exception occurred: {}", ex.getMessage(), ex);

        CosesCommonDTO errorResponse = new CosesCommonDTO();
        errorResponse.markError("VALIDATION_ERROR", "Validation failed");
        errorResponse.setDetails(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle IllegalArgumentException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CosesCommonDTO> handleIllegalArgumentException(IllegalArgumentException ex,
            WebRequest request) {
        logger.error("IllegalArgumentException occurred: {}", ex.getMessage(), ex);

        CosesCommonDTO errorResponse = new CosesCommonDTO();
        errorResponse.markError("INVALID_ARGUMENT", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle NullPointerException
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<CosesCommonDTO> handleNullPointerException(NullPointerException ex, WebRequest request) {
        logger.error("NullPointerException occurred: {}", ex.getMessage(), ex);

        CosesCommonDTO errorResponse = new CosesCommonDTO();
        errorResponse.markError("NULL_POINTER_ERROR", "Null pointer exception occurred");

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle generic RuntimeException
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CosesCommonDTO> handleRuntimeException(RuntimeException ex, WebRequest request) {
        logger.error("RuntimeException occurred: {}", ex.getMessage(), ex);

        CosesCommonDTO errorResponse = new CosesCommonDTO();
        errorResponse.markError("RUNTIME_ERROR", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle generic Exception
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CosesCommonDTO> handleGenericException(Exception ex, WebRequest request) {
        logger.error("Generic exception occurred: {}", ex.getMessage(), ex);

        CosesCommonDTO errorResponse = new CosesCommonDTO();
        errorResponse.markError("GENERIC_ERROR", "An unexpected error occurred");

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Create error response with additional details
     */
    private Map<String, Object> createErrorDetails(Exception ex, WebRequest request) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("path", request.getDescription(false));
        errorDetails.put("exception", ex.getClass().getSimpleName());

        return errorDetails;
    }
}