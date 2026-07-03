package com.gurgel.ecommerce.exception;

/**
 * Lançada quando um recurso solicitado não é encontrado. Mapeada para
 * HTTP 404 pelo {@link GlobalExceptionHandler}.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException of(String resource, Object id) {
        return new ResourceNotFoundException(resource + " não encontrado(a) com id: " + id);
    }
}
