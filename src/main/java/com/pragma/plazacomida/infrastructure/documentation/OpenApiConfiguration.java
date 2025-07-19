package com.pragma.plazacomida.infrastructure.documentation;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Plaza Comida - Microservicio de Usuarios")
                        .description("Reto de Java - Spring Boot - Pragma")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Plaza Comida Team")
                                .email("support@plazacomida.com")
                                .url("https://plazacomida.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8081")
                                .description("Development server"),
                        new Server()
                                .url("https://api.plazacomida.com")
                                .description("Production server")
                ));
    }
}