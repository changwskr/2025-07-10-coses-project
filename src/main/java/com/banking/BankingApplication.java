package com.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Spring Boot Banking Application
 * 
 * This is the main entry point for the banking system migrated from EJB to
 * Spring Boot.
 * The application provides banking services including cash card management,
 * deposit management, and teller operations.
 * 
 * @author Banking System Team
 * @version 1.0.0
 */
@SpringBootApplication
@EntityScan(basePackages = {
        "com.banking.model.entity",
        "com.kdb.oversea.cashCard.business.cashCard.model",
        "com.ims.oversea.eplatonframework.transfer"
})
@EnableJpaRepositories(basePackages = {
        "com.banking.repository",
        "com.kdb.oversea.framework.transaction.dao"
})
public class BankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingApplication.class, args);
    }
}