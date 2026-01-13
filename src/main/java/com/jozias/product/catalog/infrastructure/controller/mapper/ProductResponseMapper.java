package com.jozias.product.catalog.infrastructure.controller.mapper;

import org.springframework.stereotype.Component;

import com.jozias.product.catalog.domain.entity.Product;
import com.jozias.product.catalog.domain.entity.ProductVariant;
import com.jozias.product.catalog.domain.entity.ProductVariantValue;
import com.jozias.product.catalog.domain.entity.Seller;
import com.jozias.product.catalog.infrastructure.controller.dto.ProductDTO;
import com.jozias.product.catalog.infrastructure.controller.dto.ProductDetailDTO;
import com.jozias.product.catalog.infrastructure.controller.dto.ProductResponse;
import com.jozias.product.catalog.infrastructure.controller.dto.ProductVariantDTO;
import com.jozias.product.catalog.infrastructure.controller.dto.ProductVariantValueDTO;
import com.jozias.product.catalog.infrastructure.controller.dto.SellerDTO;

import java.util.Collections;
import java.util.List;

@Component
public class ProductResponseMapper {

    public ProductDTO toDto(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice());
    }

    public ProductDetailDTO toDetailDto(Product product) {
        return new ProductDetailDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getAvailableQuantity(),
                product.getCondition(),
                product.getCategory(),
                mapVariants(product.getVariants()),
                mapSeller(product.getSeller()));
    }

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getAvailableQuantity(),
                product.getCondition(),
                product.getCategory(),
                mapVariants(product.getVariants()),
                mapSeller(product.getSeller()));
    }

    private List<ProductVariantDTO> mapVariants(List<ProductVariant> variants) {
        if (variants == null) {
            return Collections.emptyList();
        }
        return variants.stream()
                .map(this::mapVariant)
                .toList();
    }

    private ProductVariantDTO mapVariant(ProductVariant variant) {
        return new ProductVariantDTO(
                variant.getId(),
                variant.getType(),
                mapVariantValues(variant.getValues()));
    }

    private List<ProductVariantValueDTO> mapVariantValues(List<ProductVariantValue> values) {
        if (values == null) {
            return Collections.emptyList();
        }
        return values.stream()
                .map(this::mapVariantValue)
                .toList();
    }

    private ProductVariantValueDTO mapVariantValue(ProductVariantValue value) {
        return new ProductVariantValueDTO(
                value.getId(),
                value.getValue());
    }

    private SellerDTO mapSeller(Seller seller) {
        if (seller == null) {
            return null;
        }
        return new SellerDTO(
                seller.getId(),
                seller.getName(),
                seller.getDescription(),
                seller.getScore());
    }
}