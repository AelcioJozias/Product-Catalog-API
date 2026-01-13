package com.jozias.product.catalog.infrastructure.gateway;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jozias.product.catalog.application.gateway.FindProductGateway;
import com.jozias.product.catalog.domain.entity.Product;
import com.jozias.product.catalog.infrastructure.persistence.mapper.ProductEntityMapper;
import com.jozias.product.catalog.infrastructure.persistence.repository.ProductRepository;

import java.util.Optional;

public class FindProductGatewayImpl implements FindProductGateway {

    private final ProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;

    public FindProductGatewayImpl(ProductRepository productRepository, ProductEntityMapper productEntityMapper) {
        this.productRepository = productRepository;
        this.productEntityMapper = productEntityMapper;
    }

    @Override
    public Page<Product> list(Pageable pageable) {
        return productRepository.findAll(pageable).map(productEntityMapper::toDomain);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id).map(productEntityMapper::toDomain);
    }

}
