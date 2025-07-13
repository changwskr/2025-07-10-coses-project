package com.skcc.oversea.foundation.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Configuration class for SKCC Oversea Foundation
 * 
 * Provides configuration management for the SKCC Oversea system
 * using Spring Boot configuration properties and external files.
 */
@Configuration
public class SkccConfig {

    private static final Logger logger = LoggerFactory.getLogger(SkccConfig.class);

    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${skcc.oversea.config.file:classpath:config/skcc-oversea.properties}")
    private String configFile;

    @Value("${skcc.oversea.config.xml.file:classpath:config/skcc-oversea.xml}")
    private String xmlConfigFile;

    @Value("${skcc.oversea.machine.mode:DEV}")
    private String machineMode;

    @Value("${skcc.oversea.environment:development}")
    private String environment;

    private final Map<String, Object> configCache = new HashMap<>();
    private Properties properties;

    /**
     * Initialize configuration
     */
    @Bean
    public SkccConfig skccConfig() {
        loadProperties();
        logger.info("SKCC Oversea Configuration initialized - Mode: {}, Environment: {}",
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
    public String getConfigValue(String key) {
        return getConfigValue(key, null);
    }

    /**
     * Get configuration value by key with default value
     */
    public String getConfigValue(String key, String defaultValue) {
        // First check cache
        if (configCache.containsKey(key)) {
            return (String) configCache.get(key);
        }

        // Then check properties file
        String value = properties.getProperty(key, defaultValue);

        // Cache the result
        configCache.put(key, value);

        return value;
    }

    /**
     * Get machine mode
     */
    public String getMachineMode() {
        return machineMode;
    }

    /**
     * Get environment
     */
    public String getEnvironment() {
        return environment;
    }

    /**
     * Check if running in development mode
     */
    public boolean isDevelopment() {
        return "DEV".equalsIgnoreCase(machineMode) || "development".equalsIgnoreCase(environment);
    }

    /**
     * Check if running in production mode
     */
    public boolean isProduction() {
        return "PROD".equalsIgnoreCase(machineMode) || "production".equalsIgnoreCase(environment);
    }

    /**
     * Get all properties
     */
    public Properties getProperties() {
        return new Properties(properties);
    }
}