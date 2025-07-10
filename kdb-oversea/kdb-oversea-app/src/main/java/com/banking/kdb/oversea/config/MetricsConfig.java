package com.banking.kdb.oversea.config;

import com.banking.foundation.log.FoundationLogger;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Metrics Configuration for KDB Oversea
 * 
 * Provides Micrometer metrics configuration including
 * custom metrics, timers, and monitoring setup.
 */
@Configuration
public class MetricsConfig {

    private static final FoundationLogger logger = FoundationLogger.getLogger(MetricsConfig.class);

    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        logger.info("Configuring TimedAspect for method timing");
        return new TimedAspect(registry);
    }

    @Bean
    public Timer cashCardCreationTimer(MeterRegistry registry) {
        logger.info("Creating cash card creation timer metric");
        return Timer.builder("kdb.oversea.cashcard.creation.time")
                .description("Time taken to create cash cards")
                .tag("service", "cashcard")
                .tag("operation", "creation")
                .register(registry);
    }

    @Bean
    public Timer transactionProcessingTimer(MeterRegistry registry) {
        logger.info("Creating transaction processing timer metric");
        return Timer.builder("kdb.oversea.transaction.processing.time")
                .description("Time taken to process transactions")
                .tag("service", "transaction")
                .tag("operation", "processing")
                .register(registry);
    }

    @Bean
    public Timer customerLookupTimer(MeterRegistry registry) {
        logger.info("Creating customer lookup timer metric");
        return Timer.builder("kdb.oversea.customer.lookup.time")
                .description("Time taken to lookup customer information")
                .tag("service", "customer")
                .tag("operation", "lookup")
                .register(registry);
    }

    @Bean
    public Timer depositOperationTimer(MeterRegistry registry) {
        logger.info("Creating deposit operation timer metric");
        return Timer.builder("kdb.oversea.deposit.operation.time")
                .description("Time taken for deposit operations")
                .tag("service", "deposit")
                .tag("operation", "processing")
                .register(registry);
    }

    @Bean
    public Timer tellerOperationTimer(MeterRegistry registry) {
        logger.info("Creating teller operation timer metric");
        return Timer.builder("kdb.oversea.teller.operation.time")
                .description("Time taken for teller operations")
                .tag("service", "teller")
                .tag("operation", "processing")
                .register(registry);
    }
}