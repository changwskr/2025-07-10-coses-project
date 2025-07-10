package com.banking.framework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * CosesFramework Spring Boot Application
 * 
 * This is the main entry point for the CosesFramework migrated to Spring Boot.
 * The framework provides business action management, event handling, and common
 * utilities.
 * 
 * @author Banking System Team
 * @version 2.0.0
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.banking.framework",
        "com.banking.framework.action",
        "com.banking.framework.business",
        "com.banking.framework.exception",
        "com.banking.framework.transfer",
        "com.banking.framework.util"
})
public class CosesFrameworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(CosesFrameworkApplication.class, args);
    }
}