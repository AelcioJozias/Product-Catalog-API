package com.jozias.product.catalog.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.jozias.product.catalog.domain.entity.Condition;
import com.jozias.product.catalog.domain.entity.Product;
import com.jozias.product.catalog.domain.entity.ProductVariant;
import com.jozias.product.catalog.domain.entity.Seller;
import com.jozias.product.catalog.domain.exception.ProductInstanceInvalidException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Product")
class ProductTest {

    private Seller validSeller;

    @BeforeEach
    void setUp() {
        validSeller = new Seller("Tech Store", "Best tech products", 95);
        validSeller.setId(1L);
    }

    private Product createValidProduct() {
        return new Product(
                1L,
                "Smartphone",
                "A great smartphone with amazing features",
                new BigDecimal("999.99"),
                10,
                Condition.NEW,
                "Electronics",
                new ArrayList<>(),
                validSeller);
    }

    @Nested
    @DisplayName("Creation")
    class Creation {

        @Test
        @DisplayName("given valid data when created then should have correct values")
        void givenValidData_whenCreated_thenShouldHaveCorrectValues() {
            // given & when
            Product product = createValidProduct();

            // then
            assertThat(product.getId()).isEqualTo(1L);
            assertThat(product.getName()).isEqualTo("Smartphone");
            assertThat(product.getDescription()).isEqualTo("A great smartphone with amazing features");
            assertThat(product.getPrice()).isEqualByComparingTo(new BigDecimal("999.99"));
            assertThat(product.getAvailableQuantity()).isEqualTo(10);
            assertThat(product.getCondition()).isEqualTo(Condition.NEW);
            assertThat(product.getCategory()).isEqualTo("Electronics");
            assertThat(product.getVariants()).isEmpty();
            assertThat(product.getSeller()).isEqualTo(validSeller);
        }

        @Test
        @DisplayName("given null variants when created then should initialize empty list")
        void givenNullVariants_whenCreated_thenShouldInitializeEmptyList() {
            // when
            Product product = new Product(
                    1L, "Smartphone", "A great smartphone with amazing features",
                    new BigDecimal("999.99"), 10, Condition.NEW, "Electronics",
                    null, validSeller);

            // then
            assertThat(product.getVariants()).isNotNull().isEmpty();
        }

        @Test
        @DisplayName("given null id when created then should allow null id")
        void givenNullId_whenCreated_thenShouldAllowNullId() {
            // when
            Product product = new Product(
                    null, "Smartphone", "A great smartphone with amazing features",
                    new BigDecimal("999.99"), 10, Condition.NEW, "Electronics",
                    new ArrayList<>(), validSeller);

            // then
            assertThat(product.getId()).isNull();
        }
    }

    @Nested
    @DisplayName("Validation")
    class Validation {

        @Test
        @DisplayName("given null name when created then should throw ProductInstanceInvalidException")
        void givenNullName_whenCreated_thenShouldThrowProductInstanceInvalidException() {
            assertThatThrownBy(() -> new Product(
                    1L, null, "A great smartphone with amazing features",
                    new BigDecimal("999.99"), 10, Condition.NEW, "Electronics",
                    new ArrayList<>(), validSeller))
                    .isInstanceOf(ProductInstanceInvalidException.class)
                    .hasMessageContaining("name");
        }

        @Test
        @DisplayName("given short name when created then should throw ProductInstanceInvalidException")
        void givenShortName_whenCreated_thenShouldThrowProductInstanceInvalidException() {
            assertThatThrownBy(() -> new Product(
                    1L, "ab", "A great smartphone with amazing features",
                    new BigDecimal("999.99"), 10, Condition.NEW, "Electronics",
                    new ArrayList<>(), validSeller))
                    .isInstanceOf(ProductInstanceInvalidException.class)
                    .hasMessageContaining("name");
        }

        @Test
        @DisplayName("given null description when created then should throw ProductInstanceInvalidException")
        void givenNullDescription_whenCreated_thenShouldThrowProductInstanceInvalidException() {
            assertThatThrownBy(() -> new Product(
                    1L, "Smartphone", null,
                    new BigDecimal("999.99"), 10, Condition.NEW, "Electronics",
                    new ArrayList<>(), validSeller))
                    .isInstanceOf(ProductInstanceInvalidException.class)
                    .hasMessageContaining("description");
        }

        @Test
        @DisplayName("given short description when created then should throw ProductInstanceInvalidException")
        void givenShortDescription_whenCreated_thenShouldThrowProductInstanceInvalidException() {
            assertThatThrownBy(() -> new Product(
                    1L, "Smartphone", "Short",
                    new BigDecimal("999.99"), 10, Condition.NEW, "Electronics",
                    new ArrayList<>(), validSeller))
                    .isInstanceOf(ProductInstanceInvalidException.class)
                    .hasMessageContaining("description");
        }

        @Test
        @DisplayName("given null price when created then should throw ProductInstanceInvalidException")
        void givenNullPrice_whenCreated_thenShouldThrowProductInstanceInvalidException() {
            assertThatThrownBy(() -> new Product(
                    1L, "Smartphone", "A great smartphone with amazing features",
                    null, 10, Condition.NEW, "Electronics",
                    new ArrayList<>(), validSeller))
                    .isInstanceOf(ProductInstanceInvalidException.class)
                    .hasMessageContaining("price");
        }

        @Test
        @DisplayName("given negative price when created then should throw ProductInstanceInvalidException")
        void givenNegativePrice_whenCreated_thenShouldThrowProductInstanceInvalidException() {
            assertThatThrownBy(() -> new Product(
                    1L, "Smartphone", "A great smartphone with amazing features",
                    new BigDecimal("-10.00"), 10, Condition.NEW, "Electronics",
                    new ArrayList<>(), validSeller))
                    .isInstanceOf(ProductInstanceInvalidException.class)
                    .hasMessageContaining("price");
        }

        @Test
        @DisplayName("given zero price when created then should allow zero price")
        void givenZeroPrice_whenCreated_thenShouldAllowZeroPrice() {
            // when
            Product product = new Product(
                    1L, "Smartphone", "A great smartphone with amazing features",
                    BigDecimal.ZERO, 10, Condition.NEW, "Electronics",
                    new ArrayList<>(), validSeller);

            // then
            assertThat(product.getPrice()).isEqualByComparingTo(BigDecimal.ZERO);
        }

        @Test
        @DisplayName("given null condition when created then should throw ProductInstanceInvalidException")
        void givenNullCondition_whenCreated_thenShouldThrowProductInstanceInvalidException() {
            assertThatThrownBy(() -> new Product(
                    1L, "Smartphone", "A great smartphone with amazing features",
                    new BigDecimal("999.99"), 10, null, "Electronics",
                    new ArrayList<>(), validSeller))
                    .isInstanceOf(ProductInstanceInvalidException.class)
                    .hasMessageContaining("condition");
        }

        @Test
        @DisplayName("given null category when created then should throw ProductInstanceInvalidException")
        void givenNullCategory_whenCreated_thenShouldThrowProductInstanceInvalidException() {
            assertThatThrownBy(() -> new Product(
                    1L, "Smartphone", "A great smartphone with amazing features",
                    new BigDecimal("999.99"), 10, Condition.NEW, null,
                    new ArrayList<>(), validSeller))
                    .isInstanceOf(ProductInstanceInvalidException.class)
                    .hasMessageContaining("category");
        }

        @Test
        @DisplayName("given short category when created then should throw ProductInstanceInvalidException")
        void givenShortCategory_whenCreated_thenShouldThrowProductInstanceInvalidException() {
            assertThatThrownBy(() -> new Product(
                    1L, "Smartphone", "A great smartphone with amazing features",
                    new BigDecimal("999.99"), 10, Condition.NEW, "ab",
                    new ArrayList<>(), validSeller))
                    .isInstanceOf(ProductInstanceInvalidException.class)
                    .hasMessageContaining("category");
        }

        @Test
        @DisplayName("given null seller when created then should throw ProductInstanceInvalidException")
        void givenNullSeller_whenCreated_thenShouldThrowProductInstanceInvalidException() {
            assertThatThrownBy(() -> new Product(
                    1L, "Smartphone", "A great smartphone with amazing features",
                    new BigDecimal("999.99"), 10, Condition.NEW, "Electronics",
                    new ArrayList<>(), null))
                    .isInstanceOf(ProductInstanceInvalidException.class)
                    .hasMessageContaining("seller");
        }
    }

    @Nested
    @DisplayName("Update")
    class Update {

        @Test
        @DisplayName("given valid data when update then should update all fields")
        void givenValidData_whenUpdate_thenShouldUpdateAllFields() {
            // given
            Product product = createValidProduct();

            // when
            product.update(
                    "Updated Phone",
                    "Updated description for the phone",
                    new BigDecimal("1299.99"),
                    5,
                    Condition.REFURBISHED,
                    "Mobile Devices");

            // then
            assertThat(product.getName()).isEqualTo("Updated Phone");
            assertThat(product.getDescription()).isEqualTo("Updated description for the phone");
            assertThat(product.getPrice()).isEqualByComparingTo(new BigDecimal("1299.99"));
            assertThat(product.getAvailableQuantity()).isEqualTo(5);
            assertThat(product.getCondition()).isEqualTo(Condition.REFURBISHED);
            assertThat(product.getCategory()).isEqualTo("Mobile Devices");
        }

        @Test
        @DisplayName("given invalid name when update then should throw ProductInstanceInvalidException")
        void givenInvalidName_whenUpdate_thenShouldThrowProductInstanceInvalidException() {
            // given
            Product product = createValidProduct();

            // when & then
            assertThatThrownBy(() -> product.update(
                    "ab",
                    "Updated description for the phone",
                    new BigDecimal("1299.99"),
                    5,
                    Condition.REFURBISHED,
                    "Mobile Devices"))
                    .isInstanceOf(ProductInstanceInvalidException.class)
                    .hasMessageContaining("name");
        }
    }

    @Nested
    @DisplayName("Variant Management")
    class VariantManagement {

        @Test
        @DisplayName("given variant when addVariant then should add to list")
        void givenVariant_whenAddVariant_thenShouldAddToList() {
            // given
            Product product = createValidProduct();
            ProductVariant variant = new ProductVariant(1L, "Color", new ArrayList<>());

            // when
            product.addVariant(variant);

            // then
            assertThat(product.getVariants()).hasSize(1);
            assertThat(product.getVariants().get(0)).isEqualTo(variant);
        }

        @Test
        @DisplayName("given null variant when addVariant then should not add")
        void givenNullVariant_whenAddVariant_thenShouldNotAdd() {
            // given
            Product product = createValidProduct();

            // when
            product.addVariant(null);

            // then
            assertThat(product.getVariants()).isEmpty();
        }

        @Test
        @DisplayName("given existing variant when removeVariant then should remove from list")
        void givenExistingVariant_whenRemoveVariant_thenShouldRemoveFromList() {
            // given
            Product product = createValidProduct();
            ProductVariant variant = new ProductVariant(1L, "Color", new ArrayList<>());
            product.addVariant(variant);

            // when
            product.removeVariant(variant);

            // then
            assertThat(product.getVariants()).isEmpty();
        }

        @Test
        @DisplayName("given null variant when removeVariant then should not throw")
        void givenNullVariant_whenRemoveVariant_thenShouldNotThrow() {
            // given
            Product product = createValidProduct();

            // when
            product.removeVariant(null);

            // then
            assertThat(product.getVariants()).isEmpty();
        }
    }

    @Nested
    @DisplayName("UpdateVariants")
    class UpdateVariants {

        @Test
        @DisplayName("given null variants when updateVariants then should clear variants")
        void givenNullVariants_whenUpdateVariants_thenShouldClearVariants() {
            // given
            Product product = createValidProduct();
            product.addVariant(new ProductVariant(1L, "Color", new ArrayList<>()));

            // when
            product.updateVariants(null);

            // then
            assertThat(product.getVariants()).isEmpty();
        }

        @Test
        @DisplayName("given empty variants when updateVariants then should clear variants")
        void givenEmptyVariants_whenUpdateVariants_thenShouldClearVariants() {
            // given
            Product product = createValidProduct();
            product.addVariant(new ProductVariant(1L, "Color", new ArrayList<>()));

            // when
            product.updateVariants(new ArrayList<>());

            // then
            assertThat(product.getVariants()).isEmpty();
        }

        @Test
        @DisplayName("given new variant without id when updateVariants then should add variant")
        void givenNewVariantWithoutId_whenUpdateVariants_thenShouldAddVariant() {
            // given
            Product product = createValidProduct();
            List<ProductVariant> newVariants = List.of(
                    new ProductVariant(null, "Size", new ArrayList<>()));

            // when
            product.updateVariants(newVariants);

            // then
            assertThat(product.getVariants()).hasSize(1);
            assertThat(product.getVariants().get(0).getType()).isEqualTo("Size");
        }

        @Test
        @DisplayName("given existing variant with id when updateVariants then should update variant")
        void givenExistingVariantWithId_whenUpdateVariants_thenShouldUpdateVariant() {
            // given
            Product product = createValidProduct();
            ProductVariant existingVariant = new ProductVariant(1L, "Color", new ArrayList<>());
            product.addVariant(existingVariant);

            List<ProductVariant> updatedVariants = List.of(
                    new ProductVariant(1L, "Updated Color", new ArrayList<>()));

            // when
            product.updateVariants(updatedVariants);

            // then
            assertThat(product.getVariants()).hasSize(1);
            assertThat(product.getVariants().get(0).getType()).isEqualTo("Updated Color");
        }

        @Test
        @DisplayName("given variant not in incoming list when updateVariants then should remove variant")
        void givenVariantNotInIncomingList_whenUpdateVariants_thenShouldRemoveVariant() {
            // given
            Product product = createValidProduct();
            product.addVariant(new ProductVariant(1L, "Color", new ArrayList<>()));
            product.addVariant(new ProductVariant(2L, "Size", new ArrayList<>()));

            List<ProductVariant> incomingVariants = List.of(
                    new ProductVariant(1L, "Color", new ArrayList<>()));

            // when
            product.updateVariants(incomingVariants);

            // then
            assertThat(product.getVariants()).hasSize(1);
            assertThat(product.getVariants().get(0).getId()).isEqualTo(1L);
        }
    }
}
