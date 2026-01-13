package com.jozias.product.catalog.infrastructure.controller.mapper;

import org.springframework.stereotype.Component;

import com.jozias.product.catalog.domain.entity.ProductVariant;
import com.jozias.product.catalog.domain.entity.ProductVariantValue;
import com.jozias.product.catalog.infrastructure.controller.dto.CreateProductVariantRequest;
import com.jozias.product.catalog.infrastructure.controller.dto.CreateProductVariantValueRequest;
import com.jozias.product.catalog.infrastructure.controller.dto.UpdateProductVariantRequest;
import com.jozias.product.catalog.infrastructure.controller.dto.UpdateProductVariantValueRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ProductRequestMapper {

    // ========== Métodos para Criação (sem IDs) ==========

    public List<ProductVariant> toCreateDomainVariants(List<CreateProductVariantRequest> variantRequests) {
        if (variantRequests == null) {
            return new ArrayList<>();
        }
        return variantRequests.stream()
                .map(this::toCreateDomainVariant)
                .toList();
    }

    private ProductVariant toCreateDomainVariant(CreateProductVariantRequest request) {
        List<ProductVariantValue> values = Collections.emptyList();
        if (request.values() != null) {
            values = request.values().stream()
                    .map(this::toCreateDomainVariantValue)
                    .toList();
        }
        return new ProductVariant(null, request.type(), values);
    }

    private ProductVariantValue toCreateDomainVariantValue(CreateProductVariantValueRequest request) {
        return new ProductVariantValue(request.value());
    }

    // ========== Métodos para Atualização (com IDs) ==========

    public List<ProductVariant> toUpdateDomainVariants(List<UpdateProductVariantRequest> variantRequests) {
        if (variantRequests == null) {
            return new ArrayList<>();
        }
        return variantRequests.stream()
                .map(this::toUpdateDomainVariant)
                .toList();
    }

    private ProductVariant toUpdateDomainVariant(UpdateProductVariantRequest request) {
        List<ProductVariantValue> values = Collections.emptyList();
        if (request.values() != null) {
            values = request.values().stream()
                    .map(this::toUpdateDomainVariantValue)
                    .toList();
        }
        return new ProductVariant(request.id(), request.type(), values);
    }

    private ProductVariantValue toUpdateDomainVariantValue(UpdateProductVariantValueRequest request) {
        if (request.id() != null) {
            return new ProductVariantValue(request.id(), request.value());
        }
        return new ProductVariantValue(request.value());
    }
}