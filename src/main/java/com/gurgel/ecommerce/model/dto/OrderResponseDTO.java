package com.gurgel.ecommerce.model.dto;

import com.gurgel.ecommerce.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Pedido completo exposto pela API.
 */
public record OrderResponseDTO(
        Long id,
        String customerName,
        String customerEmail,
        List<OrderItemResponseDTO> items,
        BigDecimal totalAmount,
        OrderStatus status,
        LocalDateTime createdAt
) {
}
