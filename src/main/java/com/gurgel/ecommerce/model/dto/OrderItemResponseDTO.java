package com.gurgel.ecommerce.model.dto;

import java.math.BigDecimal;

/**
 * Item de pedido exposto pela API (snapshot da compra).
 */
public record OrderItemResponseDTO(
        Long carId,
        String carModel,
        BigDecimal unitPrice,
        int quantity,
        BigDecimal subtotal
) {
}
