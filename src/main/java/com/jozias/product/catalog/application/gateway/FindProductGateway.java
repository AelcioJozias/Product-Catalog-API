package com.jozias.product.catalog.application.gateway;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jozias.product.catalog.domain.entity.Product;

import java.util.Optional;

public interface FindProductGateway {
    Page<Product> list(Pageable pageable);

    Optional<Product> findById(Long id);
}
