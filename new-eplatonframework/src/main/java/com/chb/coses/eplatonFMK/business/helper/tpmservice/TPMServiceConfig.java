package com.chb.coses.eplatonFMK.business.helper.tpmservice;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TPM Service Configuration
 * Spring 기반으로 전환된 TPM 서비스 설정 클래스
 * 
 * @author unascribed
 * @version 1.0
 */
@Configuration
@EnableScheduling
public class TPMServiceConfig {

    private static final Logger logger = LoggerFactory.getLogger(TPMServiceConfig.class);

    @Value("${tpm.service.timeout:30}")
    private int timeout;

    @Value("${tpm.service.maxConnections:100}")
    private int maxConnections;

    @Value("${tpm.service.retryCount:3}")
    private int retryCount;

    @Value("${tpm.service.logLevel:INFO}")
    private String logLevel;

    @Value("${tpm.service.host:localhost}")
    private String host;

    @Value("${tpm.service.port:8080}")
    private int port;

    @Value("${tpm.service.cache.enabled:true}")
    private boolean cacheEnabled;

    @Value("${tpm.service.cache.ttl:300000}")
    private long cacheTtl;

    @Value("${tpm.service.cache.maxSize:10000}")
    private int cacheMaxSize;

    @Value("${tpm.service.security.enabled:true}")
    private boolean securityEnabled;

    @Value("${tpm.service.security.sessionTimeout:1800000}")
    private long sessionTimeout;

    @Value("${tpm.service.security.maxFailedAttempts:5}")
    private int maxFailedAttempts;

    @Value("${tpm.service.monitoring.enabled:true}")
    private boolean monitoringEnabled;

    @Value("${tpm.service.monitoring.interval:300000}")
    private long monitoringInterval;

    @Value("${tpm.service.audit.enabled:true}")
    private boolean auditEnabled;

    @Value("${tpm.service.audit.maxLogs:10000}")
    private int auditMaxLogs;

    /**
     * TPM 서비스 Bean 생성
     */
    @Bean
    public TPMService tpmService() {
        logger.info("TPM 서비스 Bean 생성");
        return new TPMService();
    }

    /**
     * TPM 서비스 매니저 Bean 생성
     */
    @Bean
    public TPMServiceManager tpmServiceManager() {
        logger.info("TPM 서비스 매니저 Bean 생성");
        return new TPMServiceManager();
    }

    /**
     * TPM 서비스 모니터 Bean 생성
     */
    @Bean
    public TPMServiceMonitor tpmServiceMonitor() {
        logger.info("TPM 서비스 모니터 Bean 생성");
        return new TPMServiceMonitor();
    }

    /**
     * TPM 서비스 헬스 Bean 생성
     */
    @Bean
    public TPMServiceHealth tpmServiceHealth() {
        logger.info("TPM 서비스 헬스 Bean 생성");
        return new TPMServiceHealth();
    }

    /**
     * TPM 서비스 메트릭 Bean 생성
     */
    @Bean
    public TPMServiceMetrics tpmServiceMetrics() {
        logger.info("TPM 서비스 메트릭 Bean 생성");
        return new TPMServiceMetrics();
    }

    /**
     * TPM 서비스 감사 Bean 생성
     */
    @Bean
    public TPMServiceAudit tpmServiceAudit() {
        logger.info("TPM 서비스 감사 Bean 생성");
        return new TPMServiceAudit();
    }

    /**
     * TPM 서비스 보안 Bean 생성
     */
    @Bean
    public TPMServiceSecurity tpmServiceSecurity() {
        logger.info("TPM 서비스 보안 Bean 생성");
        return new TPMServiceSecurity();
    }

    /**
     * TPM 서비스 캐시 Bean 생성
     */
    @Bean
    public TPMServiceCache tpmServiceCache() {
        logger.info("TPM 서비스 캐시 Bean 생성");
        return new TPMServiceCache();
    }

    /**
     * TPM 서비스 설정 정보 Bean 생성
     */
    @Bean
    public TPMServiceProperties tpmServiceProperties() {
        TPMServiceProperties properties = new TPMServiceProperties();
        properties.setTimeout(timeout);
        properties.setMaxConnections(maxConnections);
        properties.setRetryCount(retryCount);
        properties.setLogLevel(logLevel);
        properties.setHost(host);
        properties.setPort(port);
        properties.setCacheEnabled(cacheEnabled);
        properties.setCacheTtl(cacheTtl);
        properties.setCacheMaxSize(cacheMaxSize);
        properties.setSecurityEnabled(securityEnabled);
        properties.setSessionTimeout(sessionTimeout);
        properties.setMaxFailedAttempts(maxFailedAttempts);
        properties.setMonitoringEnabled(monitoringEnabled);
        properties.setMonitoringInterval(monitoringInterval);
        properties.setAuditEnabled(auditEnabled);
        properties.setAuditMaxLogs(auditMaxLogs);

        logger.info("TPM 서비스 설정 정보 Bean 생성: {}", properties);
        return properties;
    }

    /**
     * TPM 서비스 설정 정보 클래스
     */
    public static class TPMServiceProperties {
        private int timeout;
        private int maxConnections;
        private int retryCount;
        private String logLevel;
        private String host;
        private int port;
        private boolean cacheEnabled;
        private long cacheTtl;
        private int cacheMaxSize;
        private boolean securityEnabled;
        private long sessionTimeout;
        private int maxFailedAttempts;
        private boolean monitoringEnabled;
        private long monitoringInterval;
        private boolean auditEnabled;
        private int auditMaxLogs;

        // Getters and Setters
        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public int getMaxConnections() {
            return maxConnections;
        }

        public void setMaxConnections(int maxConnections) {
            this.maxConnections = maxConnections;
        }

        public int getRetryCount() {
            return retryCount;
        }

        public void setRetryCount(int retryCount) {
            this.retryCount = retryCount;
        }

        public String getLogLevel() {
            return logLevel;
        }

        public void setLogLevel(String logLevel) {
            this.logLevel = logLevel;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public boolean isCacheEnabled() {
            return cacheEnabled;
        }

        public void setCacheEnabled(boolean cacheEnabled) {
            this.cacheEnabled = cacheEnabled;
        }

        public long getCacheTtl() {
            return cacheTtl;
        }

        public void setCacheTtl(long cacheTtl) {
            this.cacheTtl = cacheTtl;
        }

        public int getCacheMaxSize() {
            return cacheMaxSize;
        }

        public void setCacheMaxSize(int cacheMaxSize) {
            this.cacheMaxSize = cacheMaxSize;
        }

        public boolean isSecurityEnabled() {
            return securityEnabled;
        }

        public void setSecurityEnabled(boolean securityEnabled) {
            this.securityEnabled = securityEnabled;
        }

        public long getSessionTimeout() {
            return sessionTimeout;
        }

        public void setSessionTimeout(long sessionTimeout) {
            this.sessionTimeout = sessionTimeout;
        }

        public int getMaxFailedAttempts() {
            return maxFailedAttempts;
        }

        public void setMaxFailedAttempts(int maxFailedAttempts) {
            this.maxFailedAttempts = maxFailedAttempts;
        }

        public boolean isMonitoringEnabled() {
            return monitoringEnabled;
        }

        public void setMonitoringEnabled(boolean monitoringEnabled) {
            this.monitoringEnabled = monitoringEnabled;
        }

        public long getMonitoringInterval() {
            return monitoringInterval;
        }

        public void setMonitoringInterval(long monitoringInterval) {
            this.monitoringInterval = monitoringInterval;
        }

        public boolean isAuditEnabled() {
            return auditEnabled;
        }

        public void setAuditEnabled(boolean auditEnabled) {
            this.auditEnabled = auditEnabled;
        }

        public int getAuditMaxLogs() {
            return auditMaxLogs;
        }

        public void setAuditMaxLogs(int auditMaxLogs) {
            this.auditMaxLogs = auditMaxLogs;
        }

        @Override
        public String toString() {
            return "TPMServiceProperties{" +
                    "timeout=" + timeout +
                    ", maxConnections=" + maxConnections +
                    ", retryCount=" + retryCount +
                    ", logLevel='" + logLevel + '\'' +
                    ", host='" + host + '\'' +
                    ", port=" + port +
                    ", cacheEnabled=" + cacheEnabled +
                    ", cacheTtl=" + cacheTtl +
                    ", cacheMaxSize=" + cacheMaxSize +
                    ", securityEnabled=" + securityEnabled +
                    ", sessionTimeout=" + sessionTimeout +
                    ", maxFailedAttempts=" + maxFailedAttempts +
                    ", monitoringEnabled=" + monitoringEnabled +
                    ", monitoringInterval=" + monitoringInterval +
                    ", auditEnabled=" + auditEnabled +
                    ", auditMaxLogs=" + auditMaxLogs +
                    '}';
        }
    }
}