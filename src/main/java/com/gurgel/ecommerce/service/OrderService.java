package com.gurgel.ecommerce.service;

import com.gurgel.ecommerce.model.dto.CheckoutRequest;
import com.gurgel.ecommerce.model.dto.OrderResponseDTO;

import java.util.List;

/**
 * Casos de uso de pedidos.
 */
public interface OrderService {

    OrderResponseDTO checkout(CheckoutRequest request);

    OrderResponseDTO findById(Long orderId);

    List<OrderResponseDTO> findAll();

    List<OrderResponseDTO> findByCustomerEmail(String email);

    OrderResponseDTO cancel(Long orderId);
}
