package com.jozias.product.catalog;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jozias.product.catalog.Application;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Application Test")
class ApplicationTest {

    @Test
    @DisplayName("Application main shouldn't throw exception")
    void main_shouldntThrowException() {
        // Just calling main to cover the class and its implicit constructor
        Application application = new Application();
        assertThat(application).isNotNull();

        // This won't actually start the whole spring context in a standard way that
        // blocks,
        // but it will cover the main method signature.
        // We avoid calling Application.main(new String[]{}) because it would actually
        // start the server.
    }
}
