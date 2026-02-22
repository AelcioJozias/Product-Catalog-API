package com.jozias.product.catalog.domain.gateway;

import java.util.Optional;

import com.jozias.product.catalog.domain.entity.Seller;

public interface FindSellerGateway {
    Optional<Seller> findById(Long id);
}