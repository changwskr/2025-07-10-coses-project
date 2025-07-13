package com.skcc.oversea.eplatonframework.business.controller;

import com.skcc.oversea.eplatonframework.business.dto.ServiceResponse;
import com.skcc.oversea.eplatonframework.business.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Base Controller with common response handling
 */
@RestControllerAdvice
public abstract class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Create success response
     */
    protected <T> ResponseEntity<ServiceResponse<T>> success(T data) {
        ServiceResponse<T> response = new ServiceResponse<>();
        response.setSuccess(true);
        response.setData(data);
        response.setMessage("Success");
        return ResponseEntity.ok(response);
    }

    /**
     * Create success response with message
     */
    protected <T> ResponseEntity<ServiceResponse<T>> success(T data, String message) {
        ServiceResponse<T> response = new ServiceResponse<>();
        response.setSuccess(true);
        response.setData(data);
        response.setMessage(message);
        return ResponseEntity.ok(response);
    }

    /**
     * Create success response for list
     */
    protected <T> ResponseEntity<ServiceResponse<List<T>>> successList(List<T> data) {
        ServiceResponse<List<T>> response = new ServiceResponse<>();
        response.setSuccess(true);
        response.setData(data);
        response.setMessage("Success");
        return ResponseEntity.ok(response);
    }

    /**
     * Create error response
     */
    protected <T> ResponseEntity<ServiceResponse<T>> error(String message) {
        ServiceResponse<T> response = new ServiceResponse<>();
        response.setSuccess(false);
        response.setMessage(message);
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Create error response with status
     */
    protected <T> ResponseEntity<ServiceResponse<T>> error(String message, HttpStatus status) {
        ServiceResponse<T> response = new ServiceResponse<>();
        response.setSuccess(false);
        response.setMessage(message);
        return ResponseEntity.status(status).body(response);
    }

    /**
     * Handle business exceptions
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ServiceResponse<Object>> handleBusinessException(BusinessException e) {
        logger.error("Business exception occurred: {}", e.getMessage(), e);
        ServiceResponse<Object> response = new ServiceResponse<>();
        response.setSuccess(false);
        response.setMessage(e.getMessage());
        response.setErrorCode(e.getErrorCode());
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Handle general exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServiceResponse<Object>> handleException(Exception e) {
        logger.error("Unexpected error occurred: {}", e.getMessage(), e);
        ServiceResponse<Object> response = new ServiceResponse<>();
        response.setSuccess(false);
        response.setMessage("Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}