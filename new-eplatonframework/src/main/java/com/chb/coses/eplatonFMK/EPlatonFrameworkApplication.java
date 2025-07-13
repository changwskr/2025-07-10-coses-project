package com.chb.coses.eplatonFMK;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * EPlaton Framework Spring Boot 애플리케이션 메인 클래스
 * 
 * @author Spring Boot Migration
 * @version 1.0
 */
@SpringBootApplication
@EntityScan("com.chb.coses.eplatonFMK")
@EnableJpaRepositories("com.chb.coses.eplatonFMK")
@EnableTransactionManagement
@EnableAsync
public class EPlatonFrameworkApplication {

    /**
     * 애플리케이션 메인 메소드
     * 
     * @param args 명령행 인수
     */
    public static void main(String[] args) {
        SpringApplication.run(EPlatonFrameworkApplication.class, args);
    }
}