package com.gurgel.ecommerce.model.dto;

import java.math.BigDecimal;

/**
 * Item do carrinho exposto pela API.
 */
public record CartItemResponseDTO(
        Long itemId,
        Long carId,
        String carModel,
        String imageUrl,
        BigDecimal unitPrice,
        int quantity,
        BigDecimal subtotal
) {
}
