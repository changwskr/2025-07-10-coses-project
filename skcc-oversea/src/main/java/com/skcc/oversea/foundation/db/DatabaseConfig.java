package com.skcc.oversea.foundation.db;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.skcc.oversea")
@EnableTransactionManagement
public class DatabaseConfig {
    // Database configuration will be handled by application.yml
}