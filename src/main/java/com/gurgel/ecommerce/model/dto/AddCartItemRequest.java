package com.gurgel.ecommerce.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Requisição para adicionar um carro ao carrinho.
 */
public record AddCartItemRequest(

        @NotNull(message = "O id do carro é obrigatório")
        Long carId,

        @NotNull(message = "A quantidade é obrigatória")
        @Min(value = 1, message = "A quantidade mínima é 1")
        Integer quantity
) {
}
