package com.jozias.product.catalog.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jozias.product.catalog.domain.entity.ProductVariantValue;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ProductVariantValue")
class ProductVariantValueTest {

    @Test
    @DisplayName("given id and value when created then should have correct values")
    void givenIdAndValue_whenCreated_thenShouldHaveCorrectValues() {
        // given
        Long id = 1L;
        String value = "Red";

        // when
        ProductVariantValue variantValue = new ProductVariantValue(id, value);

        // then
        assertThat(variantValue.getId()).isEqualTo(id);
        assertThat(variantValue.getValue()).isEqualTo(value);
    }

    @Test
    @DisplayName("given only value when created then should have null id")
    void givenOnlyValue_whenCreated_thenShouldHaveNullId() {
        // given
        String value = "Blue";

        // when
        ProductVariantValue variantValue = new ProductVariantValue(value);

        // then
        assertThat(variantValue.getId()).isNull();
        assertThat(variantValue.getValue()).isEqualTo(value);
    }

    @Test
    @DisplayName("given no args when created then should have null values")
    void givenNoArgs_whenCreated_thenShouldHaveNullValues() {
        // when
        ProductVariantValue variantValue = new ProductVariantValue();

        // then
        assertThat(variantValue.getId()).isNull();
        assertThat(variantValue.getValue()).isNull();
    }

    @Test
    @DisplayName("given new value when update then should update value")
    void givenNewValue_whenUpdate_thenShouldUpdateValue() {
        // given
        ProductVariantValue variantValue = new ProductVariantValue(1L, "Red");
        String newValue = "Blue";

        // when
        variantValue.update(newValue);

        // then
        assertThat(variantValue.getValue()).isEqualTo(newValue);
    }

    @Test
    @DisplayName("given same value when equals then should return true")
    void givenSameValue_whenEquals_thenShouldReturnTrue() {
        // given
        ProductVariantValue value1 = new ProductVariantValue(1L, "Red");
        ProductVariantValue value2 = new ProductVariantValue(2L, "Red");

        // when & then
        assertThat(value1).isEqualTo(value2);
    }

    @Test
    @DisplayName("given different value when equals then should return false")
    void givenDifferentValue_whenEquals_thenShouldReturnFalse() {
        // given
        ProductVariantValue value1 = new ProductVariantValue(1L, "Red");
        ProductVariantValue value2 = new ProductVariantValue(1L, "Blue");

        // when & then
        assertThat(value1).isNotEqualTo(value2);
    }

    @Test
    @DisplayName("given null value when equals with null then should return true")
    void givenNullValue_whenEqualsWithNull_thenShouldReturnTrue() {
        // given
        ProductVariantValue value1 = new ProductVariantValue(1L, null);
        ProductVariantValue value2 = new ProductVariantValue(2L, null);

        // when & then
        assertThat(value1).isEqualTo(value2);
    }

    @Test
    @DisplayName("given same object when equals then should return true")
    void givenSameObject_whenEquals_thenShouldReturnTrue() {
        // given
        ProductVariantValue value = new ProductVariantValue(1L, "Red");

        // when & then
        assertThat(value).isEqualTo(value);
    }

    @Test
    @DisplayName("given null when equals then should return false")
    void givenNull_whenEquals_thenShouldReturnFalse() {
        // given
        ProductVariantValue value = new ProductVariantValue(1L, "Red");

        // when & then
        assertThat(value).isNotEqualTo(null);
    }

    @Test
    @DisplayName("given different class when equals then should return false")
    void givenDifferentClass_whenEquals_thenShouldReturnFalse() {
        // given
        ProductVariantValue value = new ProductVariantValue(1L, "Red");

        // when & then
        assertThat(value).isNotEqualTo("Red");
    }

    @Test
    @DisplayName("given same value when hashCode then should be equal")
    void givenSameValue_whenHashCode_thenShouldBeEqual() {
        // given
        ProductVariantValue value1 = new ProductVariantValue(1L, "Red");
        ProductVariantValue value2 = new ProductVariantValue(2L, "Red");

        // when & then
        assertThat(value1.hashCode()).isEqualTo(value2.hashCode());
    }

    @Test
    @DisplayName("given null value when hashCode then should return zero")
    void givenNullValue_whenHashCode_thenShouldReturnZero() {
        // given
        ProductVariantValue value = new ProductVariantValue(1L, null);

        // when & then
        assertThat(value.hashCode()).isZero();
    }

    @Test
    @DisplayName("given value when toString then should return formatted string")
    void givenValue_whenToString_thenShouldReturnFormattedString() {
        // given
        ProductVariantValue value = new ProductVariantValue(1L, "Red");

        // when
        String result = value.toString();

        // then
        assertThat(result).contains("ProductVariantValue");
        assertThat(result).contains("id=1");
        assertThat(result).contains("value='Red'");
    }

    @Test
    @DisplayName("given value when setId then should update id")
    void givenValue_whenSetId_thenShouldUpdateId() {
        // given
        ProductVariantValue value = new ProductVariantValue("Red");
        Long newId = 5L;

        // when
        value.setId(newId);

        // then
        assertThat(value.getId()).isEqualTo(newId);
    }

    @Test
    @DisplayName("given value when setValue then should update value")
    void givenValue_whenSetValue_thenShouldUpdateValue() {
        // given
        ProductVariantValue variantValue = new ProductVariantValue(1L, "Red");
        String newValue = "Green";

        // when
        variantValue.setValue(newValue);

        // then
        assertThat(variantValue.getValue()).isEqualTo(newValue);
    }
}
