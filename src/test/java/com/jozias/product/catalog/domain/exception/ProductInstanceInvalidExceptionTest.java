package com.jozias.product.catalog.domain.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jozias.product.catalog.domain.exception.ProductInstanceInvalidException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ProductInstanceInvalidException")
class ProductInstanceInvalidExceptionTest {

    @Test
    @DisplayName("given message when created then should have correct message")
    void givenMessage_whenCreated_thenShouldHaveCorrectMessage() {
        // given
        String expectedMessage = "The field 'name' in product is invalid";

        // when
        ProductInstanceInvalidException exception = new ProductInstanceInvalidException(expectedMessage);

        // then
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    @DisplayName("given exception when thrown then should be RuntimeException")
    void givenException_whenThrown_thenShouldBeRuntimeException() {
        // given
        ProductInstanceInvalidException exception = new ProductInstanceInvalidException("Test");

        // then
        assertThat(exception).isInstanceOf(RuntimeException.class);
    }
}
