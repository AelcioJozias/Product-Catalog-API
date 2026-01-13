package com.jozias.product.catalog.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.jozias.product.catalog.domain.entity.ProductVariant;
import com.jozias.product.catalog.domain.entity.ProductVariantValue;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ProductVariant")
class ProductVariantTest {

    @Test
    @DisplayName("given valid data when created then should have correct values")
    void givenValidData_whenCreated_thenShouldHaveCorrectValues() {
        // given
        Long id = 1L;
        String type = "Color";
        List<ProductVariantValue> values = List.of(new ProductVariantValue(1L, "Red"));

        // when
        ProductVariant variant = new ProductVariant(id, type, values);

        // then
        assertThat(variant.getId()).isEqualTo(id);
        assertThat(variant.getType()).isEqualTo(type);
        assertThat(variant.getValues()).hasSize(1);
    }

    @Test
    @DisplayName("given variant when setId then should update id")
    void givenVariant_whenSetId_thenShouldUpdateId() {
        // given
        ProductVariant variant = new ProductVariant(1L, "Color", new ArrayList<>());
        Long newId = 5L;

        // when
        variant.setId(newId);

        // then
        assertThat(variant.getId()).isEqualTo(newId);
    }

    @Test
    @DisplayName("given variant when setType then should update type")
    void givenVariant_whenSetType_thenShouldUpdateType() {
        // given
        ProductVariant variant = new ProductVariant(1L, "Color", new ArrayList<>());
        String newType = "Size";

        // when
        variant.setType(newType);

        // then
        assertThat(variant.getType()).isEqualTo(newType);
    }

    @Test
    @DisplayName("given variant when setValues then should update values")
    void givenVariant_whenSetValues_thenShouldUpdateValues() {
        // given
        ProductVariant variant = new ProductVariant(1L, "Color", new ArrayList<>());
        List<ProductVariantValue> newValues = List.of(new ProductVariantValue(1L, "Red"));

        // when
        variant.setValues(newValues);

        // then
        assertThat(variant.getValues()).hasSize(1);
    }

    @Nested
    @DisplayName("update method")
    class UpdateMethod {

        @Test
        @DisplayName("given new type and values when update then should update both")
        void givenNewTypeAndValues_whenUpdate_thenShouldUpdateBoth() {
            // given
            List<ProductVariantValue> initialValues = new ArrayList<>();
            initialValues.add(new ProductVariantValue(1L, "Red"));
            ProductVariant variant = new ProductVariant(1L, "Color", initialValues);

            String newType = "Size";
            // Using null ID to add new value (values with ID only update existing ones)
            List<ProductVariantValue> newValues = List.of(new ProductVariantValue(null, "Large"));

            // when
            variant.update(newType, newValues);

            // then
            assertThat(variant.getType()).isEqualTo(newType);
            assertThat(variant.getValues()).hasSize(1);
            assertThat(variant.getValues().get(0).getValue()).isEqualTo("Large");
        }

        @Test
        @DisplayName("given null values when update then should clear values")
        void givenNullValues_whenUpdate_thenShouldClearValues() {
            // given
            List<ProductVariantValue> initialValues = new ArrayList<>();
            initialValues.add(new ProductVariantValue(1L, "Red"));
            ProductVariant variant = new ProductVariant(1L, "Color", initialValues);

            // when
            variant.update("Color", null);

            // then
            assertThat(variant.getValues()).isEmpty();
        }

        @Test
        @DisplayName("given empty values when update then should clear values")
        void givenEmptyValues_whenUpdate_thenShouldClearValues() {
            // given
            List<ProductVariantValue> initialValues = new ArrayList<>();
            initialValues.add(new ProductVariantValue(1L, "Red"));
            ProductVariant variant = new ProductVariant(1L, "Color", initialValues);

            // when
            variant.update("Color", new ArrayList<>());

            // then
            assertThat(variant.getValues()).isEmpty();
        }

        @Test
        @DisplayName("given new value without id when update then should add value")
        void givenNewValueWithoutId_whenUpdate_thenShouldAddValue() {
            // given
            List<ProductVariantValue> initialValues = new ArrayList<>();
            ProductVariant variant = new ProductVariant(1L, "Color", initialValues);

            List<ProductVariantValue> newValues = List.of(new ProductVariantValue(null, "Green"));

            // when
            variant.update("Color", newValues);

            // then
            assertThat(variant.getValues()).hasSize(1);
            assertThat(variant.getValues().get(0).getValue()).isEqualTo("Green");
        }

        @Test
        @DisplayName("given existing value with matching id when update then should update value")
        void givenExistingValueWithMatchingId_whenUpdate_thenShouldUpdateValue() {
            // given
            List<ProductVariantValue> initialValues = new ArrayList<>();
            initialValues.add(new ProductVariantValue(1L, "Red"));
            ProductVariant variant = new ProductVariant(1L, "Color", initialValues);

            List<ProductVariantValue> incomingValues = List.of(new ProductVariantValue(1L, "Blue"));

            // when
            variant.update("Color", incomingValues);

            // then
            assertThat(variant.getValues()).hasSize(1);
            assertThat(variant.getValues().get(0).getValue()).isEqualTo("Blue");
        }

        @Test
        @DisplayName("given value not in incoming list when update then should remove value")
        void givenValueNotInIncomingList_whenUpdate_thenShouldRemoveValue() {
            // given
            List<ProductVariantValue> initialValues = new ArrayList<>();
            initialValues.add(new ProductVariantValue(1L, "Red"));
            initialValues.add(new ProductVariantValue(2L, "Blue"));
            ProductVariant variant = new ProductVariant(1L, "Color", initialValues);

            List<ProductVariantValue> incomingValues = List.of(new ProductVariantValue(1L, "Red"));

            // when
            variant.update("Color", incomingValues);

            // then
            assertThat(variant.getValues()).hasSize(1);
            assertThat(variant.getValues().get(0).getId()).isEqualTo(1L);
        }
    }
}
