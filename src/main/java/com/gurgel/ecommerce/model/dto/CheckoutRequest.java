package com.gurgel.ecommerce.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Requisição de checkout: transforma um carrinho em pedido.
 */
public record CheckoutRequest(

        @NotNull(message = "O id do carrinho é obrigatório")
        Long cartId,

        @NotBlank(message = "O nome do cliente é obrigatório")
        @Size(min = 2, max = 120, message = "O nome deve ter entre 2 e 120 caracteres")
        String customerName,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String customerEmail
) {
}
