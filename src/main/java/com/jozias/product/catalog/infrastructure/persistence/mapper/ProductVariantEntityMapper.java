package com.jozias.product.catalog.infrastructure.persistence.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.jozias.product.catalog.domain.entity.ProductVariant;
import com.jozias.product.catalog.infrastructure.persistence.entity.ProductVariantEntity;

@Mapper(componentModel = "spring", uses = { ProductVariantValueEntityMapper.class })
public abstract class ProductVariantEntityMapper {

    public abstract ProductVariant toDomain(ProductVariantEntity entity);

    @Mapping(target = "sortOrder", ignore = true)
    @Mapping(target = "product", ignore = true)
    public abstract ProductVariantEntity toEntity(ProductVariant domain);

    @AfterMapping
    protected void linkValues(@MappingTarget ProductVariantEntity entity) {
        if (entity.getValues() != null) {
            entity.getValues().forEach(value -> value.setVariant(entity));
        }
    }
}
