package com.jozias.product.catalog.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                                .info(new Info()
                                                .title("Product Catalog API")
                                                .version("1.0.0")
                                                .description(
                                                                "API RESTful para catálogo de produtos desenvolvida como estudo de Clean Architecture, estratégias de cache e boas práticas de backend.")
                                                .contact(new Contact()
                                                                .name("Aelcio Putzel")
                                                                .email("aelciojoziasp@gmail.com"))
                                                .license(new License()
                                                                .name("MIT")
                                                                .url("https://opensource.org/licenses/MIT")));
        }
}
