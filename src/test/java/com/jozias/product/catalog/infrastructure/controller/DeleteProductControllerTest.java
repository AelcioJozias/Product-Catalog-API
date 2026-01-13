package com.jozias.product.catalog.infrastructure.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jozias.product.catalog.application.usecase.DeleteProductUsecase;
import com.jozias.product.catalog.infrastructure.controller.DeleteProductController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("DeleteProductController")
class DeleteProductControllerTest {

    @Mock
    private DeleteProductUsecase deleteProductUsecase;

    @InjectMocks
    private DeleteProductController deleteProductController;

    @Test
    @DisplayName("given id when deleteById then should return 204 No Content")
    void givenId_whenDeleteById_thenShouldReturn204NoContent() {
        // given
        Long productId = 1L;

        // when
        ResponseEntity<Void> response = deleteProductController.deleteById(productId);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
        verify(deleteProductUsecase).deleteById(productId);
    }

    @Test
    @DisplayName("given different id when deleteById then should call usecase with correct id")
    void givenDifferentId_whenDeleteById_thenShouldCallUsecaseWithCorrectId() {
        // given
        Long productId = 999L;

        // when
        ResponseEntity<Void> response = deleteProductController.deleteById(productId);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(deleteProductUsecase).deleteById(999L);
    }
}
