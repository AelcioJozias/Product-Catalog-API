package com.jozias.product.catalog.application.usecase;

import com.jozias.product.catalog.application.gateway.FindSellerGateway;
import com.jozias.product.catalog.domain.entity.Seller;
import com.jozias.product.catalog.domain.exception.EntityNotFoundException;

public class FindSellerUseCase {

    private final FindSellerGateway findSellerGateway;

    public FindSellerUseCase(FindSellerGateway findSellerGateway) {
        this.findSellerGateway = findSellerGateway;
    }

    Seller findById(Long id) {
        return findSellerGateway.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seller with id %s not found".formatted(id)));
    }

}
