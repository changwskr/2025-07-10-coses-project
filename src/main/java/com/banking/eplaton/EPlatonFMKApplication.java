package com.banking.eplaton;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * EPlatonFMK Spring Boot Application
 * 
 * Main application class for EPlatonFMK framework migration.
 * This replaces the legacy EJB-based EPlatonFMK system.
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.banking.eplaton",
        "com.banking.framework",
        "com.banking.service",
        "com.banking.controller"
})
@EnableTransactionManagement
public class EPlatonFMKApplication {

    public static void main(String[] args) {
        SpringApplication.run(EPlatonFMKApplication.class, args);
    }
}