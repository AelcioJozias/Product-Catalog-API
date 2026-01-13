package com.jozias.product.catalog.infrastructure.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Requisição para atualização de um produto existente")
public record UpdateProductRequest(
                @Schema(description = "Nome do produto", example = "Smartphone Samsung Galaxy S21") @NotBlank(message = "Name is required") @Size(min = 3, max = 200, message = "Name must be between 3 and 200 characters") String name,

                @Schema(description = "Descrição detalhada do produto", example = "Smartphone com 128GB de memória, Câmera Tripla, Tela de 6.2\"") @NotBlank(message = "Description is required") String description,

                @Schema(description = "Preço unitário do produto", example = "3500.00") @NotNull(message = "Price is required") @Positive(message = "Price must be positive") BigDecimal price,

                @Schema(description = "Quantidade disponível em estoque", example = "50") @NotNull(message = "Available quantity is required") @Min(value = 0, message = "Available quantity cannot be negative") Integer availableQuantity,

                @Schema(description = "Condição do produto", example = "NEW", allowableValues = {
                                "NEW", "USED",
                                "REFURBISHED" }) @NotBlank(message = "Condition is required") String condition,

                @Schema(description = "Categoria do produto", example = "Eletrônicos") @NotBlank(message = "Category is required") String category,

                @Schema(description = "Lista de variantes do produto (cor, tamanho, etc.)") @Valid List<UpdateProductVariantRequest> variants) {
}
