package com.jozias.product.catalog.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jozias.product.catalog.infrastructure.persistence.entity.SellerEntity;

public interface SellerRepository extends JpaRepository<SellerEntity, Long> {
}
