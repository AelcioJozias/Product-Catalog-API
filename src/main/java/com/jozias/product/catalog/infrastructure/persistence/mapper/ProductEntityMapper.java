package com.jozias.product.catalog.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.jozias.product.catalog.domain.entity.Product;
import com.jozias.product.catalog.infrastructure.persistence.entity.ProductEntity;

@Mapper(componentModel = "spring", uses = { SellerEntityMapper.class, ProductVariantEntityMapper.class })
public abstract class ProductEntityMapper {

    public abstract Product toDomain(ProductEntity entity);

    public abstract ProductEntity toEntity(Product domain);

    @org.mapstruct.AfterMapping
    protected void linkVariants(@org.mapstruct.MappingTarget ProductEntity entity) {
        if (entity.getVariants() != null) {
            entity.getVariants().forEach(variant -> variant.setProduct(entity));
        }
    }
}
