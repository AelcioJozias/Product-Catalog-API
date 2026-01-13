package com.jozias.product.catalog.infrastructure.controller;

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

import com.jozias.product.catalog.application.usecase.FindProductUseCase;
import com.jozias.product.catalog.domain.entity.Condition;
import com.jozias.product.catalog.domain.entity.Product;
import com.jozias.product.catalog.domain.entity.Seller;
import com.jozias.product.catalog.domain.exception.EntityNotFoundException;
import com.jozias.product.catalog.infrastructure.controller.FindProductController;
import com.jozias.product.catalog.infrastructure.controller.dto.PageResponse;
import com.jozias.product.catalog.infrastructure.controller.dto.ProductDTO;
import com.jozias.product.catalog.infrastructure.controller.dto.ProductDetailDTO;
import com.jozias.product.catalog.infrastructure.controller.mapper.ProductResponseMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("FindProductController")
class FindProductControllerTest {

    @Mock
    private FindProductUseCase findProductUseCase;

    @Mock
    private ProductResponseMapper productResponseMapper;

    @InjectMocks
    private FindProductController findProductController;

    private Seller seller;
    private Product product;
    private ProductDTO productDTO;
    private ProductDetailDTO productDetailDTO;

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

        productDTO = new ProductDTO(1L, "Smartphone", new BigDecimal("999.99"));

        productDetailDTO = new ProductDetailDTO(
                1L,
                "Smartphone",
                "A great smartphone with amazing features",
                new BigDecimal("999.99"),
                10,
                Condition.NEW,
                "Electronics",
                List.of(),
                null);
    }

    @Test
    @DisplayName("given products exist when listAll then should return page of ProductDTO")
    void givenProductsExist_whenListAll_thenShouldReturnPageOfProductDTO() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> productPage = new PageImpl<>(List.of(product), pageable, 1);

        when(findProductUseCase.findAll(pageable)).thenReturn(productPage);
        when(productResponseMapper.toDto(any(Product.class))).thenReturn(productDTO);

        // when
        PageResponse<ProductDTO> result = findProductController.listAll(pageable);

        // then
        assertThat(result).isNotNull();
        assertThat(result.content()).hasSize(1);
        assertThat(result.content().get(0).id()).isEqualTo(1L);
        assertThat(result.page()).isZero();
        assertThat(result.size()).isEqualTo(10);
        assertThat(result.totalElements()).isEqualTo(1L);
        assertThat(result.totalPages()).isEqualTo(1);
        assertThat(result.first()).isTrue();
        assertThat(result.last()).isTrue();
        verify(findProductUseCase).findAll(pageable);
    }

    @Test
    @DisplayName("given empty result when listAll then should return empty page")
    void givenEmptyResult_whenListAll_thenShouldReturnEmptyPage() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> emptyPage = new PageImpl<>(List.of(), pageable, 0);

        when(findProductUseCase.findAll(pageable)).thenReturn(emptyPage);

        // when
        PageResponse<ProductDTO> result = findProductController.listAll(pageable);

        // then
        assertThat(result).isNotNull();
        assertThat(result.content()).isEmpty();
        assertThat(result.totalElements()).isZero();
    }

    @Test
    @DisplayName("given existing id when detailById then should return ProductDetailDTO")
    void givenExistingId_whenDetailById_thenShouldReturnProductDetailDTO() {
        // given
        when(findProductUseCase.findById(1L)).thenReturn(product);
        when(productResponseMapper.toDetailDto(product)).thenReturn(productDetailDTO);

        // when
        ProductDetailDTO result = findProductController.detailById(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("Smartphone");
        verify(findProductUseCase).findById(1L);
    }

    @Test
    @DisplayName("given non-existent id when detailById then should throw EntityNotFoundException")
    void givenNonExistentId_whenDetailById_thenShouldThrowEntityNotFoundException() {
        // given
        when(findProductUseCase.findById(999L)).thenThrow(new EntityNotFoundException("Product not found"));

        // when & then
        assertThatThrownBy(() -> findProductController.detailById(999L))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
