package com.jozias.product.catalog.application.usecase;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;

import com.jozias.product.catalog.application.gateway.DeleteProductGateway;

@Slf4j
public class DeleteProductUsecase {

    private final DeleteProductGateway deleteProductGateway;

    public DeleteProductUsecase(DeleteProductGateway deleteProductGateway) {
        this.deleteProductGateway = deleteProductGateway;
    }

    @Caching(evict = {
            @CacheEvict(value = "products", allEntries = true),
            @CacheEvict(value = "productDetails", key = "#id")
    })
    public void deleteById(Long id) {
        deleteProductGateway.deleteById(id);
        log.info("Product ID: {} deleted", id);
    }
}
