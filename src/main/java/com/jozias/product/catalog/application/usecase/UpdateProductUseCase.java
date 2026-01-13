package com.jozias.product.catalog.application.usecase;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;

import com.jozias.product.catalog.application.dto.UpdateProductDTO;
import com.jozias.product.catalog.application.gateway.FindProductGateway;
import com.jozias.product.catalog.application.gateway.SaveProductGateway;
import com.jozias.product.catalog.domain.entity.Product;
import com.jozias.product.catalog.domain.exception.EntityNotFoundException;

@Slf4j
public class UpdateProductUseCase {

    private final SaveProductGateway saveProductGateway;
    private final FindProductGateway findProductGateway;

    public UpdateProductUseCase(SaveProductGateway saveProductGateway, FindProductGateway findProductGateway) {
        this.saveProductGateway = saveProductGateway;
        this.findProductGateway = findProductGateway;
    }

    @Caching(evict = {
            @CacheEvict(value = "products", allEntries = true),
            @CacheEvict(value = "productDetails", key = "#dto.id")
    })
    public Product execute(UpdateProductDTO dto) {
        Product product = findProductGateway.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + dto.id()));

        product.update(
                dto.name(),
                dto.description(),
                dto.price(),
                dto.availableQuantity(),
                dto.condition(),
                dto.category());

        product.updateVariants(dto.variants());

        Product savedProduct = saveProductGateway.save(product);
        log.info("Product ID: {} successfully updated", savedProduct.getId());
        return savedProduct;
    }
}