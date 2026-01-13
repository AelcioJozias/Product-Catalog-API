package com.jozias.product.catalog.infrastructure.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Schema(description = "Variante do produto para criação (ex: Cor)")
public record CreateProductVariantRequest(
                @Schema(description = "Tipo da variante", example = "COR") @NotBlank(message = "Variant type is required") String type,

                @Schema(description = "Valores da variante") @Valid List<CreateProductVariantValueRequest> values) {
}
