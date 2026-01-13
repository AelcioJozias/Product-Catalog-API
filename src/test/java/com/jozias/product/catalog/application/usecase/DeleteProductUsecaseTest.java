package com.jozias.product.catalog.application.usecase;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jozias.product.catalog.application.gateway.DeleteProductGateway;
import com.jozias.product.catalog.application.usecase.DeleteProductUsecase;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("DeleteProductUsecase")
class DeleteProductUsecaseTest {

    @Mock
    private DeleteProductGateway deleteProductGateway;

    @InjectMocks
    private DeleteProductUsecase deleteProductUsecase;

    @Test
    @DisplayName("given id when deleteById then should call gateway")
    void givenId_whenDeleteById_thenShouldCallGateway() {
        // given
        Long productId = 1L;

        // when
        deleteProductUsecase.deleteById(productId);

        // then
        verify(deleteProductGateway).deleteById(productId);
    }

    @Test
    @DisplayName("given different id when deleteById then should call gateway with correct id")
    void givenDifferentId_whenDeleteById_thenShouldCallGatewayWithCorrectId() {
        // given
        Long productId = 999L;

        // when
        deleteProductUsecase.deleteById(productId);

        // then
        verify(deleteProductGateway).deleteById(999L);
    }
}
