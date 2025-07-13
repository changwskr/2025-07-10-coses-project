package com.skcc.oversea.foundation.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for SKCC Oversea Foundation
 * 
 * Provides configuration management for the SKCC Oversea system
 * using Spring Boot configuration properties and external XML files.
 */
@Configuration
public class Config {

  private static final Logger logger = LoggerFactory.getLogger(Config.class);

  @Autowired
  private ResourceLoader resourceLoader;

  @Value("${skcc.oversea.config.xml.file:classpath:config/skcc-oversea.xml}")
  private String configXmlFile;

  private final Map<String, Document> xmlCache = new HashMap<>();
  private Document configDocument;

  /**
   * Initialize configuration
   */
  @Bean
  public Config config() {
    loadXmlConfig();
    logger.info("SKCC Oversea Configuration initialized");
    return this;
  }

  /**
   * Load XML configuration file
   */
  private void loadXmlConfig() {
    try {
      Resource resource = resourceLoader.getResource(configXmlFile);
      if (resource.exists()) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        try (InputStream inputStream = resource.getInputStream()) {
          configDocument = builder.parse(inputStream);
          configDocument.getDocumentElement().normalize();
        }
        logger.info("XML Configuration loaded from: {}", configXmlFile);
      } else {
        logger.warn("XML Configuration file not found: {}", configXmlFile);
        configDocument = null;
      }
    } catch (Exception e) {
      logger.error("Failed to load XML configuration: {}", e.getMessage());
      configDocument = null;
    }
  }

  /**
   * Get configuration value by service name and element name
   */
  public String getValue(String serviceName, String elementName) {
    if (configDocument == null) {
      logger.warn("Configuration document not loaded");
      return null;
    }

    try {
      Element rootElement = configDocument.getDocumentElement();
      NodeList serviceNodes = rootElement.getElementsByTagName(serviceName);

      if (serviceNodes.getLength() > 0) {
        Element serviceElement = (Element) serviceNodes.item(0);
        NodeList elementNodes = serviceElement.getElementsByTagName(elementName);

        if (elementNodes.getLength() > 0) {
          return elementNodes.item(0).getTextContent().trim();
        }
      }

      logger.debug("Configuration value not found: {}.{}", serviceName, elementName);
      return null;
    } catch (Exception e) {
      logger.error("Error getting configuration value: {}.{}", serviceName, elementName, e);
      return null;
    }
  }

  /**
   * Get configuration element by service name
   */
  public Element getElement(String serviceName) {
    if (configDocument == null) {
      logger.warn("Configuration document not loaded");
      return null;
    }

    try {
      Element rootElement = configDocument.getDocumentElement();
      NodeList serviceNodes = rootElement.getElementsByTagName(serviceName);

      if (serviceNodes.getLength() > 0) {
        return (Element) serviceNodes.item(0);
      }

      logger.debug("Configuration element not found: {}", serviceName);
      return null;
    } catch (Exception e) {
      logger.error("Error getting configuration element: {}", serviceName, e);
      return null;
    }
  }

  /**
   * Get configuration document
   */
  public Document getConfigDocument() {
    return configDocument;
  }

  /**
   * Check if configuration is loaded
   */
  public boolean isConfigLoaded() {
    return configDocument != null;
  }
}
