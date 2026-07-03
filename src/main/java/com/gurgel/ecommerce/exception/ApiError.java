package com.gurgel.ecommerce.exception;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Corpo padronizado de resposta de erro da API.
 */
public record ApiError(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        List<FieldValidationError> fieldErrors
) {

    public static ApiError of(int status, String error, String message, String path) {
        return new ApiError(LocalDateTime.now(), status, error, message, path, List.of());
    }

    public static ApiError of(int status, String error, String message, String path,
                              List<FieldValidationError> fieldErrors) {
        return new ApiError(LocalDateTime.now(), status, error, message, path, fieldErrors);
    }

    /**
     * Detalha o erro de validação de um campo específico.
     */
    public record FieldValidationError(String field, String message) {
    }
}
