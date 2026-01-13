package com.jozias.product.catalog.domain.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jozias.product.catalog.domain.exception.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("EntityNotFoundException")
class EntityNotFoundExceptionTest {

    @Test
    @DisplayName("given message when created then should have correct message")
    void givenMessage_whenCreated_thenShouldHaveCorrectMessage() {
        // given
        String expectedMessage = "Entity not found with id: 123";

        // when
        EntityNotFoundException exception = new EntityNotFoundException(expectedMessage);

        // then
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    @DisplayName("given exception when thrown then should be RuntimeException")
    void givenException_whenThrown_thenShouldBeRuntimeException() {
        // given
        EntityNotFoundException exception = new EntityNotFoundException("Test");

        // then
        assertThat(exception).isInstanceOf(RuntimeException.class);
    }
}
