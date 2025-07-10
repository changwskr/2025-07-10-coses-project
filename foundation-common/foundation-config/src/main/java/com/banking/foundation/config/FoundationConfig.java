package com.banking.foundation.config;

import com.banking.foundation.log.FoundationLogger;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Foundation Configuration Manager
 * 
 * Provides comprehensive configuration management with support for
 * multiple configuration sources, environment-specific configs, and
 * dynamic configuration updates.
 * This replaces the legacy Config class with Spring Boot features.
 */
@Component
@Configuration
public class FoundationConfig {

    private static final FoundationLogger logger = new FoundationLogger(FoundationConfig.class);

    private static final String DEFAULT_CONFIG_FILE = "application.properties";
    private static final String ENV_CONFIG_FILE = "application-{}.properties";

    private Properties properties;
    private Map<String, Object> dynamicConfig;
    private final Environment environment;
    private final ObjectMapper objectMapper;

    @Autowired
    public FoundationConfig(Environment environment) {
        this.environment = environment;
        this.objectMapper = new ObjectMapper();
        this.dynamicConfig = new HashMap<>();
        loadProperties();
    }

    /**
     * Load properties from default configuration file
     */
    private void loadProperties() {
        properties = new Properties();

        try {
            // Load default properties
            loadFromClasspath(DEFAULT_CONFIG_FILE);

            // Load environment-specific properties
            String activeProfile = getActiveProfile();
            if (activeProfile != null && !activeProfile.isEmpty()) {
                String envConfigFile = ENV_CONFIG_FILE.replace("{}", activeProfile);
                loadFromClasspath(envConfigFile);
            }

            logger.info("Configuration loaded successfully");

        } catch (Exception e) {
            logger.error("Error loading configuration", e);
        }
    }

    /**
     * Load properties from classpath
     */
    private void loadFromClasspath(String configFile) throws IOException {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(configFile)) {
            if (input != null) {
                Properties newProps = new Properties();
                newProps.load(input);
                properties.putAll(newProps);
                logger.info("Loaded configuration from: {}", configFile);
            }
        }
    }

    /**
     * Load properties from specified file
     * 
     * @param configFile configuration file path
     */
    public void loadProperties(String configFile) {
        try (FileInputStream input = new FileInputStream(configFile)) {
            Properties newProps = new Properties();
            newProps.load(input);
            properties.putAll(newProps);
            logger.info("Loaded configuration from file: {}", configFile);
        } catch (IOException e) {
            logger.error("Error loading configuration from {}", configFile, e);
        }
    }

    /**
     * Get property value
     * 
     * @param key property key
     * @return property value
     */
    public String getProperty(String key) {
        // Check dynamic config first
        Object dynamicValue = dynamicConfig.get(key);
        if (dynamicValue != null) {
            return dynamicValue.toString();
        }

        // Check Spring environment
        String envValue = environment.getProperty(key);
        if (envValue != null) {
            return envValue;
        }

        // Check properties file
        return properties.getProperty(key);
    }

    /**
     * Get property value with default
     * 
     * @param key          property key
     * @param defaultValue default value
     * @return property value or default
     */
    public String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        return value != null ? value : defaultValue;
    }

    /**
     * Get integer property
     * 
     * @param key          property key
     * @param defaultValue default value
     * @return integer property value
     */
    public int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                logger.warn("Invalid integer value for key: {}", key);
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * Get long property
     * 
     * @param key          property key
     * @param defaultValue default value
     * @return long property value
     */
    public long getLongProperty(String key, long defaultValue) {
        String value = getProperty(key);
        if (value != null) {
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                logger.warn("Invalid long value for key: {}", key);
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * Get boolean property
     * 
     * @param key          property key
     * @param defaultValue default value
     * @return boolean property value
     */
    public boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = getProperty(key);
        if (value != null) {
            return Boolean.parseBoolean(value);
        }
        return defaultValue;
    }

    /**
     * Get double property
     * 
     * @param key          property key
     * @param defaultValue default value
     * @return double property value
     */
    public double getDoubleProperty(String key, double defaultValue) {
        String value = getProperty(key);
        if (value != null) {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                logger.warn("Invalid double value for key: {}", key);
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * Set dynamic property
     * 
     * @param key   property key
     * @param value property value
     */
    public void setDynamicProperty(String key, Object value) {
        dynamicConfig.put(key, value);
        logger.info("Set dynamic property: {} = {}", key, value);
    }

    /**
     * Remove dynamic property
     * 
     * @param key property key
     */
    public void removeDynamicProperty(String key) {
        dynamicConfig.remove(key);
        logger.info("Removed dynamic property: {}", key);
    }

    /**
     * Check if property exists
     * 
     * @param key property key
     * @return true if exists, false otherwise
     */
    public boolean hasProperty(String key) {
        return dynamicConfig.containsKey(key) ||
                environment.containsProperty(key) ||
                properties.containsKey(key);
    }

    /**
     * Get all properties
     * 
     * @return properties object
     */
    public Properties getProperties() {
        Properties allProps = new Properties();
        allProps.putAll(properties);
        allProps.putAll(dynamicConfig);
        return allProps;
    }

    /**
     * Get active profile
     * 
     * @return active profile
     */
    public String getActiveProfile() {
        String[] activeProfiles = environment.getActiveProfiles();
        return activeProfiles.length > 0 ? activeProfiles[0] : null;
    }

    /**
     * Get all profiles
     * 
     * @return all profiles
     */
    public String[] getAllProfiles() {
        return environment.getActiveProfiles();
    }

    /**
     * Get database configuration
     * 
     * @return database configuration map
     */
    public Map<String, String> getDatabaseConfig() {
        Map<String, String> dbConfig = new HashMap<>();
        dbConfig.put("url", getProperty("spring.datasource.url", "jdbc:h2:mem:testdb"));
        dbConfig.put("username", getProperty("spring.datasource.username", "sa"));
        dbConfig.put("password", getProperty("spring.datasource.password", ""));
        dbConfig.put("driver", getProperty("spring.datasource.driver-class-name", "org.h2.Driver"));
        return dbConfig;
    }

    /**
     * Get server configuration
     * 
     * @return server configuration map
     */
    public Map<String, Object> getServerConfig() {
        Map<String, Object> serverConfig = new HashMap<>();
        serverConfig.put("port", getIntProperty("server.port", 8080));
        serverConfig.put("contextPath", getProperty("server.servlet.context-path", ""));
        serverConfig.put("servletPath", getProperty("server.servlet.path", "/"));
        return serverConfig;
    }

    /**
     * Get logging configuration
     * 
     * @return logging configuration map
     */
    public Map<String, String> getLoggingConfig() {
        Map<String, String> loggingConfig = new HashMap<>();
        loggingConfig.put("level", getProperty("logging.level.root", "INFO"));
        loggingConfig.put("pattern", getProperty("logging.pattern.console", "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"));
        loggingConfig.put("file", getProperty("logging.file.name", ""));
        return loggingConfig;
    }

    /**
     * Reload configuration
     */
    public void reload() {
        loadProperties();
        logger.info("Configuration reloaded");
    }

    /**
     * Get configuration summary
     * 
     * @return configuration summary
     */
    public Map<String, Object> getConfigSummary() {
        Map<String, Object> summary = new HashMap<>();
        summary.put("activeProfile", getActiveProfile());
        summary.put("totalProperties", properties.size());
        summary.put("dynamicProperties", dynamicConfig.size());
        summary.put("databaseConfig", getDatabaseConfig());
        summary.put("serverConfig", getServerConfig());
        summary.put("loggingConfig", getLoggingConfig());
        return summary;
    }

    /**
     * Configuration Properties Bean
     */
    @Bean
    @ConfigurationProperties(prefix = "foundation")
    public FoundationProperties foundationProperties() {
        return new FoundationProperties();
    }

    /**
     * Foundation Properties Class
     */
    public static class FoundationProperties {
        private String version = "1.0.0";
        private String environment = "development";
        private boolean debug = false;
        private int timeout = 30000;

        // Getters and Setters
        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getEnvironment() {
            return environment;
        }

        public void setEnvironment(String environment) {
            this.environment = environment;
        }

        public boolean isDebug() {
            return debug;
        }

        public void setDebug(boolean debug) {
            this.debug = debug;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }
    }
}