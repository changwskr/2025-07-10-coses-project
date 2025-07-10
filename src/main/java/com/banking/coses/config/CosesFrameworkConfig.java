package com.banking.coses.config;

import com.banking.coses.action.ActionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * COSES Framework Configuration
 * 
 * Provides configuration for COSES Framework components and their dependencies.
 */
@Configuration
@ComponentScan(basePackages = "com.banking.coses")
@EnableTransactionManagement
public class CosesFrameworkConfig {

    private static final Logger logger = LoggerFactory.getLogger(CosesFrameworkConfig.class);

    /**
     * Configure ActionManager bean
     */
    @Bean
    public ActionManager actionManager() {
        logger.info("Creating ActionManager bean");
        return new ActionManager();
    }
}