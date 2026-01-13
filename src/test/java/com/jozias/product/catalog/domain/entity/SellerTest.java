package com.jozias.product.catalog.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jozias.product.catalog.domain.entity.Seller;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Seller")
class SellerTest {

    @Test
    @DisplayName("given valid data when created then should have correct values")
    void givenValidData_whenCreated_thenShouldHaveCorrectValues() {
        // given
        String name = "Tech Store";
        String description = "Best tech products";
        int score = 95;

        // when
        Seller seller = new Seller(name, description, score);

        // then
        assertThat(seller.getName()).isEqualTo(name);
        assertThat(seller.getDescription()).isEqualTo(description);
        assertThat(seller.getScore()).isEqualTo(score);
        assertThat(seller.getId()).isNull();
    }

    @Test
    @DisplayName("given seller when setId then should update id")
    void givenSeller_whenSetId_thenShouldUpdateId() {
        // given
        Seller seller = new Seller("Store", "Description", 90);

        // when
        seller.setId(1L);

        // then
        assertThat(seller.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("given seller when setName then should update name")
    void givenSeller_whenSetName_thenShouldUpdateName() {
        // given
        Seller seller = new Seller("Old Name", "Description", 90);
        String newName = "New Name";

        // when
        seller.setName(newName);

        // then
        assertThat(seller.getName()).isEqualTo(newName);
    }

    @Test
    @DisplayName("given seller when setDescription then should update description")
    void givenSeller_whenSetDescription_thenShouldUpdateDescription() {
        // given
        Seller seller = new Seller("Store", "Old Description", 90);
        String newDescription = "New Description";

        // when
        seller.setDescription(newDescription);

        // then
        assertThat(seller.getDescription()).isEqualTo(newDescription);
    }

    @Test
    @DisplayName("given seller when setScore then should update score")
    void givenSeller_whenSetScore_thenShouldUpdateScore() {
        // given
        Seller seller = new Seller("Store", "Description", 90);
        int newScore = 100;

        // when
        seller.setScore(newScore);

        // then
        assertThat(seller.getScore()).isEqualTo(newScore);
    }
}
