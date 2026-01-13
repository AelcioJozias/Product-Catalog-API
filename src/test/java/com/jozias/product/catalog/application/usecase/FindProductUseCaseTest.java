package com.jozias.product.catalog.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.jozias.product.catalog.application.gateway.FindProductGateway;
import com.jozias.product.catalog.application.usecase.FindProductUseCase;
import com.jozias.product.catalog.domain.entity.Condition;
import com.jozias.product.catalog.domain.entity.Product;
import com.jozias.product.catalog.domain.entity.Seller;
import com.jozias.product.catalog.domain.exception.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("FindProductUseCase")
class FindProductUseCaseTest {

    @Mock
    private FindProductGateway findProductGateway;

    @InjectMocks
    private FindProductUseCase findProductUseCase;

    private Seller seller;
    private Product product;

    @BeforeEach
    void setUp() {
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

    @Test
    @DisplayName("given products exist when findAll then should return page of products")
    void givenProductsExist_whenFindAll_thenShouldReturnPageOfProducts() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> expectedPage = new PageImpl<>(List.of(product), pageable, 1);
        when(findProductGateway.list(pageable)).thenReturn(expectedPage);

        // when
        Page<Product> result = findProductUseCase.findAll(pageable);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(product);
        verify(findProductGateway).list(pageable);
    }

    @Test
    @DisplayName("given empty result when findAll then should return empty page")
    void givenEmptyResult_whenFindAll_thenShouldReturnEmptyPage() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> emptyPage = new PageImpl<>(List.of(), pageable, 0);
        when(findProductGateway.list(pageable)).thenReturn(emptyPage);

        // when
        Page<Product> result = findProductUseCase.findAll(pageable);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEmpty();
    }

    @Test
    @DisplayName("given existing id when findById then should return product")
    void givenExistingId_whenFindById_thenShouldReturnProduct() {
        // given
        when(findProductGateway.findById(1L)).thenReturn(Optional.of(product));

        // when
        Product result = findProductUseCase.findById(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Smartphone");
        verify(findProductGateway).findById(1L);
    }

    @Test
    @DisplayName("given non-existent id when findById then should throw EntityNotFoundException")
    void givenNonExistentId_whenFindById_thenShouldThrowEntityNotFoundException() {
        // given
        when(findProductGateway.findById(999L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> findProductUseCase.findById(999L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Product with id 999 not found");

        verify(findProductGateway).findById(999L);
    }
}
