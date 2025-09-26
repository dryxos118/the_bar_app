package com.example.the_bar_app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "TheBarApp API",
                version = "1.0.0",
                description = "Powered by Dryxos118",
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi allApis() {
        return GroupedOpenApi.builder()
                .group("All")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public GroupedOpenApi authApis(){
        return GroupedOpenApi.builder()
                .group("Auth")
                .pathsToMatch("/auth/**")
                .build();
    }

    @Bean
    public GroupedOpenApi usersApis() {
        return GroupedOpenApi.builder()
                .group("Users")
                .pathsToMatch("/user/**")
                .build();
    }

    @Bean
    public GroupedOpenApi drinksApis() {
        return GroupedOpenApi.builder()
                .group("Drinks")
                .pathsToMatch("/drinks/**")
                .build();
    }
}
