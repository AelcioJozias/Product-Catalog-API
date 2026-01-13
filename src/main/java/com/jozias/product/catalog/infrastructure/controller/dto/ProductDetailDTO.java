package com.jozias.product.catalog.infrastructure.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

import com.jozias.product.catalog.domain.entity.Condition;

@Schema(description = "DTO detalhado do produto para visualização")
public record ProductDetailDTO(
        @Schema(description = "ID do produto", example = "1") Long id,
        @Schema(description = "Nome do produto", example = "Smartphone") String name,
        @Schema(description = "Descrição", example = "Descrição detalhada...") String description,
        @Schema(description = "Preço", example = "999.99") BigDecimal price,
        @Schema(description = "Quantidade", example = "10") Integer availableQuantity,
        @Schema(description = "Condição", example = "NEW") Condition condition,
        @Schema(description = "Categoria", example = "Electronics") String category,
        @Schema(description = "Variantes") List<ProductVariantDTO> variants,
        @Schema(description = "Informações do vendedor") SellerDTO seller) {
}