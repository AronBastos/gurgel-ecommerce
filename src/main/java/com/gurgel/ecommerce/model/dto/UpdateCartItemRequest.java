package com.gurgel.ecommerce.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Requisição para atualizar a quantidade de um item do carrinho.
 */
public record UpdateCartItemRequest(

        @NotNull(message = "A quantidade é obrigatória")
        @Min(value = 1, message = "A quantidade mínima é 1")
        Integer quantity
) {
}
