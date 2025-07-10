package com.banking.neweplatonframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * New EPlaton Framework Spring Boot Application
 * 
 * Main application class for the New EPlaton Framework.
 * This replaces the legacy EPlaton Framework with Spring Boot features.
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.banking.neweplatonframework",
        "com.banking.coses",
        "com.banking.eplaton"
})
@EnableTransactionManagement
public class NewEPlatonFrameworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewEPlatonFrameworkApplication.class, args);
    }
}