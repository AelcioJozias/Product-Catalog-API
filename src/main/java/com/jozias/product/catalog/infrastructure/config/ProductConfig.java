package com.jozias.product.catalog.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jozias.product.catalog.application.gateway.DeleteProductGateway;
import com.jozias.product.catalog.application.gateway.FindProductGateway;
import com.jozias.product.catalog.application.gateway.FindSellerGateway;
import com.jozias.product.catalog.application.gateway.SaveProductGateway;
import com.jozias.product.catalog.application.usecase.CreateProductUseCase;
import com.jozias.product.catalog.application.usecase.DeleteProductUsecase;
import com.jozias.product.catalog.application.usecase.FindProductUseCase;
import com.jozias.product.catalog.application.usecase.UpdateProductUseCase;
import com.jozias.product.catalog.infrastructure.gateway.DeleteProductGatewayImpl;
import com.jozias.product.catalog.infrastructure.gateway.FindProductGatewayImpl;
import com.jozias.product.catalog.infrastructure.gateway.SaveProductGatewayImpl;
import com.jozias.product.catalog.infrastructure.persistence.mapper.ProductEntityMapper;
import com.jozias.product.catalog.infrastructure.persistence.repository.ProductRepository;

@Configuration
public class ProductConfig {

    @Bean
    public FindProductGateway productGateway(ProductRepository productRepository,
            ProductEntityMapper productEntityMapper) {
        return new FindProductGatewayImpl(productRepository, productEntityMapper);
    }

    @Bean
    public SaveProductGateway saveProductGateway(ProductRepository productRepository,
            ProductEntityMapper productEntityMapper) {
        return new SaveProductGatewayImpl(productRepository, productEntityMapper);
    }

    @Bean
    public DeleteProductGateway deleteProductGateway(ProductRepository productRepository) {
        return new DeleteProductGatewayImpl(productRepository);
    }

    @Bean
    public FindProductUseCase productUseCase(FindProductGateway findProductGateway) {
        return new FindProductUseCase(findProductGateway);
    }

    @Bean
    public CreateProductUseCase createProductUseCase(SaveProductGateway saveProductGateway,
            FindSellerGateway findSellerGateway) {
        return new CreateProductUseCase(saveProductGateway, findSellerGateway);
    }

    @Bean
    public UpdateProductUseCase updateProductUseCase(SaveProductGateway saveProductGateway,
            FindProductGateway findProductGateway) {
        return new UpdateProductUseCase(saveProductGateway, findProductGateway);
    }

    @Bean
    public DeleteProductUsecase deleteProductUsecase(DeleteProductGateway deleteProductGateway) {
        return new DeleteProductUsecase(deleteProductGateway);
    }
}
