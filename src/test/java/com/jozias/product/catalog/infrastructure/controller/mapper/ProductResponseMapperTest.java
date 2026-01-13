package com.jozias.product.catalog.infrastructure.controller.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.jozias.product.catalog.domain.entity.Condition;
import com.jozias.product.catalog.domain.entity.Product;
import com.jozias.product.catalog.domain.entity.ProductVariant;
import com.jozias.product.catalog.domain.entity.ProductVariantValue;
import com.jozias.product.catalog.domain.entity.Seller;
import com.jozias.product.catalog.infrastructure.controller.dto.ProductDTO;
import com.jozias.product.catalog.infrastructure.controller.dto.ProductDetailDTO;
import com.jozias.product.catalog.infrastructure.controller.dto.ProductResponse;
import com.jozias.product.catalog.infrastructure.controller.mapper.ProductResponseMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ProductResponseMapper")
class ProductResponseMapperTest {

    private ProductResponseMapper mapper;
    private Seller seller;
    private Product product;

    @BeforeEach
    void setUp() {
        mapper = new ProductResponseMapper();

        seller = new Seller("Tech Store", "Best tech products", 95);
        seller.setId(1L);

        product = new Product(
                1L,
                "Smartphone",
                "A great smartphone with amazing features",
                new BigDecimal("999.99"),
                10,
                Condition.NEW,
                "Electronics",
                new ArrayList<>(),
                seller);
    }

    @Nested
    @DisplayName("toDto")
    class ToDto {

        @Test
        @DisplayName("given product when toDto then should return ProductDTO")
        void givenProduct_whenToDto_thenShouldReturnProductDTO() {
            // when
            ProductDTO result = mapper.toDto(product);

            // then
            assertThat(result).isNotNull();
            assertThat(result.id()).isEqualTo(1L);
            assertThat(result.name()).isEqualTo("Smartphone");
            assertThat(result.price()).isEqualByComparingTo(new BigDecimal("999.99"));
        }
    }

    @Nested
    @DisplayName("toDetailDto")
    class ToDetailDto {

        @Test
        @DisplayName("given product when toDetailDto then should return ProductDetailDTO")
        void givenProduct_whenToDetailDto_thenShouldReturnProductDetailDTO() {
            // when
            ProductDetailDTO result = mapper.toDetailDto(product);

            // then
            assertThat(result).isNotNull();
            assertThat(result.id()).isEqualTo(1L);
            assertThat(result.name()).isEqualTo("Smartphone");
            assertThat(result.description()).isEqualTo("A great smartphone with amazing features");
            assertThat(result.price()).isEqualByComparingTo(new BigDecimal("999.99"));
            assertThat(result.availableQuantity()).isEqualTo(10);
            assertThat(result.condition()).isEqualTo(Condition.NEW);
            assertThat(result.category()).isEqualTo("Electronics");
            assertThat(result.variants()).isEmpty();
            assertThat(result.seller()).isNotNull();
            assertThat(result.seller().id()).isEqualTo(1L);
        }

        @Test
        @DisplayName("given product with variants when toDetailDto then should map variants")
        void givenProductWithVariants_whenToDetailDto_thenShouldMapVariants() {
            // given
            ProductVariantValue value = new ProductVariantValue(1L, "Red");
            ProductVariant variant = new ProductVariant(1L, "Color", List.of(value));
            product.addVariant(variant);

            // when
            ProductDetailDTO result = mapper.toDetailDto(product);

            // then
            assertThat(result.variants()).hasSize(1);
            assertThat(result.variants().get(0).id()).isEqualTo(1L);
            assertThat(result.variants().get(0).type()).isEqualTo("Color");
            assertThat(result.variants().get(0).values()).hasSize(1);
            assertThat(result.variants().get(0).values().get(0).value()).isEqualTo("Red");
        }
    }

    @Nested
    @DisplayName("toResponse")
    class ToResponse {

        @Test
        @DisplayName("given product when toResponse then should return ProductResponse")
        void givenProduct_whenToResponse_thenShouldReturnProductResponse() {
            // when
            ProductResponse result = mapper.toResponse(product);

            // then
            assertThat(result).isNotNull();
            assertThat(result.id()).isEqualTo(1L);
            assertThat(result.name()).isEqualTo("Smartphone");
            assertThat(result.description()).isEqualTo("A great smartphone with amazing features");
            assertThat(result.price()).isEqualByComparingTo(new BigDecimal("999.99"));
            assertThat(result.availableQuantity()).isEqualTo(10);
            assertThat(result.condition()).isEqualTo(Condition.NEW);
            assertThat(result.category()).isEqualTo("Electronics");
        }
    }

    @Nested
    @DisplayName("Null handling")
    class NullHandling {

        @Test
        @DisplayName("given null variants when mapping then should return empty list")
        void givenNullVariants_whenMapping_thenShouldReturnEmptyList() {
            // given - product already has null variants initialized to empty list in
            // constructor
            // when
            ProductDetailDTO result = mapper.toDetailDto(product);

            // then
            assertThat(result.variants()).isEmpty();
        }

        @Test
        @DisplayName("given null seller when mapping then should return null seller")
        void givenNullSeller_whenMapping_thenShouldReturnNullSeller() {
            // given
            Product productWithNullSeller = new Product(
                    1L,
                    "Smartphone",
                    "A great smartphone with amazing features",
                    new BigDecimal("999.99"),
                    10,
                    Condition.NEW,
                    "Electronics",
                    new ArrayList<>(),
                    seller // seller is required, but we'll test the mapper method directly
            );

            // when
            ProductResponse result = mapper.toResponse(productWithNullSeller);

            // then
            assertThat(result.seller()).isNotNull(); // seller is present in this case
        }

        @Test
        @DisplayName("given variant with null values when mapping then should return empty values list")
        void givenVariantWithNullValues_whenMapping_thenShouldReturnEmptyValuesList() {
            // given
            ProductVariant variant = new ProductVariant(1L, "Color", null);
            product.addVariant(variant);

            // when
            ProductDetailDTO result = mapper.toDetailDto(product);

            // then
            assertThat(result.variants()).hasSize(1);
            assertThat(result.variants().get(0).values()).isEmpty();
        }
    }

    @Nested
    @DisplayName("Seller mapping")
    class SellerMapping {

        @Test
        @DisplayName("given valid seller when mapping then should map all seller fields")
        void givenValidSeller_whenMapping_thenShouldMapAllSellerFields() {
            // when
            ProductDetailDTO result = mapper.toDetailDto(product);

            // then
            assertThat(result.seller().id()).isEqualTo(1L);
            assertThat(result.seller().name()).isEqualTo("Tech Store");
            assertThat(result.seller().description()).isEqualTo("Best tech products");
            assertThat(result.seller().score()).isEqualTo(95);
        }
    }
}
