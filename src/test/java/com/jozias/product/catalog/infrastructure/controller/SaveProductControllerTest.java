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

import com.jozias.product.catalog.application.usecase.CreateProductUseCase;
import com.jozias.product.catalog.domain.entity.Condition;
import com.jozias.product.catalog.domain.entity.Product;
import com.jozias.product.catalog.domain.entity.ProductVariant;
import com.jozias.product.catalog.domain.entity.Seller;
import com.jozias.product.catalog.infrastructure.controller.SaveProductController;
import com.jozias.product.catalog.infrastructure.controller.dto.CreateProductRequest;
import com.jozias.product.catalog.infrastructure.controller.dto.ProductResponse;
import com.jozias.product.catalog.infrastructure.controller.dto.SellerDTO;
import com.jozias.product.catalog.infrastructure.controller.mapper.ProductRequestMapper;
import com.jozias.product.catalog.infrastructure.controller.mapper.ProductResponseMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("SaveProductController")
class SaveProductControllerTest {

    @Mock
    private CreateProductUseCase createProductUseCase;

    @Mock
    private ProductRequestMapper productRequestMapper;

    @Mock
    private ProductResponseMapper productResponseMapper;

    @InjectMocks
    private SaveProductController saveProductController;

    private CreateProductRequest createProductRequest;
    private Product createdProduct;
    private ProductResponse productResponse;
    private Seller seller;

    @BeforeEach
    void setUp() {
        createProductRequest = new CreateProductRequest(
                "Smartphone",
                "A great smartphone with amazing features",
                new BigDecimal("999.99"),
                10,
                "NEW",
                "Electronics",
                1L,
                null);

        seller = new Seller("Tech Store", "Best tech products", 95);
        seller.setId(1L);

        createdProduct = new Product(
                1L,
                "Smartphone",
                "A great smartphone with amazing features",
                new BigDecimal("999.99"),
                10,
                Condition.NEW,
                "Electronics",
                new ArrayList<>(),
                seller);

        SellerDTO sellerDTO = new SellerDTO(1L, "Tech Store", "Best tech products", 95);

        productResponse = new ProductResponse(
                1L,
                "Smartphone",
                "A great smartphone with amazing features",
                new BigDecimal("999.99"),
                10,
                Condition.NEW,
                "Electronics",
                List.of(),
                sellerDTO);
    }

    @Test
    @DisplayName("given valid request when create then should return 201 with ProductResponse")
    void givenValidRequest_whenCreate_thenShouldReturn201WithProductResponse() {
        // given
        when(productRequestMapper.toCreateDomainVariants(any())).thenReturn(new ArrayList<>());
        when(createProductUseCase.execute(any())).thenReturn(createdProduct);
        when(productResponseMapper.toResponse(createdProduct)).thenReturn(productResponse);

        // when
        ResponseEntity<ProductResponse> response = saveProductController.create(createProductRequest);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().id()).isEqualTo(1L);
        assertThat(response.getBody().name()).isEqualTo("Smartphone");

        verify(createProductUseCase).execute(any());
    }

    @Test
    @DisplayName("given request with variants when create then should map variants")
    void givenRequestWithVariants_whenCreate_thenShouldMapVariants() {
        // given
        List<ProductVariant> variants = List.of(new ProductVariant(null, "Color", new ArrayList<>()));
        when(productRequestMapper.toCreateDomainVariants(any())).thenReturn(variants);
        when(createProductUseCase.execute(any())).thenReturn(createdProduct);
        when(productResponseMapper.toResponse(createdProduct)).thenReturn(productResponse);

        // when
        ResponseEntity<ProductResponse> response = saveProductController.create(createProductRequest);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(productRequestMapper).toCreateDomainVariants(any());
    }
}
