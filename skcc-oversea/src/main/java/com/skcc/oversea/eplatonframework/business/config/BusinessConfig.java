package com.skcc.oversea.eplatonframework.business.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.skcc.oversea.eplatonframework.business.interceptor.BusinessOperationInterceptor;

/**
 * Business Configuration for SKCC Oversea
 * 
 * Provides configuration for business layer components.
 */
@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableAsync
@EnableScheduling
public class BusinessConfig {

    /**
     * Business Operation Interceptor Bean
     */
    @Bean
    public BusinessOperationInterceptor businessOperationInterceptor() {
        return new BusinessOperationInterceptor();
    }

    /**
     * Business Properties Bean
     */
    @Bean
    public BusinessProperties businessProperties() {
        return new BusinessProperties();
    }

    /**
     * Business Properties Class
     */
    public static class BusinessProperties {

        private int defaultTimeout = 300;
        private int maxRetryAttempts = 3;
        private boolean enableAudit = true;
        private boolean enablePerformanceLogging = true;
        private String defaultLanguage = "ko";
        private String defaultCurrency = "KRW";

        // Getters and Setters
        public int getDefaultTimeout() {
            return defaultTimeout;
        }

        public void setDefaultTimeout(int defaultTimeout) {
            this.defaultTimeout = defaultTimeout;
        }

        public int getMaxRetryAttempts() {
            return maxRetryAttempts;
        }

        public void setMaxRetryAttempts(int maxRetryAttempts) {
            this.maxRetryAttempts = maxRetryAttempts;
        }

        public boolean isEnableAudit() {
            return enableAudit;
        }

        public void setEnableAudit(boolean enableAudit) {
            this.enableAudit = enableAudit;
        }

        public boolean isEnablePerformanceLogging() {
            return enablePerformanceLogging;
        }

        public void setEnablePerformanceLogging(boolean enablePerformanceLogging) {
            this.enablePerformanceLogging = enablePerformanceLogging;
        }

        public String getDefaultLanguage() {
            return defaultLanguage;
        }

        public void setDefaultLanguage(String defaultLanguage) {
            this.defaultLanguage = defaultLanguage;
        }

        public String getDefaultCurrency() {
            return defaultCurrency;
        }

        public void setDefaultCurrency(String defaultCurrency) {
            this.defaultCurrency = defaultCurrency;
        }
    }
}