package com.gurgel.ecommerce.model.enums;

/**
 * Estados possíveis do ciclo de vida de um pedido.
 */
public enum OrderStatus {
    PENDING,    // Pedido criado, aguardando pagamento
    PAID,       // Pagamento confirmado
    SHIPPED,    // Pedido enviado
    DELIVERED,  // Pedido entregue
    CANCELLED   // Pedido cancelado
}
