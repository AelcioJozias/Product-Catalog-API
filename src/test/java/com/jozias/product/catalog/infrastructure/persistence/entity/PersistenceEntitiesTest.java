package com.jozias.product.catalog.infrastructure.persistence.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jozias.product.catalog.domain.entity.Condition;
import com.jozias.product.catalog.infrastructure.persistence.entity.ProductEntity;
import com.jozias.product.catalog.infrastructure.persistence.entity.ProductVariantEntity;
import com.jozias.product.catalog.infrastructure.persistence.entity.ProductVariantValueEntity;
import com.jozias.product.catalog.infrastructure.persistence.entity.SellerEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Persistence Entities Test")
class PersistenceEntitiesTest {

    @Test
    @DisplayName("ProductEntity should have getters and setters working and equals/hashCode by id")
    void productEntity_shouldWork() {
        ProductEntity entity1 = new ProductEntity();
        entity1.setId(1L);
        entity1.setName("Smartphone");

        ProductEntity entity2 = new ProductEntity();
        entity2.setId(1L);
        entity2.setName("Different Name");

        // Equals should be by ID
        assertThat(entity1).isEqualTo(entity2);
        assertThat(entity1.hashCode()).isEqualTo(entity2.hashCode());

        entity1.setDescription("Desc");
        entity1.setPrice(BigDecimal.TEN);
        entity1.setAvailableQuantity(10);
        entity1.setCondition(Condition.NEW);
        entity1.setCategory("Electronics");

        assertThat(entity1.getDescription()).isEqualTo("Desc");
        assertThat(entity1.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(entity1.getAvailableQuantity()).isEqualTo(10);
        assertThat(entity1.getCondition()).isEqualTo(Condition.NEW);
        assertThat(entity1.getCategory()).isEqualTo("Electronics");
    }

    @Test
    @DisplayName("ProductVariantEntity should have getters and setters working")
    void productVariantEntity_shouldWork() {
        ProductVariantEntity entity1 = new ProductVariantEntity();
        entity1.setId(1L);
        entity1.setType("Color");

        ProductVariantEntity entity2 = new ProductVariantEntity();
        entity2.setId(1L);

        assertThat(entity1).isEqualTo(entity2);
        assertThat(entity1.hashCode()).isEqualTo(entity2.hashCode());

        assertThat(entity1.getType()).isEqualTo("Color");
    }

    @Test
    @DisplayName("ProductVariantValueEntity should have getters and setters working")
    void productVariantValueEntity_shouldWork() {
        ProductVariantValueEntity entity1 = new ProductVariantValueEntity();
        entity1.setId(1L);
        entity1.setValue("Red");

        ProductVariantValueEntity entity2 = new ProductVariantValueEntity();
        entity2.setId(1L);

        assertThat(entity1).isEqualTo(entity2);
        assertThat(entity1.hashCode()).isEqualTo(entity2.hashCode());

        assertThat(entity1.getValue()).isEqualTo("Red");
    }

    @Test
    @DisplayName("SellerEntity should have getters and setters working")
    void sellerEntity_shouldWork() {
        SellerEntity entity = new SellerEntity();
        entity.setId(1L);
        entity.setName("Name");
        entity.setDescription("Desc");
        entity.setScore(100);

        SellerEntity entity2 = new SellerEntity("Name", "Desc", 100);
        entity2.setId(1L);

        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getName()).isEqualTo("Name");
        assertThat(entity.getDescription()).isEqualTo("Desc");
        assertThat(entity.getScore()).isEqualTo(100);

        assertThat(entity2.getName()).isEqualTo("Name");
    }
}
