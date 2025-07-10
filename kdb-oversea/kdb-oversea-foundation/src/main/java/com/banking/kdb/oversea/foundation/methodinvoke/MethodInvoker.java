package com.banking.kdb.oversea.foundation.methodinvoke;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.foundation.logej.LOGEJ;
import com.banking.kdb.oversea.foundation.utility.CommonUtil;

import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Method Invoker utility for KDB Oversea Foundation
 * 
 * Provides utilities for method invocation with logging, timing, and error
 * handling.
 */
public class MethodInvoker {

    private static final FoundationLogger logger = FoundationLogger.getLogger(MethodInvoker.class);
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * Invoke method with logging and timing
     */
    public static <T> T invokeWithLogging(String methodName, String context, Supplier<T> method) {
        long startTime = System.currentTimeMillis();
        String transactionId = CommonUtil.generateUniqueId();

        try {
            LOGEJ.logTransactionStep("METHOD_INVOKE", transactionId, "START",
                    String.format("Method: %s, Context: %s", methodName, context));

            T result = method.get();

            long duration = System.currentTimeMillis() - startTime;
            LOGEJ.logTransactionStep("METHOD_INVOKE", transactionId, "SUCCESS",
                    String.format("Method: %s, Duration: %dms", methodName, duration));

            LOGEJ.logPerformanceMetric(methodName + "_duration", String.valueOf(duration), "ms");

            return result;

        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            LOGEJ.logTransactionStep("METHOD_INVOKE", transactionId, "ERROR",
                    String.format("Method: %s, Duration: %dms, Error: %s", methodName, duration, e.getMessage()));

            LOGEJ.logError(String.format("Method invocation failed - Method: %s, Context: %s", methodName, context),
                    "METHOD_INVOKE", "ERROR", methodName, e);

            throw new RuntimeException("Method invocation failed: " + methodName, e);
        }
    }

    /**
     * Invoke method with logging and timing (void return)
     */
    public static void invokeWithLogging(String methodName, String context, Runnable method) {
        long startTime = System.currentTimeMillis();
        String transactionId = CommonUtil.generateUniqueId();

        try {
            LOGEJ.logTransactionStep("METHOD_INVOKE", transactionId, "START",
                    String.format("Method: %s, Context: %s", methodName, context));

            method.run();

            long duration = System.currentTimeMillis() - startTime;
            LOGEJ.logTransactionStep("METHOD_INVOKE", transactionId, "SUCCESS",
                    String.format("Method: %s, Duration: %dms", methodName, duration));

            LOGEJ.logPerformanceMetric(methodName + "_duration", String.valueOf(duration), "ms");

        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            LOGEJ.logTransactionStep("METHOD_INVOKE", transactionId, "ERROR",
                    String.format("Method: %s, Duration: %dms, Error: %s", methodName, duration, e.getMessage()));

            LOGEJ.logError(String.format("Method invocation failed - Method: %s, Context: %s", methodName, context),
                    "METHOD_INVOKE", "ERROR", methodName, e);

            throw new RuntimeException("Method invocation failed: " + methodName, e);
        }
    }

    /**
     * Invoke method asynchronously
     */
    public static <T> CompletableFuture<T> invokeAsync(String methodName, String context, Supplier<T> method) {
        return CompletableFuture.supplyAsync(() -> {
            return invokeWithLogging(methodName, context, method);
        }, executorService);
    }

    /**
     * Invoke method asynchronously (void return)
     */
    public static CompletableFuture<Void> invokeAsync(String methodName, String context, Runnable method) {
        return CompletableFuture.runAsync(() -> {
            invokeWithLogging(methodName, context, method);
        }, executorService);
    }

    /**
     * Invoke method with timeout
     */
    public static <T> T invokeWithTimeout(String methodName, String context, Supplier<T> method, long timeoutMs) {
        try {
            CompletableFuture<T> future = invokeAsync(methodName, context, method);
            return future.get(timeoutMs, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            LOGEJ.logError(String.format("Method invocation timeout - Method: %s, Context: %s, Timeout: %dms",
                    methodName, context, timeoutMs), "METHOD_INVOKE", "TIMEOUT", methodName, e);
            throw new RuntimeException("Method invocation timeout: " + methodName, e);
        }
    }

    /**
     * Invoke method with retry logic
     */
    public static <T> T invokeWithRetry(String methodName, String context, Supplier<T> method, int maxRetries,
            long delayMs) {
        Exception lastException = null;

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                if (attempt > 1) {
                    LOGEJ.logInfo(String.format("Retry attempt %d for method: %s", attempt, methodName),
                            "METHOD_INVOKE", "RETRY", methodName);
                    Thread.sleep(delayMs);
                }

                return invokeWithLogging(methodName, context, method);

            } catch (Exception e) {
                lastException = e;
                LOGEJ.logWarn(String.format("Method invocation failed (attempt %d/%d) - Method: %s, Context: %s",
                        attempt, maxRetries, methodName, context), "METHOD_INVOKE", "RETRY", methodName);

                if (attempt == maxRetries) {
                    LOGEJ.logError(String.format("Method invocation failed after %d attempts - Method: %s, Context: %s",
                            maxRetries, methodName, context), "METHOD_INVOKE", "MAX_RETRIES", methodName, e);
                }
            }
        }

        throw new RuntimeException("Method invocation failed after " + maxRetries + " attempts: " + methodName,
                lastException);
    }

    /**
     * Invoke method with circuit breaker pattern
     */
    public static <T> T invokeWithCircuitBreaker(String methodName, String context, Supplier<T> method,
            CircuitBreaker circuitBreaker) {
        if (!circuitBreaker.canExecute()) {
            LOGEJ.logWarn(String.format("Circuit breaker is open - Method: %s, Context: %s", methodName, context),
                    "METHOD_INVOKE", "CIRCUIT_BREAKER", methodName);
            throw new RuntimeException("Circuit breaker is open for method: " + methodName);
        }

        try {
            T result = invokeWithLogging(methodName, context, method);
            circuitBreaker.recordSuccess();
            return result;

        } catch (Exception e) {
            circuitBreaker.recordFailure();
            throw e;
        }
    }

    /**
     * Invoke method with fallback
     */
    public static <T> T invokeWithFallback(String methodName, String context, Supplier<T> method,
            Supplier<T> fallbackMethod) {
        try {
            return invokeWithLogging(methodName, context, method);

        } catch (Exception e) {
            LOGEJ.logWarn(String.format("Primary method failed, using fallback - Method: %s, Context: %s",
                    methodName, context), "METHOD_INVOKE", "FALLBACK", methodName);

            return invokeWithLogging(methodName + "_fallback", context, fallbackMethod);
        }
    }

    /**
     * Invoke method with validation
     */
    public static <T> T invokeWithValidation(String methodName, String context, Supplier<T> method,
            java.util.function.Predicate<T> validator) {
        T result = invokeWithLogging(methodName, context, method);

        if (!validator.test(result)) {
            LOGEJ.logError(String.format("Method result validation failed - Method: %s, Context: %s",
                    methodName, context), "METHOD_INVOKE", "VALIDATION", methodName);
            throw new RuntimeException("Method result validation failed: " + methodName);
        }

        return result;
    }

    /**
     * Invoke method with pre and post processing
     */
    public static <T> T invokeWithProcessing(String methodName, String context, Supplier<T> method,
            Runnable preProcessor, java.util.function.Consumer<T> postProcessor) {
        // Pre-processing
        if (preProcessor != null) {
            invokeWithLogging(methodName + "_pre", context, preProcessor);
        }

        // Main method execution
        T result = invokeWithLogging(methodName, context, method);

        // Post-processing
        if (postProcessor != null) {
            invokeWithLogging(methodName + "_post", context, () -> postProcessor.accept(result));
        }

        return result;
    }

    /**
     * Invoke method with parameter logging
     */
    public static <T> T invokeWithParameterLogging(String methodName, String context, Object[] parameters,
            Supplier<T> method) {
        String paramString = CommonUtil.toJson(parameters);
        LOGEJ.logDebug(String.format("Method parameters - Method: %s, Parameters: %s", methodName, paramString),
                "METHOD_INVOKE", "PARAMETERS", methodName);

        return invokeWithLogging(methodName, context, method);
    }

    /**
     * Invoke method with result logging
     */
    public static <T> T invokeWithResultLogging(String methodName, String context, Supplier<T> method) {
        T result = invokeWithLogging(methodName, context, method);

        String resultString = CommonUtil.toJson(result);
        LOGEJ.logDebug(String.format("Method result - Method: %s, Result: %s", methodName, resultString),
                "METHOD_INVOKE", "RESULT", methodName);

        return result;
    }

    /**
     * Shutdown the executor service
     */
    public static void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Circuit Breaker implementation
     */
    public static class CircuitBreaker {
        private final int failureThreshold;
        private final long timeoutMs;
        private final long resetTimeoutMs;

        private int failureCount = 0;
        private long lastFailureTime = 0;
        private boolean isOpen = false;

        public CircuitBreaker(int failureThreshold, long timeoutMs, long resetTimeoutMs) {
            this.failureThreshold = failureThreshold;
            this.timeoutMs = timeoutMs;
            this.resetTimeoutMs = resetTimeoutMs;
        }

        public boolean canExecute() {
            if (isOpen) {
                if (System.currentTimeMillis() - lastFailureTime > resetTimeoutMs) {
                    isOpen = false;
                    failureCount = 0;
                    return true;
                }
                return false;
            }
            return true;
        }

        public void recordSuccess() {
            failureCount = 0;
            isOpen = false;
        }

        public void recordFailure() {
            failureCount++;
            lastFailureTime = System.currentTimeMillis();

            if (failureCount >= failureThreshold) {
                isOpen = true;
            }
        }

        public boolean isOpen() {
            return isOpen;
        }

        public int getFailureCount() {
            return failureCount;
        }
    }

    private MethodInvoker() {
        // Private constructor to prevent instantiation
    }
}