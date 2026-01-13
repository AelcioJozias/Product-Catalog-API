package com.jozias.product.catalog.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.jozias.product.catalog.domain.entity.Seller;
import com.jozias.product.catalog.infrastructure.persistence.entity.SellerEntity;

@Mapper(componentModel = "spring")
public interface SellerEntityMapper {

    Seller toDomain(SellerEntity entity);

    SellerEntity toEntity(Seller domain);
}
