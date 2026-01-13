package com.jozias.product.catalog.infrastructure.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.CacheManager;

import com.jozias.product.catalog.application.gateway.DeleteProductGateway;
import com.jozias.product.catalog.application.gateway.FindProductGateway;
import com.jozias.product.catalog.application.gateway.FindSellerGateway;
import com.jozias.product.catalog.application.gateway.SaveProductGateway;
import com.jozias.product.catalog.application.usecase.CreateProductUseCase;
import com.jozias.product.catalog.application.usecase.DeleteProductUsecase;
import com.jozias.product.catalog.application.usecase.FindProductUseCase;
import com.jozias.product.catalog.application.usecase.FindSellerUseCase;
import com.jozias.product.catalog.application.usecase.UpdateProductUseCase;
import com.jozias.product.catalog.infrastructure.config.CacheConfig;
import com.jozias.product.catalog.infrastructure.config.ProductConfig;
import com.jozias.product.catalog.infrastructure.config.SellerConfig;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("Infrastructure Config Test")
class InfrastructureConfigTest {

    @Mock
    private SaveProductGateway saveProductGateway;
    @Mock
    private FindProductGateway findProductGateway;
    @Mock
    private DeleteProductGateway deleteProductGateway;
    @Mock
    private FindSellerGateway findSellerGateway;

    @Test
    @DisplayName("CacheConfig should create CacheManager")
    void cacheConfig_shouldCreateCacheManager() {
        CacheConfig config = new CacheConfig();
        CacheManager cacheManager = config.cacheManager();
        assertThat(cacheManager).isNotNull();
    }

    @Test
    @DisplayName("ProductConfig should create all product use cases and gateways")
    void productConfig_shouldCreateUseCasesAndGateways() {
        ProductConfig config = new ProductConfig();

        // Gateways
        FindProductGateway fpg = config.productGateway(null, null);
        SaveProductGateway spg = config.saveProductGateway(null, null);
        DeleteProductGateway dpg = config.deleteProductGateway(null);

        // Use cases
        CreateProductUseCase createUseCase = config.createProductUseCase(saveProductGateway, findSellerGateway);
        UpdateProductUseCase updateUseCase = config.updateProductUseCase(saveProductGateway, findProductGateway);
        FindProductUseCase findUseCase = config.productUseCase(findProductGateway);
        DeleteProductUsecase deleteUseCase = config.deleteProductUsecase(deleteProductGateway);

        assertThat(fpg).isNotNull();
        assertThat(spg).isNotNull();
        assertThat(dpg).isNotNull();
        assertThat(createUseCase).isNotNull();
        assertThat(updateUseCase).isNotNull();
        assertThat(findUseCase).isNotNull();
        assertThat(deleteUseCase).isNotNull();
    }

    @Test
    @DisplayName("SellerConfig should create find seller use case and gateway")
    void sellerConfig_shouldCreateUseCaseAndGateway() {
        SellerConfig config = new SellerConfig();

        FindSellerGateway fsg = config.findSellerGateway(null, null);
        FindSellerUseCase findUseCase = config.findSellerUseCase(findSellerGateway);

        assertThat(fsg).isNotNull();
        assertThat(findUseCase).isNotNull();
    }
}
