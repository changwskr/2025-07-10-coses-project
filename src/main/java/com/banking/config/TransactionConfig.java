package com.banking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Properties;

/**
 * Transaction Configuration for Banking System
 * 
 * Provides transaction management configuration including transaction
 * interceptors and timeout settings.
 */
@Configuration
@EnableTransactionManagement
public class TransactionConfig {

    @Bean
    public TransactionInterceptor transactionInterceptor() {
        Properties properties = new Properties();

        // Read-only transactions
        properties.setProperty("get*", "PROPAGATION_REQUIRED,ISOLATION_READ_COMMITTED,readOnly");
        properties.setProperty("find*", "PROPAGATION_REQUIRED,ISOLATION_READ_COMMITTED,readOnly");
        properties.setProperty("list*", "PROPAGATION_REQUIRED,ISOLATION_READ_COMMITTED,readOnly");
        properties.setProperty("search*", "PROPAGATION_REQUIRED,ISOLATION_READ_COMMITTED,readOnly");

        // Write transactions
        properties.setProperty("save*", "PROPAGATION_REQUIRED,ISOLATION_READ_COMMITTED,-Exception");
        properties.setProperty("update*", "PROPAGATION_REQUIRED,ISOLATION_READ_COMMITTED,-Exception");
        properties.setProperty("delete*", "PROPAGATION_REQUIRED,ISOLATION_READ_COMMITTED,-Exception");
        properties.setProperty("process*", "PROPAGATION_REQUIRED,ISOLATION_READ_COMMITTED,-Exception");
        properties.setProperty("execute*", "PROPAGATION_REQUIRED,ISOLATION_READ_COMMITTED,-Exception");

        // Default transaction
        properties.setProperty("*", "PROPAGATION_REQUIRED,ISOLATION_READ_COMMITTED");

        return new TransactionInterceptor();
    }
}