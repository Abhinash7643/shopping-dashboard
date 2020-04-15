package com.abhinash.shoppingdashboard.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customCofiguration() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("My API Docs")
                        .description("REST API documentation"));
    }
}
