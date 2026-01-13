package com.jozias.product.catalog.infrastructure.gateway;

import com.jozias.product.catalog.application.gateway.SaveProductGateway;
import com.jozias.product.catalog.domain.entity.Product;
import com.jozias.product.catalog.infrastructure.persistence.mapper.ProductEntityMapper;
import com.jozias.product.catalog.infrastructure.persistence.repository.ProductRepository;

public class SaveProductGatewayImpl implements SaveProductGateway {

    private final ProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;

    public SaveProductGatewayImpl(ProductRepository productRepository, ProductEntityMapper productEntityMapper) {
        this.productRepository = productRepository;
        this.productEntityMapper = productEntityMapper;
    }

    @Override
    public Product save(Product product) {
        return productEntityMapper.toDomain(productRepository.save(productEntityMapper.toEntity(product)));
    }
}
