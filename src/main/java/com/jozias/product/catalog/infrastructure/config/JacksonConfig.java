package com.jozias.product.catalog.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.cfg.CoercionAction;
import tools.jackson.databind.cfg.CoercionInputShape;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.type.LogicalType;

/**
 * Jackson 3 configuration to disable automatic type coercion.
 * This ensures that invalid type values (e.g., number for String field)
 * result in proper type mismatch errors instead of silent conversion.
 */
@Configuration
public class JacksonConfig {

    @Bean
    public JsonMapper jsonMapper(JsonMapper.Builder builder) {
        builder.withCoercionConfig(LogicalType.Textual, cfg -> cfg
                .setCoercion(CoercionInputShape.Integer, CoercionAction.Fail)
                .setCoercion(CoercionInputShape.Float, CoercionAction.Fail)
                .setCoercion(CoercionInputShape.Boolean, CoercionAction.Fail));

        builder.withCoercionConfig(LogicalType.Integer, cfg -> cfg
                .setCoercion(CoercionInputShape.String, CoercionAction.Fail));

        builder.withCoercionConfig(LogicalType.Float, cfg -> cfg
                .setCoercion(CoercionInputShape.String, CoercionAction.Fail));

        return builder.build();
    }
}
