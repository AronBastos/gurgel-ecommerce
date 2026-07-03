package com.gurgel.ecommerce.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Metadados da documentação OpenAPI / Swagger UI.
 * Disponível em {@code /api/swagger-ui.html}.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI gurgelOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gurgel E-commerce API")
                        .description("API REST para o catálogo de carros clássicos Gurgel, "
                                + "com carrinho de compras e emissão de pedidos.")
                        .version("1.0.0")
                        .contact(new Contact().name("Aron Lopes").email("aron.lopes1@gmail.com"))
                        .license(new License().name("MIT")));
    }
}
