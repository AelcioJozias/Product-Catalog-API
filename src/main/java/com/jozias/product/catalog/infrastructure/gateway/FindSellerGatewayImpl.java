package com.jozias.product.catalog.infrastructure.gateway;

import java.util.Optional;

import com.jozias.product.catalog.application.gateway.FindSellerGateway;
import com.jozias.product.catalog.domain.entity.Seller;
import com.jozias.product.catalog.infrastructure.persistence.mapper.SellerEntityMapper;
import com.jozias.product.catalog.infrastructure.persistence.repository.SellerRepository;

public class FindSellerGatewayImpl implements FindSellerGateway {

    private final SellerRepository sellerRepository;
    private final SellerEntityMapper sellerEntityMapper;

    public FindSellerGatewayImpl(SellerRepository sellerRepository, SellerEntityMapper sellerEntityMapper) {
        this.sellerRepository = sellerRepository;
        this.sellerEntityMapper = sellerEntityMapper;
    }

    @Override
    public Optional<Seller> findById(Long id) {
        return sellerRepository.findById(id).map(sellerEntityMapper::toDomain);
    }
}
