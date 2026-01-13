package com.jozias.product.catalog.infrastructure.persistence.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.jozias.product.catalog.domain.entity.Condition;
import com.jozias.product.catalog.domain.entity.Product;
import com.jozias.product.catalog.domain.entity.ProductVariant;
import com.jozias.product.catalog.domain.entity.ProductVariantValue;
import com.jozias.product.catalog.domain.entity.Seller;
import com.jozias.product.catalog.infrastructure.persistence.entity.ProductEntity;
import com.jozias.product.catalog.infrastructure.persistence.entity.ProductVariantEntity;
import com.jozias.product.catalog.infrastructure.persistence.entity.ProductVariantValueEntity;
import com.jozias.product.catalog.infrastructure.persistence.entity.SellerEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Persistence Mappers Test")
class PersistenceMappersTest {

    private ProductEntityMapperImpl productMapper;
    private ProductVariantEntityMapperImpl variantMapper;
    private ProductVariantValueEntityMapperImpl valueMapper;
    private SellerEntityMapperImpl sellerMapper;

    @BeforeEach
    void setUp() {
        valueMapper = new ProductVariantValueEntityMapperImpl();
        sellerMapper = new SellerEntityMapperImpl();

        variantMapper = new ProductVariantEntityMapperImpl();
        ReflectionTestUtils.setField(variantMapper, "productVariantValueEntityMapper", valueMapper);

        productMapper = new ProductEntityMapperImpl();
        ReflectionTestUtils.setField(productMapper, "sellerEntityMapper", sellerMapper);
        ReflectionTestUtils.setField(productMapper, "productVariantEntityMapper", variantMapper);
    }

    @Test
    @DisplayName("given SellerEntity when toDomain then should map to Seller")
    void givenSellerEntity_whenToDomain_thenShouldMapToSeller() {
        // given
        SellerEntity entity = new SellerEntity();
        entity.setId(1L);
        entity.setName("Tech Store");
        entity.setDescription("Best tech products");
        entity.setScore(95);

        // when
        Seller domain = sellerMapper.toDomain(entity);

        // then
        assertThat(domain.getId()).isEqualTo(1L);
        assertThat(domain.getName()).isEqualTo("Tech Store");
        assertThat(domain.getDescription()).isEqualTo("Best tech products");
        assertThat(domain.getScore()).isEqualTo(95);
    }

    @Test
    @DisplayName("given Seller when toEntity then should map to SellerEntity")
    void givenSeller_whenToEntity_thenShouldMapToSellerEntity() {
        // given
        Seller domain = new Seller("Tech Store", "Best tech products", 95);
        domain.setId(1L);

        // when
        SellerEntity entity = sellerMapper.toEntity(domain);

        // then
        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getName()).isEqualTo("Tech Store");
        assertThat(entity.getDescription()).isEqualTo("Best tech products");
        assertThat(entity.getScore()).isEqualTo(95);
    }

    @Test
    @DisplayName("given ProductVariantEntity when toDomain then should map to ProductVariant")
    void givenProductVariantEntity_whenToDomain_thenShouldMapToProductVariant() {
        // given
        ProductVariantEntity entity = new ProductVariantEntity();
        entity.setId(1L);
        entity.setType("Color");

        ProductVariantValueEntity valueEntity = new ProductVariantValueEntity();
        valueEntity.setId(1L);
        valueEntity.setValue("Red");
        entity.setValues(List.of(valueEntity));

        // when
        ProductVariant domain = variantMapper.toDomain(entity);

        // then
        assertThat(domain.getId()).isEqualTo(1L);
        assertThat(domain.getType()).isEqualTo("Color");
        assertThat(domain.getValues()).hasSize(1);
        assertThat(domain.getValues().get(0).getValue()).isEqualTo("Red");
    }

    @Test
    @DisplayName("given ProductEntity when toDomain then should map to Product")
    void givenProductEntity_whenToDomain_thenShouldMapToProduct() {
        // given
        SellerEntity sellerEntity = new SellerEntity();
        sellerEntity.setId(1L);
        sellerEntity.setName("Store");

        ProductEntity entity = new ProductEntity();
        entity.setId(1L);
        entity.setName("Smartphone");
        entity.setDescription("Detailed description here");
        entity.setPrice(new BigDecimal("999.99"));
        entity.setAvailableQuantity(10);
        entity.setCondition(Condition.NEW);
        entity.setCategory("Electronics");
        entity.setSeller(sellerEntity);
        entity.setVariants(new ArrayList<>());

        // when
        Product domain = productMapper.toDomain(entity);

        // then
        assertThat(domain.getId()).isEqualTo(1L);
        assertThat(domain.getName()).isEqualTo("Smartphone");
        assertThat(domain.getSeller().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("given Product when toEntity then should map to ProductEntity and link variants")
    void givenProduct_whenToEntity_thenShouldMapToProductEntityAndLinkVariants() {
        // given
        Seller seller = new Seller("Store", "Desc", 90);
        seller.setId(1L);

        Product domain = new Product(
                1L, "Smartphone", "A very long description for the test",
                new BigDecimal("999.99"), 10, Condition.NEW, "Electronics",
                new ArrayList<>(), seller);

        ProductVariant variant = new ProductVariant(null, "Color", List.of(new ProductVariantValue("Red")));
        domain.addVariant(variant);

        // when
        ProductEntity entity = productMapper.toEntity(domain);

        // then
        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getVariants()).hasSize(1);
        assertThat(entity.getVariants().get(0).getProduct()).isEqualTo(entity); // Verified linkVariants worked
    }
}
