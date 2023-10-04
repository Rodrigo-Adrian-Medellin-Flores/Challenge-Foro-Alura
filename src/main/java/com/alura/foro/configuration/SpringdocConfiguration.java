package com.alura.foro.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringdocConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
    return new OpenAPI()
            .components(new Components()
            .addSecuritySchemes("bearer-key",
            new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
            .info(new Info().title("API REST Foro Alura")
            .description("Challenge Foro Alura  -  Elaborado por:  Rodrigo Adrian Medelín Flores ONE - Alura Latam G5     v1.0 2023"));
    }
    
}
