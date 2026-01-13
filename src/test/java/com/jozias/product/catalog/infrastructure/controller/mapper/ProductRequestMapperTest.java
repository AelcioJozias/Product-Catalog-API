package com.jozias.product.catalog.infrastructure.controller.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.jozias.product.catalog.domain.entity.ProductVariant;
import com.jozias.product.catalog.domain.entity.ProductVariantValue;
import com.jozias.product.catalog.infrastructure.controller.dto.CreateProductVariantRequest;
import com.jozias.product.catalog.infrastructure.controller.dto.CreateProductVariantValueRequest;
import com.jozias.product.catalog.infrastructure.controller.dto.UpdateProductVariantRequest;
import com.jozias.product.catalog.infrastructure.controller.dto.UpdateProductVariantValueRequest;
import com.jozias.product.catalog.infrastructure.controller.mapper.ProductRequestMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ProductRequestMapper")
class ProductRequestMapperTest {

    private ProductRequestMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ProductRequestMapper();
    }

    @Nested
    @DisplayName("toCreateDomainVariants")
    class ToCreateDomainVariants {

        @Test
        @DisplayName("given null variants when toCreateDomainVariants then should return empty list")
        void givenNullVariants_whenToCreateDomainVariants_thenShouldReturnEmptyList() {
            // when
            List<ProductVariant> result = mapper.toCreateDomainVariants(null);

            // then
            assertThat(result).isNotNull().isEmpty();
        }

        @Test
        @DisplayName("given empty variants when toCreateDomainVariants then should return empty list")
        void givenEmptyVariants_whenToCreateDomainVariants_thenShouldReturnEmptyList() {
            // when
            List<ProductVariant> result = mapper.toCreateDomainVariants(List.of());

            // then
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("given variant requests when toCreateDomainVariants then should return mapped variants without ids")
        void givenVariantRequests_whenToCreateDomainVariants_thenShouldReturnMappedVariantsWithoutIds() {
            // given
            CreateProductVariantValueRequest valueRequest = new CreateProductVariantValueRequest("Red");
            CreateProductVariantRequest variantRequest = new CreateProductVariantRequest("Color",
                    List.of(valueRequest));
            List<CreateProductVariantRequest> requests = List.of(variantRequest);

            // when
            List<ProductVariant> result = mapper.toCreateDomainVariants(requests);

            // then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).getId()).isNull();
            assertThat(result.get(0).getType()).isEqualTo("Color");
            assertThat(result.get(0).getValues()).hasSize(1);
            assertThat(result.get(0).getValues().get(0).getValue()).isEqualTo("Red");
        }

        @Test
        @DisplayName("given variant with null values when toCreateDomainVariants then should have empty values list")
        void givenVariantWithNullValues_whenToCreateDomainVariants_thenShouldHaveEmptyValuesList() {
            // given
            CreateProductVariantRequest variantRequest = new CreateProductVariantRequest("Color", null);
            List<CreateProductVariantRequest> requests = List.of(variantRequest);

            // when
            List<ProductVariant> result = mapper.toCreateDomainVariants(requests);

            // then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).getValues()).isEmpty();
        }
    }

    @Nested
    @DisplayName("toUpdateDomainVariants")
    class ToUpdateDomainVariants {

        @Test
        @DisplayName("given null variants when toUpdateDomainVariants then should return empty list")
        void givenNullVariants_whenToUpdateDomainVariants_thenShouldReturnEmptyList() {
            // when
            List<ProductVariant> result = mapper.toUpdateDomainVariants(null);

            // then
            assertThat(result).isNotNull().isEmpty();
        }

        @Test
        @DisplayName("given variant requests when toUpdateDomainVariants then should return mapped variants with ids")
        void givenVariantRequests_whenToUpdateDomainVariants_thenShouldReturnMappedVariantsWithIds() {
            // given
            UpdateProductVariantValueRequest valueRequest = new UpdateProductVariantValueRequest(5L, "Blue");
            UpdateProductVariantRequest variantRequest = new UpdateProductVariantRequest(1L, "Color",
                    List.of(valueRequest));
            List<UpdateProductVariantRequest> requests = List.of(variantRequest);

            // when
            List<ProductVariant> result = mapper.toUpdateDomainVariants(requests);

            // then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).getId()).isEqualTo(1L);
            assertThat(result.get(0).getType()).isEqualTo("Color");
            assertThat(result.get(0).getValues()).hasSize(1);
            ProductVariantValue value = result.get(0).getValues().get(0);
            assertThat(value.getId()).isEqualTo(5L);
            assertThat(value.getValue()).isEqualTo("Blue");
        }

        @Test
        @DisplayName("given variant value without id when toUpdateDomainVariants then should map without id")
        void givenVariantValueWithoutId_whenToUpdateDomainVariants_thenShouldMapWithoutId() {
            // given
            UpdateProductVariantValueRequest valueRequest = new UpdateProductVariantValueRequest(null, "Green");
            UpdateProductVariantRequest variantRequest = new UpdateProductVariantRequest(1L, "Color",
                    List.of(valueRequest));
            List<UpdateProductVariantRequest> requests = List.of(variantRequest);

            // when
            List<ProductVariant> result = mapper.toUpdateDomainVariants(requests);

            // then
            ProductVariantValue value = result.get(0).getValues().get(0);
            assertThat(value.getId()).isNull();
            assertThat(value.getValue()).isEqualTo("Green");
        }

        @Test
        @DisplayName("given variant with null values when toUpdateDomainVariants then should have empty values list")
        void givenVariantWithNullValues_whenToUpdateDomainVariants_thenShouldHaveEmptyValuesList() {
            // given
            UpdateProductVariantRequest variantRequest = new UpdateProductVariantRequest(1L, "Color", null);
            List<UpdateProductVariantRequest> requests = List.of(variantRequest);

            // when
            List<ProductVariant> result = mapper.toUpdateDomainVariants(requests);

            // then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).getValues()).isEmpty();
        }

        @Test
        @DisplayName("given multiple variant values when toUpdateDomainVariants then should map all values")
        void givenMultipleVariantValues_whenToUpdateDomainVariants_thenShouldMapAllValues() {
            // given
            UpdateProductVariantValueRequest value1 = new UpdateProductVariantValueRequest(1L, "Red");
            UpdateProductVariantValueRequest value2 = new UpdateProductVariantValueRequest(null, "Blue");
            UpdateProductVariantRequest variantRequest = new UpdateProductVariantRequest(1L, "Color",
                    List.of(value1, value2));
            List<UpdateProductVariantRequest> requests = List.of(variantRequest);

            // when
            List<ProductVariant> result = mapper.toUpdateDomainVariants(requests);

            // then
            assertThat(result.get(0).getValues()).hasSize(2);
        }
    }
}
