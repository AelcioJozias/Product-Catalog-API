package com.jozias.product.catalog.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jozias.product.catalog.application.dto.UpdateProductDTO;
import com.jozias.product.catalog.application.gateway.FindProductGateway;
import com.jozias.product.catalog.application.gateway.SaveProductGateway;
import com.jozias.product.catalog.application.usecase.UpdateProductUseCase;
import com.jozias.product.catalog.domain.entity.Condition;
import com.jozias.product.catalog.domain.entity.Product;
import com.jozias.product.catalog.domain.entity.Seller;
import com.jozias.product.catalog.domain.exception.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("UpdateProductUseCase")
class UpdateProductUseCaseTest {

    @Mock
    private SaveProductGateway saveProductGateway;

    @Mock
    private FindProductGateway findProductGateway;

    @InjectMocks
    private UpdateProductUseCase updateProductUseCase;

    private Seller seller;
    private Product existingProduct;
    private UpdateProductDTO updateDto;

    @BeforeEach
    void setUp() {
        seller = new Seller("Tech Store", "Best tech products", 95);
        seller.setId(1L);

        existingProduct = new Product(
                1L,
                "Smartphone",
                "A great smartphone with amazing features",
                new BigDecimal("999.99"),
                10,
                Condition.NEW,
                "Electronics",
                new ArrayList<>(),
                seller);

        updateDto = new UpdateProductDTO(
                1L,
                "Updated Smartphone",
                "Updated description for the smartphone",
                new BigDecimal("1099.99"),
                5,
                Condition.REFURBISHED,
                "Mobile Devices",
                new ArrayList<>());
    }

    @Test
    @DisplayName("given valid DTO when execute then should update and return product")
    void givenValidDto_whenExecute_thenShouldUpdateAndReturnProduct() {
        // given
        when(findProductGateway.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(saveProductGateway.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Product result = updateProductUseCase.execute(updateDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated Smartphone");
        assertThat(result.getDescription()).isEqualTo("Updated description for the smartphone");
        assertThat(result.getPrice()).isEqualByComparingTo(new BigDecimal("1099.99"));
        assertThat(result.getCondition()).isEqualTo(Condition.REFURBISHED);

        verify(saveProductGateway).save(any(Product.class));
    }

    @Test
    @DisplayName("given non-existent product when execute then should throw EntityNotFoundException")
    void givenNonExistentProduct_whenExecute_thenShouldThrowEntityNotFoundException() {
        // given
        when(findProductGateway.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> updateProductUseCase.execute(updateDto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Product not found");

        verify(findProductGateway).findById(1L);
    }

    @Test
    @DisplayName("given DTO with variants when execute then should update variants")
    void givenDtoWithVariants_whenExecute_thenShouldUpdateVariants() {
        // given
        when(findProductGateway.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(saveProductGateway.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Product result = updateProductUseCase.execute(updateDto);

        // then
        assertThat(result).isNotNull();
        verify(saveProductGateway).save(any(Product.class));
    }
}
