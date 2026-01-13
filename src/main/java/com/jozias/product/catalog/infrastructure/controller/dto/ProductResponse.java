package com.jozias.product.catalog.infrastructure.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

import com.jozias.product.catalog.domain.entity.Condition;

@Schema(description = "Dados de resposta detalhados de um produto")
public record ProductResponse(
        @Schema(description = "ID único do produto", example = "1") Long id,
        @Schema(description = "Nome do produto", example = "Smartphone Samsung Galaxy S21") String name,
        @Schema(description = "Descrição detalhada do produto", example = "Smartphone com 128GB de memória...") String description,
        @Schema(description = "Preço unitário", example = "3500.00") BigDecimal price,
        @Schema(description = "Quantidade disponível", example = "50") Integer availableQuantity,
        @Schema(description = "Condição do produto", example = "NEW") Condition condition,
        @Schema(description = "Categoria", example = "Eletrônicos") String category,
        @Schema(description = "Variantes do produto") List<ProductVariantDTO> variants,
        @Schema(description = "Vendedor responsável") SellerDTO seller) {
}
