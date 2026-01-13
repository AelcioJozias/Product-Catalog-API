package com.jozias.product.catalog.infrastructure.gateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jozias.product.catalog.domain.entity.Seller;
import com.jozias.product.catalog.infrastructure.gateway.FindSellerGatewayImpl;
import com.jozias.product.catalog.infrastructure.persistence.entity.SellerEntity;
import com.jozias.product.catalog.infrastructure.persistence.mapper.SellerEntityMapper;
import com.jozias.product.catalog.infrastructure.persistence.repository.SellerRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("FindSellerGatewayImpl")
class FindSellerGatewayImplTest {

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private SellerEntityMapper sellerEntityMapper;

    @InjectMocks
    private FindSellerGatewayImpl findSellerGateway;

    private SellerEntity sellerEntity;
    private Seller seller;

    @BeforeEach
    void setUp() {
        sellerEntity = new SellerEntity();
        sellerEntity.setId(1L);
        sellerEntity.setName("Tech Store");
        sellerEntity.setDescription("Best tech products");
        sellerEntity.setScore(95);

        seller = new Seller("Tech Store", "Best tech products", 95);
        seller.setId(1L);
    }

    @Test
    @DisplayName("given existing id when findById then should return mapped seller")
    void givenExistingId_whenFindById_thenShouldReturnMappedSeller() {
        // given
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(sellerEntity));
        when(sellerEntityMapper.toDomain(sellerEntity)).thenReturn(seller);

        // when
        Optional<Seller> result = findSellerGateway.findById(1L);

        // then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(seller);
        verify(sellerRepository).findById(1L);
    }

    @Test
    @DisplayName("given non-existent id when findById then should return empty")
    void givenNonExistentId_whenFindById_thenShouldReturnEmpty() {
        // given
        when(sellerRepository.findById(999L)).thenReturn(Optional.empty());

        // when
        Optional<Seller> result = findSellerGateway.findById(999L);

        // then
        assertThat(result).isEmpty();
        verify(sellerRepository).findById(999L);
    }
}
