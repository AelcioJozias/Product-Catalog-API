package com.jozias.product.catalog.domain.gateway;

import com.jozias.product.catalog.domain.entity.Product;

public interface SaveProductGateway {
    Product save(Product product);
}
