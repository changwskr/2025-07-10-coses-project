package com.chb.coses.foundation.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration helper class for managing application configuration
 */
public class ConfigHelper {

    private static Properties properties;
    private static final String DEFAULT_CONFIG_FILE = "application.properties";

    static {
        loadProperties();
    }

    /**
     * Load properties from default configuration file
     */
    private static void loadProperties() {
        properties = new Properties();
        try (InputStream input = ConfigHelper.class.getClassLoader().getResourceAsStream(DEFAULT_CONFIG_FILE)) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            System.err.println("Error loading configuration: " + e.getMessage());
        }
    }

    /**
     * Load properties from specified file
     * 
     * @param configFile configuration file path
     */
    public static void loadProperties(String configFile) {
        properties = new Properties();
        try (FileInputStream input = new FileInputStream(configFile)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Error loading configuration from " + configFile + ": " + e.getMessage());
        }
    }

    /**
     * Get property value
     * 
     * @param key property key
     * @return property value
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get property value with default
     * 
     * @param key          property key
     * @param defaultValue default value
     * @return property value or default
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Get integer property
     * 
     * @param key          property key
     * @param defaultValue default value
     * @return integer property value
     */
    public static int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
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
    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = getProperty(key);
        if (value != null) {
            return Boolean.parseBoolean(value);
        }
        return defaultValue;
    }

    /**
     * Set property
     * 
     * @param key   property key
     * @param value property value
     */
    public static void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    /**
     * Check if property exists
     * 
     * @param key property key
     * @return true if exists, false otherwise
     */
    public static boolean hasProperty(String key) {
        return properties.containsKey(key);
    }

    /**
     * Get all properties
     * 
     * @return properties object
     */
    public static Properties getProperties() {
        return new Properties(properties);
    }

    private ConfigHelper() {
        // Utility class - prevent instantiation
    }
}