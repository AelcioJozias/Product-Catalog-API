package com.jozias.product.catalog.infrastructure.controller.dto;

import java.util.List;

public record ProductVariantDTO(Long id, String type, List<ProductVariantValueDTO> values) {
}