package com.jozias.product.catalog.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.jozias.product.catalog.domain.entity.ProductVariantValue;
import com.jozias.product.catalog.infrastructure.persistence.entity.ProductVariantValueEntity;

@Mapper(componentModel = "spring")
public interface ProductVariantValueEntityMapper {

    ProductVariantValue toDomain(ProductVariantValueEntity entity);

    @Mapping(target = "variant", ignore = true)
    ProductVariantValueEntity toEntity(ProductVariantValue domain);
}
