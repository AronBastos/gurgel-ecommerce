package com.gurgel.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Ponto de entrada da aplicação Gurgel E-commerce.
 *
 * <p>API REST para o catálogo de carros clássicos Gurgel, com carrinho de
 * compras e emissão de pedidos. Segue arquitetura em camadas (Controller ->
 * Service -> Repository) e princípios SOLID.
 */
@SpringBootApplication
public class GurgelEcommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GurgelEcommerceApplication.class, args);
    }
}
