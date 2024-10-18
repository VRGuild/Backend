package com.mtvs.devlinkbackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        // Define the security scheme for the authorization header
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        // Define the security requirement for Swagger UI
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("Bearer Authentication");

        // Configure the OpenAPI settings
        return new OpenAPI()
                .info(new Info()
                        .title("DevLink API Documentation")
                        .version("1.0.0")
                        .description("API documentation for DevLink Backend"))
                .addSecurityItem(securityRequirement) // Apply the security requirement globally
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("Bearer Authentication", securityScheme));
    }
}
