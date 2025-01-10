package com.restaurante.restaurante.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {


    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Api de Inventario heladeria")
                                .description("Documentacion interactiva de la API de inventario")
                                .version("1.0.0")
                                .contact(
                                        new Contact()
                                                .name("Equipo de desarrollo de Sher")
                                                .email("equipo@desarrollo.com")
                                )
                );
    }

}
