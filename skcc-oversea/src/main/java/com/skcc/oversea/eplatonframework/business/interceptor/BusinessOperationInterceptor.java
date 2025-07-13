package com.skcc.oversea.eplatonframework.business.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.skcc.oversea.eplatonframework.business.annotation.BusinessOperation;
import com.skcc.oversea.eplatonframework.transfer.EPlatonEvent;
import com.skcc.oversea.foundation.logej.LOGEJ;
import com.skcc.oversea.foundation.constant.Constants;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Business Operation Interceptor for SKCC Oversea
 * 
 * Intercepts business method calls to provide logging, monitoring, and
 * auditing.
 */
@Aspect
@Component
public class BusinessOperationInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(BusinessOperationInterceptor.class);

    /**
     * Intercept methods annotated with @BusinessOperation
     */
    @Around("@annotation(com.skcc.oversea.eplatonframework.business.annotation.BusinessOperation)")
    public Object interceptBusinessOperation(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        BusinessOperation annotation = method.getAnnotation(BusinessOperation.class);

        String operationName = annotation.value().isEmpty() ? method.getName() : annotation.value();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = method.getName();

        LocalDateTime startTime = LocalDateTime.now();
        String transactionId = generateTransactionId();

        // Pre-execution logging
        if (annotation.loggable()) {
            logger.info("Starting business operation: {} - Class: {} - Method: {} - TransactionId: {}",
                    operationName, className, methodName, transactionId);
        }

        // Pre-execution audit
        if (annotation.auditable()) {
            auditBusinessOperation(transactionId, operationName, className, methodName, "START", null);
        }

        try {
            // Execute the business method
            Object result = joinPoint.proceed();

            // Calculate execution time
            LocalDateTime endTime = LocalDateTime.now();
            long executionTime = ChronoUnit.MILLISECONDS.between(startTime, endTime);

            // Post-execution logging
            if (annotation.loggable()) {
                logger.info("Completed business operation: {} - Execution time: {}ms - TransactionId: {}",
                        operationName, executionTime, transactionId);
            }

            // Post-execution audit
            if (annotation.auditable()) {
                auditBusinessOperation(transactionId, operationName, className, methodName, "SUCCESS", executionTime);
            }

            // Performance logging
            LOGEJ.logPerformance(operationName, executionTime,
                    String.format("Class: %s, Method: %s", className, methodName));

            return result;

        } catch (Exception e) {
            // Calculate execution time
            LocalDateTime endTime = LocalDateTime.now();
            long executionTime = ChronoUnit.MILLISECONDS.between(startTime, endTime);

            // Error logging
            logger.error("Failed business operation: {} - Error: {} - Execution time: {}ms - TransactionId: {}",
                    operationName, e.getMessage(), executionTime, transactionId, e);

            // Error audit
            if (annotation.auditable()) {
                auditBusinessOperation(transactionId, operationName, className, methodName, "ERROR", executionTime);
            }

            // Error performance logging
            LOGEJ.logError(annotation.errorCodes().length > 0 ? annotation.errorCodes()[0] : "EBUS001",
                    "Business operation failed: " + e.getMessage(), operationName, e);

            throw e;
        }
    }

    /**
     * Generate transaction ID
     */
    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis() + "_" + Thread.currentThread().getId();
    }

    /**
     * Audit business operation
     */
    private void auditBusinessOperation(String transactionId, String operationName, String className,
            String methodName, String status, Long executionTime) {
        try {
            // Create audit log entry
            String auditMessage = String.format(
                    "TransactionId: %s, Operation: %s, Class: %s, Method: %s, Status: %s, ExecutionTime: %sms",
                    transactionId, operationName, className, methodName, status,
                    executionTime != null ? executionTime : "N/A");

            // Log to audit system
            LOGEJ.logAudit(transactionId, operationName, status, auditMessage);

        } catch (Exception e) {
            logger.warn("Failed to audit business operation: {}", e.getMessage());
        }
    }

    /**
     * Extract EPlatonEvent from method arguments
     */
    private EPlatonEvent extractEPlatonEvent(Object[] args) {
        if (args != null) {
            for (Object arg : args) {
                if (arg instanceof EPlatonEvent) {
                    return (EPlatonEvent) arg;
                }
            }
        }
        return null;
    }
}