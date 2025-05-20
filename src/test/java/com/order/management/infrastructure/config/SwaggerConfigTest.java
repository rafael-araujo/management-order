//package com.order.management.infrastructure.config;
//
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.ApplicationContext;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class SwaggerConfigTest {
//
//    @Autowired
//    private ApplicationContext context;
//
//    @Autowired
//    private SwaggerConfig swaggerConfig;
//
//    @Test
//    void contextLoads() {
//        assertNotNull(context);
//        assertNotNull(swaggerConfig);
//    }
//
//    @Test
//    void customOpenAPIBeanShouldBeCreated() {
//        OpenAPI openAPI = context.getBean(OpenAPI.class);
//        assertNotNull(openAPI, "OpenAPI bean should be created");
//    }
//
//    @Test
//    void customOpenAPIShouldHaveCorrectInfo() {
//        OpenAPI openAPI = swaggerConfig.customOpenAPI();
//
//        assertNotNull(openAPI, "OpenAPI should not be null");
//        assertNotNull(openAPI.getInfo(), "Info should not be null");
//
//        Info info = openAPI.getInfo();
//        assertEquals("API de Exemplo", info.getTitle(), "Title should match");
//        assertEquals("v1.0", info.getVersion(), "Version should match");
//        assertEquals("Exemplo de utilização do Swagger com Spring Boot",
//                info.getDescription(), "Description should match");
//    }
//
//    @Test
//    void beanCreationShouldMatchDirectCreation() {
//        OpenAPI beanOpenAPI = context.getBean(OpenAPI.class);
//        OpenAPI directOpenAPI = swaggerConfig.customOpenAPI();
//
//        assertEquals(
//                directOpenAPI.getInfo().getTitle(),
//                beanOpenAPI.getInfo().getTitle(),
//                "Bean title should match direct creation"
//        );
//
//        assertEquals(
//                directOpenAPI.getInfo().getVersion(),
//                beanOpenAPI.getInfo().getVersion(),
//                "Bean version should match direct creation"
//        );
//
//        assertEquals(
//                directOpenAPI.getInfo().getDescription(),
//                beanOpenAPI.getInfo().getDescription(),
//                "Bean description should match direct creation"
//        );
//    }
//}