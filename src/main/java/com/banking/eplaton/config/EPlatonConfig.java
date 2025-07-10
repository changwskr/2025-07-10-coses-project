package com.banking.eplaton.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * EPlaton Configuration
 * 
 * Configuration class for EPlaton framework components.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.banking.eplaton.business",
        "com.banking.eplaton.service",
        "com.banking.eplaton.controller"
})
@EnableAsync
@EnableScheduling
public class EPlatonConfig {

    /**
     * EPlaton framework properties
     */
    public static class EPlatonProperties {
        public static final String FRAMEWORK_NAME = "EPlaton";
        public static final String FRAMEWORK_VERSION = "2.0.0";
        public static final int DEFAULT_TIMEOUT_SECONDS = 60;
        public static final int MAX_CACHE_SIZE = 1000;
        public static final boolean ENABLE_CACHE = true;
        public static final boolean ENABLE_LOGGING = true;
    }

    /**
     * EPlaton error codes
     */
    public static class EPlatonErrorCodes {
        public static final String INVALID_EVENT = "EPLATON001";
        public static final String ACTION_NOT_FOUND = "EPLATON002";
        public static final String ACTION_INSTANTIATION_ERROR = "EPLATON003";
        public static final String INVALID_ACTION_TYPE = "EPLATON004";
        public static final String EXECUTION_ERROR = "EPLATON005";
        public static final String TIMEOUT_ERROR = "EPLATON006";
        public static final String TRANSACTION_ERROR = "EPLATON007";
    }

    /**
     * EPlaton system names
     */
    public static class EPlatonSystems {
        public static final String CASH_CARD = "CashCard";
        public static final String CUSTOMER = "Customer";
        public static final String DEPOSIT = "Deposit";
        public static final String TRANSACTION = "Transaction";
        public static final String REFERENCE = "Reference";
    }

    /**
     * EPlaton operation types
     */
    public static class EPlatonOperations {
        public static final String CREATE = "CREATE";
        public static final String QUERY = "QUERY";
        public static final String UPDATE = "UPDATE";
        public static final String DELETE = "DELETE";
        public static final String TRANSACTION = "TRANSACTION";
        public static final String VALIDATE = "VALIDATE";
        public static final String PROCESS = "PROCESS";
    }
}