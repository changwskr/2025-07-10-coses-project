package com.banking.coses.framework.config;

import com.banking.foundation.config.FoundationConfig;
import com.banking.foundation.log.FoundationLogger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration class for the COSES Framework
 * 
 * Provides Spring Boot configuration for the framework including
 * component scanning, transaction management, and scheduling.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.banking.coses.framework.business",
        "com.banking.coses.framework.transfer",
        "com.banking.coses.framework.exception",
        "com.banking.coses.framework.constants"
})
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
@Import(FoundationConfig.class)
public class CosesFrameworkConfig {

    private static final FoundationLogger logger = FoundationLogger.getLogger(CosesFrameworkConfig.class);

    public CosesFrameworkConfig() {
        logger.info("COSES Framework Configuration initialized");
    }

    /**
     * Framework properties configuration
     */
    @Bean
    @ConditionalOnMissingBean
    public CosesFrameworkProperties cosesFrameworkProperties() {
        return new CosesFrameworkProperties();
    }

    /**
     * Framework event configuration
     */
    @Bean
    @ConditionalOnMissingBean
    public CosesEventConfig cosesEventConfig() {
        return new CosesEventConfig();
    }

    /**
     * Framework validation configuration
     */
    @Bean
    @ConditionalOnMissingBean
    public CosesValidationConfig cosesValidationConfig() {
        return new CosesValidationConfig();
    }

    /**
     * Framework properties class
     */
    public static class CosesFrameworkProperties {

        private String frameworkVersion = "1.0.0";
        private String defaultEncoding = "UTF-8";
        private String defaultLocale = "ko_KR";
        private int defaultTimeout = 30000;
        private int maxRetries = 3;
        private long retryDelay = 1000L;
        private boolean enableAudit = true;
        private boolean enableMetrics = true;
        private boolean enableTracing = true;

        // Getters and Setters
        public String getFrameworkVersion() {
            return frameworkVersion;
        }

        public void setFrameworkVersion(String frameworkVersion) {
            this.frameworkVersion = frameworkVersion;
        }

        public String getDefaultEncoding() {
            return defaultEncoding;
        }

        public void setDefaultEncoding(String defaultEncoding) {
            this.defaultEncoding = defaultEncoding;
        }

        public String getDefaultLocale() {
            return defaultLocale;
        }

        public void setDefaultLocale(String defaultLocale) {
            this.defaultLocale = defaultLocale;
        }

        public int getDefaultTimeout() {
            return defaultTimeout;
        }

        public void setDefaultTimeout(int defaultTimeout) {
            this.defaultTimeout = defaultTimeout;
        }

        public int getMaxRetries() {
            return maxRetries;
        }

        public void setMaxRetries(int maxRetries) {
            this.maxRetries = maxRetries;
        }

        public long getRetryDelay() {
            return retryDelay;
        }

        public void setRetryDelay(long retryDelay) {
            this.retryDelay = retryDelay;
        }

        public boolean isEnableAudit() {
            return enableAudit;
        }

        public void setEnableAudit(boolean enableAudit) {
            this.enableAudit = enableAudit;
        }

        public boolean isEnableMetrics() {
            return enableMetrics;
        }

        public void setEnableMetrics(boolean enableMetrics) {
            this.enableMetrics = enableMetrics;
        }

        public boolean isEnableTracing() {
            return enableTracing;
        }

        public void setEnableTracing(boolean enableTracing) {
            this.enableTracing = enableTracing;
        }
    }

    /**
     * Event configuration class
     */
    public static class CosesEventConfig {

        private String defaultEventType = "BUSINESS";
        private String defaultEventStatus = "PENDING";
        private String defaultPriority = "NORMAL";
        private long defaultTimeout = 30000L;
        private int maxRetries = 3;
        private long retryDelay = 1000L;
        private boolean enableEventLogging = true;
        private boolean enableEventMetrics = true;

        // Getters and Setters
        public String getDefaultEventType() {
            return defaultEventType;
        }

        public void setDefaultEventType(String defaultEventType) {
            this.defaultEventType = defaultEventType;
        }

        public String getDefaultEventStatus() {
            return defaultEventStatus;
        }

        public void setDefaultEventStatus(String defaultEventStatus) {
            this.defaultEventStatus = defaultEventStatus;
        }

        public String getDefaultPriority() {
            return defaultPriority;
        }

        public void setDefaultPriority(String defaultPriority) {
            this.defaultPriority = defaultPriority;
        }

        public long getDefaultTimeout() {
            return defaultTimeout;
        }

        public void setDefaultTimeout(long defaultTimeout) {
            this.defaultTimeout = defaultTimeout;
        }

        public int getMaxRetries() {
            return maxRetries;
        }

        public void setMaxRetries(int maxRetries) {
            this.maxRetries = maxRetries;
        }

        public long getRetryDelay() {
            return retryDelay;
        }

        public void setRetryDelay(long retryDelay) {
            this.retryDelay = retryDelay;
        }

        public boolean isEnableEventLogging() {
            return enableEventLogging;
        }

        public void setEnableEventLogging(boolean enableEventLogging) {
            this.enableEventLogging = enableEventLogging;
        }

        public boolean isEnableEventMetrics() {
            return enableEventMetrics;
        }

        public void setEnableEventMetrics(boolean enableEventMetrics) {
            this.enableEventMetrics = enableEventMetrics;
        }
    }

    /**
     * Validation configuration class
     */
    public static class CosesValidationConfig {

        private boolean enableValidation = true;
        private boolean enableCustomValidation = true;
        private int maxStringLength = 1000;
        private int maxIdLength = 50;
        private int maxDescriptionLength = 500;
        private boolean enableCrossFieldValidation = true;

        // Getters and Setters
        public boolean isEnableValidation() {
            return enableValidation;
        }

        public void setEnableValidation(boolean enableValidation) {
            this.enableValidation = enableValidation;
        }

        public boolean isEnableCustomValidation() {
            return enableCustomValidation;
        }

        public void setEnableCustomValidation(boolean enableCustomValidation) {
            this.enableCustomValidation = enableCustomValidation;
        }

        public int getMaxStringLength() {
            return maxStringLength;
        }

        public void setMaxStringLength(int maxStringLength) {
            this.maxStringLength = maxStringLength;
        }

        public int getMaxIdLength() {
            return maxIdLength;
        }

        public void setMaxIdLength(int maxIdLength) {
            this.maxIdLength = maxIdLength;
        }

        public int getMaxDescriptionLength() {
            return maxDescriptionLength;
        }

        public void setMaxDescriptionLength(int maxDescriptionLength) {
            this.maxDescriptionLength = maxDescriptionLength;
        }

        public boolean isEnableCrossFieldValidation() {
            return enableCrossFieldValidation;
        }

        public void setEnableCrossFieldValidation(boolean enableCrossFieldValidation) {
            this.enableCrossFieldValidation = enableCrossFieldValidation;
        }
    }
}