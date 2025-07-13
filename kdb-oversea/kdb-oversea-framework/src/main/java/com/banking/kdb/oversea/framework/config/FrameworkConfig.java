package com.banking.kdb.oversea.framework.config;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.framework.constant.FrameworkConstants;
import com.banking.kdb.oversea.framework.transaction.constant.TransactionConstants;
import com.banking.kdb.oversea.framework.transaction.tcf.TransactionControlFramework;
import com.banking.kdb.oversea.framework.transaction.tpmutil.TPMUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.Executor;

/**
 * Framework Configuration for KDB Oversea Framework
 * 
 * Provides configuration for the framework components.
 */
@Configuration
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
@PropertySource("classpath:framework.properties")
public class FrameworkConfig {

    private static final FoundationLogger logger = FoundationLogger.getLogger(FrameworkConfig.class);

    @Value("${framework.transaction.core-pool-size:10}")
    private int transactionCorePoolSize;

    @Value("${framework.transaction.max-pool-size:50}")
    private int transactionMaxPoolSize;

    @Value("${framework.transaction.queue-capacity:100}")
    private int transactionQueueCapacity;

    @Value("${framework.transaction.thread-name-prefix:transaction-}")
    private String transactionThreadNamePrefix;

    @Value("${framework.transfer.core-pool-size:5}")
    private int transferCorePoolSize;

    @Value("${framework.transfer.max-pool-size:20}")
    private int transferMaxPoolSize;

    @Value("${framework.transfer.queue-capacity:50}")
    private int transferQueueCapacity;

    @Value("${framework.transfer.thread-name-prefix:transfer-}")
    private String transferThreadNamePrefix;

    @Value("${framework.monitoring.enabled:true}")
    private boolean monitoringEnabled;

    @Value("${framework.monitoring.interval-seconds:30}")
    private int monitoringIntervalSeconds;

    @Value("${framework.retry.max-attempts:3}")
    private int maxRetryAttempts;

    @Value("${framework.retry.delay-ms:1000}")
    private long retryDelayMs;

    @Value("${framework.retry.backoff-multiplier:2.0}")
    private double retryBackoffMultiplier;

    @Value("${framework.timeout.transaction-seconds:30}")
    private int transactionTimeoutSeconds;

    @Value("${framework.timeout.authorization-seconds:60}")
    private int authorizationTimeoutSeconds;

    @Value("${framework.timeout.validation-seconds:10}")
    private int validationTimeoutSeconds;

    @Value("${framework.timeout.routing-seconds:5}")
    private int routingTimeoutSeconds;

    @Value("${framework.cache.enabled:true}")
    private boolean cacheEnabled;

    @Value("${framework.cache.ttl-transaction-seconds:300}")
    private int cacheTtlTransactionSeconds;

    @Value("${framework.cache.ttl-status-seconds:60}")
    private int cacheTtlStatusSeconds;

    @Value("${framework.cache.ttl-limits-seconds:3600}")
    private int cacheTtlLimitsSeconds;

    @Value("${framework.cache.ttl-routing-seconds:1800}")
    private int cacheTtlRoutingSeconds;

    @Value("${framework.security.enabled:true}")
    private boolean securityEnabled;

    @Value("${framework.security.roles.transaction-user:TRANSACTION_USER}")
    private String transactionUserRole;

    @Value("${framework.security.roles.transaction-admin:TRANSACTION_ADMIN}")
    private String transactionAdminRole;

    @Value("${framework.security.roles.transaction-authorizer:TRANSACTION_AUTHORIZER}")
    private String transactionAuthorizerRole;

    @Value("${framework.security.roles.transaction-auditor:TRANSACTION_AUDITOR}")
    private String transactionAuditorRole;

    @Value("${framework.compliance.enabled:true}")
    private boolean complianceEnabled;

    @Value("${framework.compliance.aml-check:true}")
    private boolean amlCheckEnabled;

    @Value("${framework.compliance.kyc-check:true}")
    private boolean kycCheckEnabled;

    @Value("${framework.compliance.sanctions-check:true}")
    private boolean sanctionsCheckEnabled;

    @Value("${framework.compliance.pep-check:true}")
    private boolean pepCheckEnabled;

    @Value("${framework.fraud.enabled:true}")
    private boolean fraudEnabled;

    @Value("${framework.fraud.risk-score-threshold:80}")
    private int fraudRiskScoreThreshold;

    @Value("${framework.fraud.pattern-analysis:true}")
    private boolean patternAnalysisEnabled;

    @Value("${framework.fraud.behavioral-analysis:true}")
    private boolean behavioralAnalysisEnabled;

    @Value("${framework.fraud.device-fingerprinting:true}")
    private boolean deviceFingerprintingEnabled;

    @Value("${framework.notification.enabled:true}")
    private boolean notificationEnabled;

    @Value("${framework.notification.email:true}")
    private boolean emailNotificationEnabled;

    @Value("${framework.notification.sms:false}")
    private boolean smsNotificationEnabled;

    @Value("${framework.notification.push:false}")
    private boolean pushNotificationEnabled;

    @Value("${framework.audit.enabled:true}")
    private boolean auditEnabled;

    @Value("${framework.audit.retention-days:90}")
    private int auditRetentionDays;

    @Value("${framework.audit.encryption:true}")
    private boolean auditEncryptionEnabled;

    @Value("${framework.reporting.enabled:true}")
    private boolean reportingEnabled;

    @Value("${framework.reporting.format:CSV}")
    private String defaultReportFormat;

    @Value("${framework.reporting.retention-days:365}")
    private int reportRetentionDays;

    @Value("${framework.maintenance.enabled:false}")
    private boolean maintenanceEnabled;

    @Value("${framework.maintenance.schedule:0 0 2 * * ?}")
    private String maintenanceSchedule;

    @Value("${framework.maintenance.duration-minutes:60}")
    private int maintenanceDurationMinutes;

    /**
     * Transaction processing executor
     */
    @Bean("transactionExecutor")
    public Executor transactionExecutor() {
        logger.info("Configuring transaction executor - Core: {}, Max: {}, Queue: {}",
                transactionCorePoolSize, transactionMaxPoolSize, transactionQueueCapacity);

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(transactionCorePoolSize);
        executor.setMaxPoolSize(transactionMaxPoolSize);
        executor.setQueueCapacity(transactionQueueCapacity);
        executor.setThreadNamePrefix(transactionThreadNamePrefix);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(30);
        executor.initialize();

        logger.info("Transaction executor configured successfully");
        return executor;
    }

    /**
     * Transfer processing executor
     */
    @Bean("transferExecutor")
    public Executor transferExecutor() {
        logger.info("Configuring transfer executor - Core: {}, Max: {}, Queue: {}",
                transferCorePoolSize, transferMaxPoolSize, transferQueueCapacity);

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(transferCorePoolSize);
        executor.setMaxPoolSize(transferMaxPoolSize);
        executor.setQueueCapacity(transferQueueCapacity);
        executor.setThreadNamePrefix(transferThreadNamePrefix);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(30);
        executor.initialize();

        logger.info("Transfer executor configured successfully");
        return executor;
    }

    /**
     * Framework constants configuration
     */
    @Bean
    public FrameworkConstants frameworkConstants() {
        logger.info("Configuring framework constants");

        // Update framework constants with configuration values
        FrameworkConstants.setMaxRetryAttempts(maxRetryAttempts);
        FrameworkConstants.setDefaultRetryDelayMs(retryDelayMs);
        FrameworkConstants.setTransactionTimeoutSeconds(transactionTimeoutSeconds);
        FrameworkConstants.setAuthorizationTimeoutSeconds(authorizationTimeoutSeconds);
        FrameworkConstants.setValidationTimeoutSeconds(validationTimeoutSeconds);
        FrameworkConstants.setRoutingTimeoutSeconds(routingTimeoutSeconds);

        logger.info("Framework constants configured successfully");
        return new FrameworkConstants();
    }

    /**
     * Transaction constants configuration
     */
    @Bean
    public TransactionConstants transactionConstants() {
        logger.info("Configuring transaction constants");

        // Update transaction constants with configuration values
        TransactionConstants.setDefaultMaxRetryAttempts(maxRetryAttempts);
        TransactionConstants.setDefaultRetryDelayMs(retryDelayMs);
        TransactionConstants.setDefaultBackoffMultiplier(retryBackoffMultiplier);
        TransactionConstants.setDefaultTransactionTimeoutSeconds(transactionTimeoutSeconds);
        TransactionConstants.setDefaultAuthorizationTimeoutSeconds(authorizationTimeoutSeconds);
        TransactionConstants.setDefaultValidationTimeoutSeconds(validationTimeoutSeconds);
        TransactionConstants.setDefaultRoutingTimeoutSeconds(routingTimeoutSeconds);

        logger.info("Transaction constants configured successfully");
        return new TransactionConstants();
    }

    /**
     * Framework properties
     */
    @Bean
    public FrameworkProperties frameworkProperties() {
        logger.info("Creating framework properties bean");

        FrameworkProperties properties = new FrameworkProperties();
        properties.setMonitoringEnabled(monitoringEnabled);
        properties.setMonitoringIntervalSeconds(monitoringIntervalSeconds);
        properties.setMaxRetryAttempts(maxRetryAttempts);
        properties.setRetryDelayMs(retryDelayMs);
        properties.setRetryBackoffMultiplier(retryBackoffMultiplier);
        properties.setTransactionTimeoutSeconds(transactionTimeoutSeconds);
        properties.setAuthorizationTimeoutSeconds(authorizationTimeoutSeconds);
        properties.setValidationTimeoutSeconds(validationTimeoutSeconds);
        properties.setRoutingTimeoutSeconds(routingTimeoutSeconds);
        properties.setCacheEnabled(cacheEnabled);
        properties.setCacheTtlTransactionSeconds(cacheTtlTransactionSeconds);
        properties.setCacheTtlStatusSeconds(cacheTtlStatusSeconds);
        properties.setCacheTtlLimitsSeconds(cacheTtlLimitsSeconds);
        properties.setCacheTtlRoutingSeconds(cacheTtlRoutingSeconds);
        properties.setSecurityEnabled(securityEnabled);
        properties.setTransactionUserRole(transactionUserRole);
        properties.setTransactionAdminRole(transactionAdminRole);
        properties.setTransactionAuthorizerRole(transactionAuthorizerRole);
        properties.setTransactionAuditorRole(transactionAuditorRole);
        properties.setComplianceEnabled(complianceEnabled);
        properties.setAmlCheckEnabled(amlCheckEnabled);
        properties.setKycCheckEnabled(kycCheckEnabled);
        properties.setSanctionsCheckEnabled(sanctionsCheckEnabled);
        properties.setPepCheckEnabled(pepCheckEnabled);
        properties.setFraudEnabled(fraudEnabled);
        properties.setFraudRiskScoreThreshold(fraudRiskScoreThreshold);
        properties.setPatternAnalysisEnabled(patternAnalysisEnabled);
        properties.setBehavioralAnalysisEnabled(behavioralAnalysisEnabled);
        properties.setDeviceFingerprintingEnabled(deviceFingerprintingEnabled);
        properties.setNotificationEnabled(notificationEnabled);
        properties.setEmailNotificationEnabled(emailNotificationEnabled);
        properties.setSmsNotificationEnabled(smsNotificationEnabled);
        properties.setPushNotificationEnabled(pushNotificationEnabled);
        properties.setAuditEnabled(auditEnabled);
        properties.setAuditRetentionDays(auditRetentionDays);
        properties.setAuditEncryptionEnabled(auditEncryptionEnabled);
        properties.setReportingEnabled(reportingEnabled);
        properties.setDefaultReportFormat(defaultReportFormat);
        properties.setReportRetentionDays(reportRetentionDays);
        properties.setMaintenanceEnabled(maintenanceEnabled);
        properties.setMaintenanceSchedule(maintenanceSchedule);
        properties.setMaintenanceDurationMinutes(maintenanceDurationMinutes);

        logger.info("Framework properties created successfully");
        return properties;
    }

    /**
     * Framework Properties class
     */
    public static class FrameworkProperties {
        private boolean monitoringEnabled;
        private int monitoringIntervalSeconds;
        private int maxRetryAttempts;
        private long retryDelayMs;
        private double retryBackoffMultiplier;
        private int transactionTimeoutSeconds;
        private int authorizationTimeoutSeconds;
        private int validationTimeoutSeconds;
        private int routingTimeoutSeconds;
        private boolean cacheEnabled;
        private int cacheTtlTransactionSeconds;
        private int cacheTtlStatusSeconds;
        private int cacheTtlLimitsSeconds;
        private int cacheTtlRoutingSeconds;
        private boolean securityEnabled;
        private String transactionUserRole;
        private String transactionAdminRole;
        private String transactionAuthorizerRole;
        private String transactionAuditorRole;
        private boolean complianceEnabled;
        private boolean amlCheckEnabled;
        private boolean kycCheckEnabled;
        private boolean sanctionsCheckEnabled;
        private boolean pepCheckEnabled;
        private boolean fraudEnabled;
        private int fraudRiskScoreThreshold;
        private boolean patternAnalysisEnabled;
        private boolean behavioralAnalysisEnabled;
        private boolean deviceFingerprintingEnabled;
        private boolean notificationEnabled;
        private boolean emailNotificationEnabled;
        private boolean smsNotificationEnabled;
        private boolean pushNotificationEnabled;
        private boolean auditEnabled;
        private int auditRetentionDays;
        private boolean auditEncryptionEnabled;
        private boolean reportingEnabled;
        private String defaultReportFormat;
        private int reportRetentionDays;
        private boolean maintenanceEnabled;
        private String maintenanceSchedule;
        private int maintenanceDurationMinutes;

        // Getters and setters
        public boolean isMonitoringEnabled() {
            return monitoringEnabled;
        }

        public void setMonitoringEnabled(boolean monitoringEnabled) {
            this.monitoringEnabled = monitoringEnabled;
        }

        public int getMonitoringIntervalSeconds() {
            return monitoringIntervalSeconds;
        }

        public void setMonitoringIntervalSeconds(int monitoringIntervalSeconds) {
            this.monitoringIntervalSeconds = monitoringIntervalSeconds;
        }

        public int getMaxRetryAttempts() {
            return maxRetryAttempts;
        }

        public void setMaxRetryAttempts(int maxRetryAttempts) {
            this.maxRetryAttempts = maxRetryAttempts;
        }

        public long getRetryDelayMs() {
            return retryDelayMs;
        }

        public void setRetryDelayMs(long retryDelayMs) {
            this.retryDelayMs = retryDelayMs;
        }

        public double getRetryBackoffMultiplier() {
            return retryBackoffMultiplier;
        }

        public void setRetryBackoffMultiplier(double retryBackoffMultiplier) {
            this.retryBackoffMultiplier = retryBackoffMultiplier;
        }

        public int getTransactionTimeoutSeconds() {
            return transactionTimeoutSeconds;
        }

        public void setTransactionTimeoutSeconds(int transactionTimeoutSeconds) {
            this.transactionTimeoutSeconds = transactionTimeoutSeconds;
        }

        public int getAuthorizationTimeoutSeconds() {
            return authorizationTimeoutSeconds;
        }

        public void setAuthorizationTimeoutSeconds(int authorizationTimeoutSeconds) {
            this.authorizationTimeoutSeconds = authorizationTimeoutSeconds;
        }

        public int getValidationTimeoutSeconds() {
            return validationTimeoutSeconds;
        }

        public void setValidationTimeoutSeconds(int validationTimeoutSeconds) {
            this.validationTimeoutSeconds = validationTimeoutSeconds;
        }

        public int getRoutingTimeoutSeconds() {
            return routingTimeoutSeconds;
        }

        public void setRoutingTimeoutSeconds(int routingTimeoutSeconds) {
            this.routingTimeoutSeconds = routingTimeoutSeconds;
        }

        public boolean isCacheEnabled() {
            return cacheEnabled;
        }

        public void setCacheEnabled(boolean cacheEnabled) {
            this.cacheEnabled = cacheEnabled;
        }

        public int getCacheTtlTransactionSeconds() {
            return cacheTtlTransactionSeconds;
        }

        public void setCacheTtlTransactionSeconds(int cacheTtlTransactionSeconds) {
            this.cacheTtlTransactionSeconds = cacheTtlTransactionSeconds;
        }

        public int getCacheTtlStatusSeconds() {
            return cacheTtlStatusSeconds;
        }

        public void setCacheTtlStatusSeconds(int cacheTtlStatusSeconds) {
            this.cacheTtlStatusSeconds = cacheTtlStatusSeconds;
        }

        public int getCacheTtlLimitsSeconds() {
            return cacheTtlLimitsSeconds;
        }

        public void setCacheTtlLimitsSeconds(int cacheTtlLimitsSeconds) {
            this.cacheTtlLimitsSeconds = cacheTtlLimitsSeconds;
        }

        public int getCacheTtlRoutingSeconds() {
            return cacheTtlRoutingSeconds;
        }

        public void setCacheTtlRoutingSeconds(int cacheTtlRoutingSeconds) {
            this.cacheTtlRoutingSeconds = cacheTtlRoutingSeconds;
        }

        public boolean isSecurityEnabled() {
            return securityEnabled;
        }

        public void setSecurityEnabled(boolean securityEnabled) {
            this.securityEnabled = securityEnabled;
        }

        public String getTransactionUserRole() {
            return transactionUserRole;
        }

        public void setTransactionUserRole(String transactionUserRole) {
            this.transactionUserRole = transactionUserRole;
        }

        public String getTransactionAdminRole() {
            return transactionAdminRole;
        }

        public void setTransactionAdminRole(String transactionAdminRole) {
            this.transactionAdminRole = transactionAdminRole;
        }

        public String getTransactionAuthorizerRole() {
            return transactionAuthorizerRole;
        }

        public void setTransactionAuthorizerRole(String transactionAuthorizerRole) {
            this.transactionAuthorizerRole = transactionAuthorizerRole;
        }

        public String getTransactionAuditorRole() {
            return transactionAuditorRole;
        }

        public void setTransactionAuditorRole(String transactionAuditorRole) {
            this.transactionAuditorRole = transactionAuditorRole;
        }

        public boolean isComplianceEnabled() {
            return complianceEnabled;
        }

        public void setComplianceEnabled(boolean complianceEnabled) {
            this.complianceEnabled = complianceEnabled;
        }

        public boolean isAmlCheckEnabled() {
            return amlCheckEnabled;
        }

        public void setAmlCheckEnabled(boolean amlCheckEnabled) {
            this.amlCheckEnabled = amlCheckEnabled;
        }

        public boolean isKycCheckEnabled() {
            return kycCheckEnabled;
        }

        public void setKycCheckEnabled(boolean kycCheckEnabled) {
            this.kycCheckEnabled = kycCheckEnabled;
        }

        public boolean isSanctionsCheckEnabled() {
            return sanctionsCheckEnabled;
        }

        public void setSanctionsCheckEnabled(boolean sanctionsCheckEnabled) {
            this.sanctionsCheckEnabled = sanctionsCheckEnabled;
        }

        public boolean isPepCheckEnabled() {
            return pepCheckEnabled;
        }

        public void setPepCheckEnabled(boolean pepCheckEnabled) {
            this.pepCheckEnabled = pepCheckEnabled;
        }

        public boolean isFraudEnabled() {
            return fraudEnabled;
        }

        public void setFraudEnabled(boolean fraudEnabled) {
            this.fraudEnabled = fraudEnabled;
        }

        public int getFraudRiskScoreThreshold() {
            return fraudRiskScoreThreshold;
        }

        public void setFraudRiskScoreThreshold(int fraudRiskScoreThreshold) {
            this.fraudRiskScoreThreshold = fraudRiskScoreThreshold;
        }

        public boolean isPatternAnalysisEnabled() {
            return patternAnalysisEnabled;
        }

        public void setPatternAnalysisEnabled(boolean patternAnalysisEnabled) {
            this.patternAnalysisEnabled = patternAnalysisEnabled;
        }

        public boolean isBehavioralAnalysisEnabled() {
            return behavioralAnalysisEnabled;
        }

        public void setBehavioralAnalysisEnabled(boolean behavioralAnalysisEnabled) {
            this.behavioralAnalysisEnabled = behavioralAnalysisEnabled;
        }

        public boolean isDeviceFingerprintingEnabled() {
            return deviceFingerprintingEnabled;
        }

        public void setDeviceFingerprintingEnabled(boolean deviceFingerprintingEnabled) {
            this.deviceFingerprintingEnabled = deviceFingerprintingEnabled;
        }

        public boolean isNotificationEnabled() {
            return notificationEnabled;
        }

        public void setNotificationEnabled(boolean notificationEnabled) {
            this.notificationEnabled = notificationEnabled;
        }

        public boolean isEmailNotificationEnabled() {
            return emailNotificationEnabled;
        }

        public void setEmailNotificationEnabled(boolean emailNotificationEnabled) {
            this.emailNotificationEnabled = emailNotificationEnabled;
        }

        public boolean isSmsNotificationEnabled() {
            return smsNotificationEnabled;
        }

        public void setSmsNotificationEnabled(boolean smsNotificationEnabled) {
            this.smsNotificationEnabled = smsNotificationEnabled;
        }

        public boolean isPushNotificationEnabled() {
            return pushNotificationEnabled;
        }

        public void setPushNotificationEnabled(boolean pushNotificationEnabled) {
            this.pushNotificationEnabled = pushNotificationEnabled;
        }

        public boolean isAuditEnabled() {
            return auditEnabled;
        }

        public void setAuditEnabled(boolean auditEnabled) {
            this.auditEnabled = auditEnabled;
        }

        public int getAuditRetentionDays() {
            return auditRetentionDays;
        }

        public void setAuditRetentionDays(int auditRetentionDays) {
            this.auditRetentionDays = auditRetentionDays;
        }

        public boolean isAuditEncryptionEnabled() {
            return auditEncryptionEnabled;
        }

        public void setAuditEncryptionEnabled(boolean auditEncryptionEnabled) {
            this.auditEncryptionEnabled = auditEncryptionEnabled;
        }

        public boolean isReportingEnabled() {
            return reportingEnabled;
        }

        public void setReportingEnabled(boolean reportingEnabled) {
            this.reportingEnabled = reportingEnabled;
        }

        public String getDefaultReportFormat() {
            return defaultReportFormat;
        }

        public void setDefaultReportFormat(String defaultReportFormat) {
            this.defaultReportFormat = defaultReportFormat;
        }

        public int getReportRetentionDays() {
            return reportRetentionDays;
        }

        public void setReportRetentionDays(int reportRetentionDays) {
            this.reportRetentionDays = reportRetentionDays;
        }

        public boolean isMaintenanceEnabled() {
            return maintenanceEnabled;
        }

        public void setMaintenanceEnabled(boolean maintenanceEnabled) {
            this.maintenanceEnabled = maintenanceEnabled;
        }

        public String getMaintenanceSchedule() {
            return maintenanceSchedule;
        }

        public void setMaintenanceSchedule(String maintenanceSchedule) {
            this.maintenanceSchedule = maintenanceSchedule;
        }

        public int getMaintenanceDurationMinutes() {
            return maintenanceDurationMinutes;
        }

        public void setMaintenanceDurationMinutes(int maintenanceDurationMinutes) {
            this.maintenanceDurationMinutes = maintenanceDurationMinutes;
        }
    }
}