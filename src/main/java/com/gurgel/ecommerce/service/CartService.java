package com.gurgel.ecommerce.service;

import com.gurgel.ecommerce.model.dto.AddCartItemRequest;
import com.gurgel.ecommerce.model.dto.CartResponseDTO;
import com.gurgel.ecommerce.model.dto.UpdateCartItemRequest;

/**
 * Casos de uso do carrinho de compras.
 */
public interface CartService {

    CartResponseDTO createCart();

    CartResponseDTO getCart(Long cartId);

    CartResponseDTO addItem(Long cartId, AddCartItemRequest request);

    CartResponseDTO updateItem(Long cartId, Long itemId, UpdateCartItemRequest request);

    CartResponseDTO removeItem(Long cartId, Long itemId);
}
