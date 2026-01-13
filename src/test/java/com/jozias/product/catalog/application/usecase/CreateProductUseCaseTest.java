package com.jozias.product.catalog.application.usecase;

import com.jozias.product.catalog.application.dto.CreateProductDTO;
import com.jozias.product.catalog.application.gateway.FindSellerGateway;
import com.jozias.product.catalog.application.gateway.SaveProductGateway;
import com.jozias.product.catalog.domain.entity.Condition;
import com.jozias.product.catalog.domain.entity.Product;
import com.jozias.product.catalog.domain.entity.Seller;
import com.jozias.product.catalog.domain.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateProductUseCase")
class CreateProductUseCaseTest {

    @Mock
    private SaveProductGateway saveProductGateway;

    @Mock
    private FindSellerGateway findSellerGateway;

    @InjectMocks
    private CreateProductUseCase createProductUseCase;

    private Seller seller;
    private CreateProductDTO validDto;

    @BeforeEach
    void setUp() {
        seller = new Seller("Tech Store", "Best tech products", 95);
        seller.setId(1L);

        validDto = new CreateProductDTO(
                1L,
                "Smartphone",
                "A great smartphone with amazing features",
                new BigDecimal("999.99"),
                10,
                Condition.NEW,
                "Electronics",
                new ArrayList<>());
    }

    @Test
    @DisplayName("given valid DTO when execute then should save and return product")
    void givenValidDto_whenExecute_thenShouldSaveAndReturnProduct() {
        // given
        when(findSellerGateway.findById(1L)).thenReturn(Optional.of(seller));

        Product savedProduct = new Product(
                1L,
                validDto.name(),
                validDto.description(),
                validDto.price(),
                validDto.availableQuantity(),
                validDto.condition(),
                validDto.category(),
                validDto.variants(),
                seller);
        when(saveProductGateway.save(any(Product.class))).thenReturn(savedProduct);

        // when
        Product result = createProductUseCase.execute(validDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Smartphone");

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(saveProductGateway).save(productCaptor.capture());

        Product capturedProduct = productCaptor.getValue();
        assertThat(capturedProduct.getSeller()).isEqualTo(seller);
    }

    @Test
    @DisplayName("given non-existent seller when execute then should throw EntityNotFoundException")
    void givenNonExistentSeller_whenExecute_thenShouldThrowEntityNotFoundException() {
        // given
        when(findSellerGateway.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> createProductUseCase.execute(validDto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Seller not found");

        verify(findSellerGateway).findById(1L);
    }
}
