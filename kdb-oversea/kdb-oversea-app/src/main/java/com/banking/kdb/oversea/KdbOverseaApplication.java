package com.banking.kdb.oversea;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.foundation.config.KdbConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Main application class for KDB Oversea Banking System
 * 
 * Spring Boot application that provides overseas banking services
 * including cash card, deposit, and teller operations.
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.banking.kdb.oversea",
        "com.banking.foundation",
        "com.banking.coses.framework"
})
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
@EnableConfigurationProperties
public class KdbOverseaApplication {

    private static final FoundationLogger logger = FoundationLogger.getLogger(KdbOverseaApplication.class);

    public static void main(String[] args) {
        try {
            logger.info("Starting KDB Oversea Banking System...");

            SpringApplication app = new SpringApplication(KdbOverseaApplication.class);
            app.run(args);

            logger.info("KDB Oversea Banking System started successfully");

        } catch (Exception e) {
            logger.error("Failed to start KDB Oversea Banking System: {}", e.getMessage(), e);
            System.exit(1);
        }
    }
}