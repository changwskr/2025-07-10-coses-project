package com.banking.neweplatonframework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * New EPlaton Framework Configuration
 * 
 * Configuration class for New EPlaton Framework components.
 * This replaces the legacy EJB configuration with Spring Boot configuration.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.banking.neweplatonframework",
        "com.banking.neweplatonframework.business",
        "com.banking.neweplatonframework.business.tcf",
        "com.banking.neweplatonframework.othersystem.operation"
})
@EnableConfigurationProperties
@PropertySource("classpath:application-neweplaton.properties")
public class NewEPlatonFrameworkConfig {

    /**
     * New EPlaton Framework Properties
     */
    @Bean
    @ConfigurationProperties(prefix = "neweplaton.framework")
    public NewEPlatonFrameworkProperties newEPlatonFrameworkProperties() {
        return new NewEPlatonFrameworkProperties();
    }

    /**
     * New EPlaton Business Executor
     * 
     * Thread pool executor for business operations
     */
    @Bean("newEPlatonBusinessExecutor")
    public Executor newEPlatonBusinessExecutor(NewEPlatonFrameworkProperties properties) {
        return new ThreadPoolExecutor(
                properties.getCorePoolSize(),
                properties.getMaxPoolSize(),
                properties.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(properties.getQueueCapacity()),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    /**
     * New EPlaton TCF Executor
     * 
     * Thread pool executor for TCF operations
     */
    @Bean("newEPlatonTCFExecutor")
    public Executor newEPlatonTCFExecutor(NewEPlatonFrameworkProperties properties) {
        return new ThreadPoolExecutor(
                properties.getTcfCorePoolSize(),
                properties.getTcfMaxPoolSize(),
                properties.getTcfKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(properties.getTcfQueueCapacity()),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    /**
     * New EPlaton Framework Properties Class
     */
    public static class NewEPlatonFrameworkProperties {

        private int corePoolSize = 10;
        private int maxPoolSize = 50;
        private int keepAliveTime = 60;
        private int queueCapacity = 1000;

        private int tcfCorePoolSize = 5;
        private int tcfMaxPoolSize = 20;
        private int tcfKeepAliveTime = 60;
        private int tcfQueueCapacity = 500;

        private int timeout = 30000;
        private int maxRetries = 3;
        private boolean enableLogging = true;
        private String logLevel = "INFO";

        // Getters and Setters
        public int getCorePoolSize() {
            return corePoolSize;
        }

        public void setCorePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
        }

        public int getMaxPoolSize() {
            return maxPoolSize;
        }

        public void setMaxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
        }

        public int getKeepAliveTime() {
            return keepAliveTime;
        }

        public void setKeepAliveTime(int keepAliveTime) {
            this.keepAliveTime = keepAliveTime;
        }

        public int getQueueCapacity() {
            return queueCapacity;
        }

        public void setQueueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
        }

        public int getTcfCorePoolSize() {
            return tcfCorePoolSize;
        }

        public void setTcfCorePoolSize(int tcfCorePoolSize) {
            this.tcfCorePoolSize = tcfCorePoolSize;
        }

        public int getTcfMaxPoolSize() {
            return tcfMaxPoolSize;
        }

        public void setTcfMaxPoolSize(int tcfMaxPoolSize) {
            this.tcfMaxPoolSize = tcfMaxPoolSize;
        }

        public int getTcfKeepAliveTime() {
            return tcfKeepAliveTime;
        }

        public void setTcfKeepAliveTime(int tcfKeepAliveTime) {
            this.tcfKeepAliveTime = tcfKeepAliveTime;
        }

        public int getTcfQueueCapacity() {
            return tcfQueueCapacity;
        }

        public void setTcfQueueCapacity(int tcfQueueCapacity) {
            this.tcfQueueCapacity = tcfQueueCapacity;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public int getMaxRetries() {
            return maxRetries;
        }

        public void setMaxRetries(int maxRetries) {
            this.maxRetries = maxRetries;
        }

        public boolean isEnableLogging() {
            return enableLogging;
        }

        public void setEnableLogging(boolean enableLogging) {
            this.enableLogging = enableLogging;
        }

        public String getLogLevel() {
            return logLevel;
        }

        public void setLogLevel(String logLevel) {
            this.logLevel = logLevel;
        }

        @Override
        public String toString() {
            return "NewEPlatonFrameworkProperties{" +
                    "corePoolSize=" + corePoolSize +
                    ", maxPoolSize=" + maxPoolSize +
                    ", keepAliveTime=" + keepAliveTime +
                    ", queueCapacity=" + queueCapacity +
                    ", tcfCorePoolSize=" + tcfCorePoolSize +
                    ", tcfMaxPoolSize=" + tcfMaxPoolSize +
                    ", tcfKeepAliveTime=" + tcfKeepAliveTime +
                    ", tcfQueueCapacity=" + tcfQueueCapacity +
                    ", timeout=" + timeout +
                    ", maxRetries=" + maxRetries +
                    ", enableLogging=" + enableLogging +
                    ", logLevel='" + logLevel + '\'' +
                    '}';
        }
    }
}