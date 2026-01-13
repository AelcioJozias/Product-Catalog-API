package com.jozias.product.catalog.infrastructure.gateway;

import com.jozias.product.catalog.application.gateway.DeleteProductGateway;
import com.jozias.product.catalog.infrastructure.persistence.repository.ProductRepository;

public class DeleteProductGatewayImpl implements DeleteProductGateway {

    private final ProductRepository productRepository;

    public DeleteProductGatewayImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
