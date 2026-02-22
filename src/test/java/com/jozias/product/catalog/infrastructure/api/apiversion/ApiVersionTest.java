package com.jozias.product.catalog.infrastructure.api.apiversion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ApiVersion Test")
class ApiVersionTest {

    @Test
    @DisplayName("ApiVersion should have V1 and allow instantiation")
    void apiVersion_shouldWork() {
        assertThat(ApiVersion.V1).isEqualTo("/v1");
        ApiVersion apiVersion = new ApiVersion();
        assertThat(apiVersion).isNotNull();
    }
}
