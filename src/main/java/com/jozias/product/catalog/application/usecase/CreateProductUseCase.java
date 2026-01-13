package com.jozias.product.catalog.application.usecase;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;

import com.jozias.product.catalog.application.dto.CreateProductDTO;
import com.jozias.product.catalog.application.gateway.FindSellerGateway;
import com.jozias.product.catalog.application.gateway.SaveProductGateway;
import com.jozias.product.catalog.domain.entity.Product;
import com.jozias.product.catalog.domain.entity.Seller;
import com.jozias.product.catalog.domain.exception.EntityNotFoundException;

@Slf4j
public class CreateProductUseCase {

    private final SaveProductGateway saveProductGateway;
    private final FindSellerGateway findSellerGateway;

    public CreateProductUseCase(SaveProductGateway saveProductGateway, FindSellerGateway findSellerGateway) {
        this.saveProductGateway = saveProductGateway;
        this.findSellerGateway = findSellerGateway;
    }

    @CacheEvict(value = "products", allEntries = true)
    public Product execute(CreateProductDTO dto) {
        Seller seller = findSellerGateway.findById(dto.sellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with id: " + dto.sellerId()));

        Product product = new Product(
                null,
                dto.name(),
                dto.description(),
                dto.price(),
                dto.availableQuantity(),
                dto.condition(),
                dto.category(),
                dto.variants(),
                seller);

        Product savedProduct = saveProductGateway.save(product);
        log.info("Product successfully created with ID: {}", savedProduct.getId());
        return savedProduct;
    }
}