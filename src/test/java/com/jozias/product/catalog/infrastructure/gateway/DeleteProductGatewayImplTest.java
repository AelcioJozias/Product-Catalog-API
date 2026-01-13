package com.jozias.product.catalog.infrastructure.gateway;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jozias.product.catalog.infrastructure.gateway.DeleteProductGatewayImpl;
import com.jozias.product.catalog.infrastructure.persistence.repository.ProductRepository;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("DeleteProductGatewayImpl")
class DeleteProductGatewayImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DeleteProductGatewayImpl deleteProductGateway;

    @Test
    @DisplayName("given id when deleteById then should call repository")
    void givenId_whenDeleteById_thenShouldCallRepository() {
        // given
        Long productId = 1L;

        // when
        deleteProductGateway.deleteById(productId);

        // then
        verify(productRepository).deleteById(productId);
    }

    @Test
    @DisplayName("given different id when deleteById then should call repository with correct id")
    void givenDifferentId_whenDeleteById_thenShouldCallRepositoryWithCorrectId() {
        // given
        Long productId = 999L;

        // when
        deleteProductGateway.deleteById(productId);

        // then
        verify(productRepository).deleteById(999L);
    }
}
