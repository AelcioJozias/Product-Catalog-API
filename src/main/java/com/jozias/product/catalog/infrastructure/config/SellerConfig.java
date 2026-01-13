package com.jozias.product.catalog.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jozias.product.catalog.application.gateway.FindSellerGateway;
import com.jozias.product.catalog.application.usecase.FindSellerUseCase;
import com.jozias.product.catalog.infrastructure.gateway.FindSellerGatewayImpl;
import com.jozias.product.catalog.infrastructure.persistence.mapper.SellerEntityMapper;
import com.jozias.product.catalog.infrastructure.persistence.repository.SellerRepository;

@Configuration
public class SellerConfig {

    @Bean
    public FindSellerGateway findSellerGateway(SellerRepository sellerRepository,
            SellerEntityMapper sellerEntityMapper) {
        return new FindSellerGatewayImpl(sellerRepository, sellerEntityMapper);
    }

    @Bean
    public FindSellerUseCase findSellerUseCase(FindSellerGateway findSellerGateway) {
        return new FindSellerUseCase(findSellerGateway);
    }
}