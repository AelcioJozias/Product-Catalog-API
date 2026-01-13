package com.jozias.product.catalog.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jozias.product.catalog.infrastructure.persistence.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
