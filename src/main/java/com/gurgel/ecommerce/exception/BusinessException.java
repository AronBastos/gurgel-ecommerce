package com.gurgel.ecommerce.exception;

/**
 * Lançada quando uma regra de negócio é violada (ex.: estoque insuficiente,
 * carrinho vazio no checkout). Mapeada para HTTP 400 pelo
 * {@link GlobalExceptionHandler}.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
