package com.banking.coses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * COSES Framework Spring Boot Application
 * 
 * Main application class for the COSES Framework.
 * This replaces the legacy COSES Framework with Spring Boot features.
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.banking.coses",
        "com.banking.eplaton"
})
@EnableTransactionManagement
public class CosesFrameworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(CosesFrameworkApplication.class, args);
    }
}