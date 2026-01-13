package com.jozias.product.catalog.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jozias.product.catalog.application.gateway.FindSellerGateway;
import com.jozias.product.catalog.application.usecase.FindSellerUseCase;
import com.jozias.product.catalog.domain.entity.Seller;
import com.jozias.product.catalog.domain.exception.EntityNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("FindSellerUseCase")
class FindSellerUseCaseTest {

    @Mock
    private FindSellerGateway findSellerGateway;

    @InjectMocks
    private FindSellerUseCase findSellerUseCase;

    private Seller seller;

    @BeforeEach
    void setUp() {
        seller = new Seller("Tech Store", "Best tech products", 95);
        seller.setId(1L);
    }

    @Test
    @DisplayName("given existing id when findById then should return seller")
    void givenExistingId_whenFindById_thenShouldReturnSeller() {
        // given
        when(findSellerGateway.findById(1L)).thenReturn(Optional.of(seller));

        // when
        Seller result = findSellerUseCase.findById(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Tech Store");
        verify(findSellerGateway).findById(1L);
    }

    @Test
    @DisplayName("given non-existent id when findById then should throw EntityNotFoundException")
    void givenNonExistentId_whenFindById_thenShouldThrowEntityNotFoundException() {
        // given
        when(findSellerGateway.findById(999L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> findSellerUseCase.findById(999L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Seller with id 999 not found");

        verify(findSellerGateway).findById(999L);
    }
}
