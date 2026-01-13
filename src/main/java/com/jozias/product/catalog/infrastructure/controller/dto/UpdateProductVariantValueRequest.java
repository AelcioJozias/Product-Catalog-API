package com.jozias.product.catalog.infrastructure.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Valor de uma variante para atualização de produto")
public record UpdateProductVariantValueRequest(
                @Schema(description = "ID do valor da variante (para atualização de valores existentes)", example = "1") Long id,

                @Schema(description = "Valor da variante", example = "Azul") @NotBlank(message = "Value is required") String value) {
}
