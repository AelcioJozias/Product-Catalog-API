package com.jozias.product.catalog.application.dto;

import java.math.BigDecimal;
import java.util.List;

import com.jozias.product.catalog.domain.entity.Condition;
import com.jozias.product.catalog.domain.entity.ProductVariant;

public record CreateProductDTO(
                Long sellerId,
                String name,
                String description,
                BigDecimal price,
                Integer availableQuantity,
                Condition condition,
                String category,
                List<ProductVariant> variants) {
}
