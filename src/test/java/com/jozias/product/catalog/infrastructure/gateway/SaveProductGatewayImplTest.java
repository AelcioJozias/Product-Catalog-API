package com.jozias.product.catalog.infrastructure.gateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jozias.product.catalog.domain.entity.Condition;
import com.jozias.product.catalog.domain.entity.Product;
import com.jozias.product.catalog.domain.entity.Seller;
import com.jozias.product.catalog.infrastructure.gateway.SaveProductGatewayImpl;
import com.jozias.product.catalog.infrastructure.persistence.entity.ProductEntity;
import com.jozias.product.catalog.infrastructure.persistence.entity.SellerEntity;
import com.jozias.product.catalog.infrastructure.persistence.mapper.ProductEntityMapper;
import com.jozias.product.catalog.infrastructure.persistence.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("SaveProductGatewayImpl")
class SaveProductGatewayImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductEntityMapper productEntityMapper;

    @InjectMocks
    private SaveProductGatewayImpl saveProductGateway;

    private ProductEntity productEntity;
    private Product product;
    private Seller seller;

    @BeforeEach
    void setUp() {
        seller = new Seller("Tech Store", "Best tech products", 95);
        seller.setId(1L);

        product = new Product(
                null,
                "Smartphone",
                "A great smartphone with amazing features",
                new BigDecimal("999.99"),
                10,
                Condition.NEW,
                "Electronics",
                new ArrayList<>(),
                seller);

        SellerEntity sellerEntity = new SellerEntity();
        sellerEntity.setId(1L);
        sellerEntity.setName("Tech Store");
        sellerEntity.setDescription("Best tech products");
        sellerEntity.setScore(95);

        productEntity = new ProductEntity();
        productEntity.setId(1L);
        productEntity.setName("Smartphone");
        productEntity.setDescription("A great smartphone");
        productEntity.setPrice(new BigDecimal("999.99"));
        productEntity.setAvailableQuantity(10);
        productEntity.setCondition(Condition.NEW);
        productEntity.setCategory("Electronics");
        productEntity.setSeller(sellerEntity);
        productEntity.setVariants(new ArrayList<>());
    }

    @Test
    @DisplayName("given product when save then should save and return mapped product")
    void givenProduct_whenSave_thenShouldSaveAndReturnMappedProduct() {
        // given
        Product savedProduct = new Product(
                1L,
                "Smartphone",
                "A great smartphone with amazing features",
                new BigDecimal("999.99"),
                10,
                Condition.NEW,
                "Electronics",
                new ArrayList<>(),
                seller);

        when(productEntityMapper.toEntity(product)).thenReturn(productEntity);
        when(productRepository.save(productEntity)).thenReturn(productEntity);
        when(productEntityMapper.toDomain(productEntity)).thenReturn(savedProduct);

        // when
        Product result = saveProductGateway.save(product);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(productEntityMapper).toEntity(product);
        verify(productRepository).save(productEntity);
        verify(productEntityMapper).toDomain(productEntity);
    }
}
