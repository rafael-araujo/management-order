package com.order.management.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SwaggerConfigTest {

    @Test
    void customOpenAPI() {
        SwaggerConfig swaggerConfig = new SwaggerConfig();
        OpenAPI openAPI = swaggerConfig.customOpenAPI();

        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());
        assertEquals("API de Exemplo", openAPI.getInfo().getTitle());
        assertEquals("v1.0", openAPI.getInfo().getVersion());
        assertEquals("Exemplo de utilização do Swagger com Spring Boot", openAPI.getInfo().getDescription());
    }
}