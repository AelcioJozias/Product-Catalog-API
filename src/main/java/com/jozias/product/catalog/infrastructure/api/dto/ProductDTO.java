package com.jozias.product.catalog.infrastructure.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "DTO resumido do produto para listagens")
public record ProductDTO(
        @Schema(description = "ID do produto", example = "1") Long id,
        @Schema(description = "Nome do produto", example = "Smartphone") String name,
        @Schema(description = "Preço", example = "999.99") BigDecimal price) {
}