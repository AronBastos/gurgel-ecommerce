package com.gurgel.ecommerce.model.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Carrinho completo exposto pela API, com itens e total.
 */
public record CartResponseDTO(
        Long id,
        List<CartItemResponseDTO> items,
        BigDecimal total,
        boolean checkedOut
) {
}
