package com.skcc.oversea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Main application class for SKCC Oversea Banking System
 * 
 * Spring Boot application that provides overseas banking services
 * including cash card, deposit, and teller operations.
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.skcc.oversea"
})
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
@EnableConfigurationProperties
public class SkccOverseaApplication {

    private static final Logger logger = LoggerFactory.getLogger(SkccOverseaApplication.class);

    public static void main(String[] args) {
        try {
            logger.info("Starting SKCC Oversea Banking System...");

            SpringApplication app = new SpringApplication(SkccOverseaApplication.class);
            app.run(args);

            logger.info("SKCC Oversea Banking System started successfully");

        } catch (Exception e) {
            logger.error("Failed to start SKCC Oversea Banking System: {}", e.getMessage(), e);
            System.exit(1);
        }
    }
}