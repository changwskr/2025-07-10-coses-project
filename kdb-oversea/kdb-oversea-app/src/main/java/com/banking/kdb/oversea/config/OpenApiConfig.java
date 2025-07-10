package com.banking.kdb.oversea.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI Configuration for KDB Oversea
 * 
 * Provides Swagger/OpenAPI documentation configuration
 * including API info, security schemes, and servers.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("KDB Oversea Banking API")
                        .description(
                                """
                                        KDB Oversea Banking System REST API

                                        This API provides comprehensive banking services including:
                                        - Customer management
                                        - Cash card operations
                                        - Deposit account management
                                        - Transaction processing
                                        - Teller operations

                                        ## Authentication
                                        The API uses JWT-based authentication. Include the JWT token in the Authorization header:
                                        ```
                                        Authorization: Bearer <your-jwt-token>
                                        ```

                                        ## Roles
                                        - **ADMIN**: Full system access
                                        - **TELLER**: Banking operations and customer service
                                        - **CUSTOMER**: Personal account access

                                        ## Rate Limiting
                                        API calls are rate-limited to ensure system stability.

                                        ## Error Handling
                                        All errors return a consistent format with error codes and messages.
                                        """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("KDB Development Team")
                                .email("dev@kdb.co.kr")
                                .url("https://www.kdb.co.kr"))
                        .license(new License()
                                .name("KDB Internal License")
                                .url("https://www.kdb.co.kr/license")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Development Server"),
                        new Server()
                                .url("https://api-dev.kdb.co.kr")
                                .description("Development Environment"),
                        new Server()
                                .url("https://api.kdb.co.kr")
                                .description("Production Environment")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .name("bearerAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("JWT token for API authentication")));
    }
}