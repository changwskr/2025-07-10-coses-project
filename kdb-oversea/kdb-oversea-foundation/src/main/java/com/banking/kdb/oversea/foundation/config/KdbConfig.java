package com.banking.kdb.oversea.foundation.config;

import com.banking.foundation.config.FoundationConfig;
import com.banking.foundation.log.FoundationLogger;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Configuration class for KDB Oversea Foundation
 * 
 * Provides configuration management for the KDB Oversea system
 * using Spring Boot configuration properties and external files.
 */
@Configuration
@Import(FoundationConfig.class)
public class KdbConfig {

    private static final FoundationLogger logger = FoundationLogger.getLogger(KdbConfig.class);

    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${kdb.oversea.config.file:classpath:config/kdb-oversea.properties}")
    private String configFile;

    @Value("${kdb.oversea.config.xml.file:classpath:config/kdb-oversea.xml}")
    private String xmlConfigFile;

    @Value("${kdb.oversea.machine.mode:DEV}")
    private String machineMode;

    @Value("${kdb.oversea.environment:development}")
    private String environment;

    private final Map<String, Object> configCache = new HashMap<>();
    private Properties properties;

    /**
     * Initialize configuration
     */
    @Bean
    public KdbConfig kdbConfig() {
        loadProperties();
        logger.info("KDB Oversea Configuration initialized - Mode: {}, Environment: {}",
                machineMode, environment);
        return this;
    }

    /**
     * Load properties from configuration file
     */
    private void loadProperties() {
        try {
            Resource resource = resourceLoader.getResource(configFile);
            if (resource.exists()) {
                properties = new Properties();
                try (InputStream inputStream = resource.getInputStream()) {
                    properties.load(inputStream);
                }
                logger.info("Configuration loaded from: {}", configFile);
            } else {
                logger.warn("Configuration file not found: {}", configFile);
                properties = new Properties();
            }
        } catch (IOException e) {
            logger.error("Failed to load configuration: {}", e.getMessage());
            properties = new Properties();
        }
    }

    /**
     * Get configuration value by key
     */
    public String getValue(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get configuration value by key with default
     */
    public String getValue(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Get configuration value by service and element
     */
    public String getServiceValue(String serviceName, String elementName) {
        String key = serviceName + "." + elementName;
        return getValue(key);
    }

    /**
     * Get configuration value by service and element with default
     */
    public String getServiceValue(String serviceName, String elementName, String defaultValue) {
        String value = getServiceValue(serviceName, elementName);
        return value != null ? value : defaultValue;
    }

    /**
     * Get machine mode
     */
    public String getMachineMode() {
        return machineMode;
    }

    /**
     * Check if running in development mode
     */
    public boolean isDevelopmentMode() {
        return "DEV".equalsIgnoreCase(machineMode);
    }

    /**
     * Check if running in production mode
     */
    public boolean isProductionMode() {
        return "PROD".equalsIgnoreCase(machineMode);
    }

    /**
     * Get environment
     */
    public String getEnvironment() {
        return environment;
    }

    /**
     * Get cached configuration value
     */
    public Object getCachedValue(String key) {
        return configCache.get(key);
    }

    /**
     * Set cached configuration value
     */
    public void setCachedValue(String key, Object value) {
        configCache.put(key, value);
    }

    /**
     * Clear configuration cache
     */
    public void clearCache() {
        configCache.clear();
    }

    /**
     * Get all properties
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * Get configuration as map
     */
    public Map<String, String> getConfigAsMap() {
        Map<String, String> configMap = new HashMap<>();
        for (String key : properties.stringPropertyNames()) {
            configMap.put(key, properties.getProperty(key));
        }
        return configMap;
    }

    /**
     * Reload configuration
     */
    public void reload() {
        loadProperties();
        clearCache();
        logger.info("Configuration reloaded");
    }

    /**
     * Get ObjectMapper for JSON processing
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}