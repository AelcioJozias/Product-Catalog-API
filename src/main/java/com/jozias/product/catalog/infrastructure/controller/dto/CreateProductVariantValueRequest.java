package com.jozias.product.catalog.infrastructure.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Valor de uma variante para criação de produto")
public record CreateProductVariantValueRequest(
                @Schema(description = "Valor da variante", example = "Azul") @NotBlank(message = "Value is required") String value) {
}
