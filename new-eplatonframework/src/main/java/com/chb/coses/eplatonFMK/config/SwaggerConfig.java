package com.chb.coses.eplatonFMK.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger 설정 클래스
 * 
 * @author Spring Boot Migration
 * @version 1.0
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI eplatonFrameworkOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EPlaton Framework API")
                        .description("EPlaton Framework 외부 연계 API 문서")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("EPlaton Development Team")
                                .email("dev@chb.co.kr")
                                .url("https://www.chb.co.kr"))
                        .license(new License()
                                .name("Internal Use Only")
                                .url("https://www.chb.co.kr/license")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("개발 서버"),
                        new Server()
                                .url("https://api.chb.co.kr")
                                .description("운영 서버")));
    }
}