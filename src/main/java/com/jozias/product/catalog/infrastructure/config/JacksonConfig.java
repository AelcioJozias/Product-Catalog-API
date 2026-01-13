package com.jozias.product.catalog.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
import com.fasterxml.jackson.databind.type.LogicalType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Jackson configuration to disable automatic type coercion.
 * This ensures that invalid type values (e.g., number for String field)
 * result in proper type mismatch errors instead of silent conversion.
 */
@Configuration
public class JacksonConfig {

        @Bean
        public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
                ObjectMapper objectMapper = builder.build();

                // Disable coercion from Integer to String
                objectMapper.coercionConfigFor(LogicalType.Textual)
                                .setCoercion(CoercionInputShape.Integer, CoercionAction.Fail);

                // Disable coercion from Float to String
                objectMapper.coercionConfigFor(LogicalType.Textual)
                                .setCoercion(CoercionInputShape.Float, CoercionAction.Fail);

                // Disable coercion from Boolean to String
                objectMapper.coercionConfigFor(LogicalType.Textual)
                                .setCoercion(CoercionInputShape.Boolean, CoercionAction.Fail);

                // Disable coercion from String to Integer
                objectMapper.coercionConfigFor(LogicalType.Integer)
                                .setCoercion(CoercionInputShape.String, CoercionAction.Fail);

                // Disable coercion from String to Float
                objectMapper.coercionConfigFor(LogicalType.Float)
                                .setCoercion(CoercionInputShape.String, CoercionAction.Fail);

                return objectMapper;
        }
}
