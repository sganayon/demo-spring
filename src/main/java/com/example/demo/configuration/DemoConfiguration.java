package com.example.demo.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DemoConfiguration {

    /**
     * Définition d'un bean de type OpenAPI nécessaire pour la configuration de Springdoc OpenAPI
     */
    @Bean
    public OpenAPI connectorOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Demo API")
                        .description("Description")
                        .version("v1"))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentation")
                        .url("url du confluence ou autre"));
    }
}
