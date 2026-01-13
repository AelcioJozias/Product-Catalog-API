package com.jozias.product.catalog.infrastructure.controller.apiversion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jozias.product.catalog.infrastructure.controller.apiversion.ApiVersion;

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
