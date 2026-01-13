package com.jozias.product.catalog.application.usecase;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jozias.product.catalog.application.gateway.FindProductGateway;
import com.jozias.product.catalog.domain.entity.Product;
import com.jozias.product.catalog.domain.exception.EntityNotFoundException;

public class FindProductUseCase {

    private final FindProductGateway findProductGateway;

    public FindProductUseCase(FindProductGateway findProductGateway) {
        this.findProductGateway = findProductGateway;
    }

    @Cacheable(value = "products", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort.toString()")
    public Page<Product> findAll(Pageable pageable) {
        return findProductGateway.list(pageable);
    }

    @Cacheable(value = "productDetails", key = "#id")
    public Product findById(Long id) {
        return findProductGateway.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Product with id %s not found".formatted(id)));
    }
}
