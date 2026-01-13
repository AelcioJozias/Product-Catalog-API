package com.jozias.product.catalog.infrastructure.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jozias.product.catalog.application.usecase.UpdateProductUseCase;
import com.jozias.product.catalog.domain.entity.Condition;
import com.jozias.product.catalog.domain.entity.Product;
import com.jozias.product.catalog.domain.entity.ProductVariant;
import com.jozias.product.catalog.domain.entity.Seller;
import com.jozias.product.catalog.domain.exception.EntityNotFoundException;
import com.jozias.product.catalog.infrastructure.controller.UpdateProductController;
import com.jozias.product.catalog.infrastructure.controller.dto.ProductResponse;
import com.jozias.product.catalog.infrastructure.controller.dto.SellerDTO;
import com.jozias.product.catalog.infrastructure.controller.dto.UpdateProductRequest;
import com.jozias.product.catalog.infrastructure.controller.mapper.ProductRequestMapper;
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
@DisplayName("UpdateProductController")
class UpdateProductControllerTest {

    @Mock
    private UpdateProductUseCase updateProductUseCase;

    @Mock
    private ProductRequestMapper productRequestMapper;

    @Mock
    private ProductResponseMapper productResponseMapper;

    @InjectMocks
    private UpdateProductController updateProductController;

    private UpdateProductRequest updateProductRequest;
    private Product updatedProduct;
    private ProductResponse productResponse;
    private Seller seller;

    @BeforeEach
    void setUp() {
        updateProductRequest = new UpdateProductRequest(
                "Updated Smartphone",
                "Updated description for the smartphone",
                new BigDecimal("1099.99"),
                5,
                "REFURBISHED",
                "Mobile Devices",
                null);

        seller = new Seller("Tech Store", "Best tech products", 95);
        seller.setId(1L);

        updatedProduct = new Product(
                1L,
                "Updated Smartphone",
                "Updated description for the smartphone",
                new BigDecimal("1099.99"),
                5,
                Condition.REFURBISHED,
                "Mobile Devices",
                new ArrayList<>(),
                seller);

        SellerDTO sellerDTO = new SellerDTO(1L, "Tech Store", "Best tech products", 95);

        productResponse = new ProductResponse(
                1L,
                "Updated Smartphone",
                "Updated description for the smartphone",
                new BigDecimal("1099.99"),
                5,
                Condition.REFURBISHED,
                "Mobile Devices",
                List.of(),
                sellerDTO);
    }

    @Test
    @DisplayName("given valid request when update then should return 200 with ProductResponse")
    void givenValidRequest_whenUpdate_thenShouldReturn200WithProductResponse() {
        // given
        when(productRequestMapper.toUpdateDomainVariants(any())).thenReturn(new ArrayList<>());
        when(updateProductUseCase.execute(any())).thenReturn(updatedProduct);
        when(productResponseMapper.toResponse(updatedProduct)).thenReturn(productResponse);

        // when
        ResponseEntity<ProductResponse> response = updateProductController.update(1L, updateProductRequest);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().id()).isEqualTo(1L);
        assertThat(response.getBody().name()).isEqualTo("Updated Smartphone");

        verify(updateProductUseCase).execute(any());
    }

    @Test
    @DisplayName("given non-existent product when update then should throw EntityNotFoundException")
    void givenNonExistentProduct_whenUpdate_thenShouldThrowEntityNotFoundException() {
        // given
        when(productRequestMapper.toUpdateDomainVariants(any())).thenReturn(new ArrayList<>());
        when(updateProductUseCase.execute(any())).thenThrow(new EntityNotFoundException("Product not found"));

        // when & then
        assertThatThrownBy(() -> updateProductController.update(999L, updateProductRequest))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("given request with variants when update then should map variants")
    void givenRequestWithVariants_whenUpdate_thenShouldMapVariants() {
        // given
        List<ProductVariant> variants = List.of(new ProductVariant(1L, "Color", new ArrayList<>()));
        when(productRequestMapper.toUpdateDomainVariants(any())).thenReturn(variants);
        when(updateProductUseCase.execute(any())).thenReturn(updatedProduct);
        when(productResponseMapper.toResponse(updatedProduct)).thenReturn(productResponse);

        // when
        ResponseEntity<ProductResponse> response = updateProductController.update(1L, updateProductRequest);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(productRequestMapper).toUpdateDomainVariants(any());
    }
}
