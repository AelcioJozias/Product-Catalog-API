package com.jozias.product.catalog.application.gateway;

import com.jozias.product.catalog.domain.entity.Product;

public interface SaveProductGateway {
    Product save(Product product);
}
