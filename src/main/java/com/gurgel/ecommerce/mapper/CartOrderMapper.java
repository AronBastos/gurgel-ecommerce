package com.gurgel.ecommerce.mapper;

import com.gurgel.ecommerce.model.dto.CartItemResponseDTO;
import com.gurgel.ecommerce.model.dto.CartResponseDTO;
import com.gurgel.ecommerce.model.dto.OrderItemResponseDTO;
import com.gurgel.ecommerce.model.dto.OrderResponseDTO;
import com.gurgel.ecommerce.model.entity.Cart;
import com.gurgel.ecommerce.model.entity.CartItem;
import com.gurgel.ecommerce.model.entity.Order;
import com.gurgel.ecommerce.model.entity.OrderItem;
import org.springframework.stereotype.Component;

/**
 * Converte entidades de carrinho e pedido em seus DTOs de resposta.
 * Mapeamento explícito por envolver cálculos (subtotais/total).
 */
@Component
public class CartOrderMapper {

    public CartResponseDTO toCartResponse(Cart cart) {
        var items = cart.getItems().stream()
                .map(this::toCartItemResponse)
                .toList();
        return new CartResponseDTO(cart.getId(), items, cart.getTotal(), cart.isCheckedOut());
    }

    private CartItemResponseDTO toCartItemResponse(CartItem item) {
        return new CartItemResponseDTO(
                item.getId(),
                item.getCar().getId(),
                item.getCar().getModel(),
                item.getCar().getImageUrl(),
                item.getCar().getPrice(),
                item.getQuantity(),
                item.getSubtotal()
        );
    }

    public OrderResponseDTO toOrderResponse(Order order) {
        var items = order.getItems().stream()
                .map(this::toOrderItemResponse)
                .toList();
        return new OrderResponseDTO(
                order.getId(),
                order.getCustomerName(),
                order.getCustomerEmail(),
                items,
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }

    private OrderItemResponseDTO toOrderItemResponse(OrderItem item) {
        return new OrderItemResponseDTO(
                item.getCarId(),
                item.getCarModel(),
                item.getUnitPrice(),
                item.getQuantity(),
                item.getSubtotal()
        );
    }
}
