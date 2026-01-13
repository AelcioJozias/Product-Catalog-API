package com.jozias.product.catalog.infrastructure.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiErrorResponse(
        String type,
        String title,
        int status,
        String detail,
        String instance,
        LocalDateTime timestamp,
        List<FieldError> errors) {
    public record FieldError(String field, String message) {
    }

    public static ApiErrorResponse of(String type, String title, int status, String detail, String instance) {
        return new ApiErrorResponse(type, title, status, detail, instance, LocalDateTime.now(), null);
    }

    public static ApiErrorResponse withErrors(String type, String title, int status, String detail, String instance,
            List<FieldError> errors) {
        return new ApiErrorResponse(type, title, status, detail, instance, LocalDateTime.now(), errors);
    }
}
