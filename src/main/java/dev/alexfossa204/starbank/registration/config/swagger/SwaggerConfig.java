package dev.alexfossa204.starbank.registration.config.swagger;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("a-bank-public")
                .pathsToMatch("/star-bank/**")
                .packagesToScan("dev.alexfossa204.starbank.registration.controller")
                .build();
    }

    @Bean
    public OpenAPI affinityBankOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Star Bank Registration Microservice")
                        .description("This Microservice is responsible for user registration in the A-Bank Microservice Ecosystem. More Detailed information about component functionality - read below\n\n")
                        .version("v0.0.2"))
                .externalDocs(new ExternalDocumentation()
                        .description("Star Bank Wiki Documentation")
                        .url("https://google.com"));
    }
}