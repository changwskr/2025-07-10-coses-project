package com.banking.eplaton.tcf.config;

import com.banking.eplaton.tcf.TCF;
import com.banking.eplaton.tcf.STF;
import com.banking.eplaton.tcf.BTF;
import com.banking.eplaton.tcf.ETF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * TCF (Transaction Control Framework) Configuration
 * 
 * Provides configuration for TCF components and their dependencies.
 */
@Configuration
@ComponentScan(basePackages = "com.banking.eplaton.tcf")
@EnableTransactionManagement
public class TCFConfig {

    private static final Logger logger = LoggerFactory.getLogger(TCFConfig.class);

    /**
     * Configure TCF bean
     */
    @Bean
    public TCF tcf() {
        logger.info("Creating TCF bean");
        return new TCF();
    }

    /**
     * Configure STF bean
     */
    @Bean
    public STF stf() {
        logger.info("Creating STF bean");
        return new STF();
    }

    /**
     * Configure BTF bean
     */
    @Bean
    public BTF btf() {
        logger.info("Creating BTF bean");
        return new BTF();
    }

    /**
     * Configure ETF bean
     */
    @Bean
    public ETF etf() {
        logger.info("Creating ETF bean");
        return new ETF();
    }
}