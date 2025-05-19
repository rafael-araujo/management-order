package com.order.management.infrastructure.adapter.model.request;

import com.order.management.domain.model.ProductModel;
import com.order.management.infrastructure.adapter.controller.model.request.OrderRequest;
import com.order.management.infrastructure.adapter.controller.validate.OnPost;
import com.order.management.infrastructure.adapter.controller.validate.OnPut;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OrderRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testOrderRequest_validData() {

        List<ProductModel> products = Collections.singletonList(ProductModel.builder().productId(1L).quantity(2).price(new BigDecimal("10.0")).build());
        OrderRequest request = OrderRequest.builder()
                .orderId(123L)
                .products(products)
                .userId(456L)
                .build();

        Set<ConstraintViolation<OrderRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testOrderRequest_nullProducts_onPostValidation() {

        OrderRequest request = OrderRequest.builder()
                .orderId(123L)
                .products(null)
                .userId(456L)
                .build();

        Set<ConstraintViolation<OrderRequest>> violations = validator.validate(request, OnPost.class);

        assertEquals(1, violations.size());
        ConstraintViolation<OrderRequest> violation = violations.iterator().next();
        assertEquals("O products não pode ser nulo.", violation.getMessage());
        assertEquals("products", violation.getPropertyPath().toString());
    }

    @Test
    void testOrderRequest_nullUserId_onPutValidation() {
        // Arrange
        List<ProductModel> products = Collections.singletonList(ProductModel.builder().productId(1L).quantity(2).price(new BigDecimal("10.0")).build());
        OrderRequest request = OrderRequest.builder()
                .orderId(123L)
                .products(products)
                .userId(null)
                .build();

        Set<ConstraintViolation<OrderRequest>> violations = validator.validate(request, OnPut.class);

        assertEquals(1, violations.size());
        ConstraintViolation<OrderRequest> violation = violations.iterator().next();
        assertEquals("O userID não pode ser nulo.", violation.getMessage());
        assertEquals("userId", violation.getPropertyPath().toString());
    }

    @Test
    void testOrderRequest_noArgsConstructor() {

        OrderRequest request = new OrderRequest();

        assertNull(request.getOrderId());
        assertNull(request.getProducts());
        assertNull(request.getUserId());
    }

    @Test
    void testOrderRequest_allArgsConstructor() {

        List<ProductModel> products = Collections.singletonList(ProductModel.builder().productId(1L).quantity(2).price(new BigDecimal("10.0")).build());
        Long userId = 789L;
        OrderRequest request = new OrderRequest(123L, products, userId);

        assertEquals(123L, request.getOrderId());
        assertEquals(products, request.getProducts());
        assertEquals(userId, request.getUserId());
    }

    @Test
    void testOrderRequest_builder() {

        List<ProductModel> products = Collections.singletonList(ProductModel.builder().productId(1L).quantity(2).price(new BigDecimal("10.0")).build());
        OrderRequest request = OrderRequest.builder()
                .orderId(123L)
                .products(products)
                .userId(456L)
                .build();

        assertEquals(123L, request.getOrderId());
        assertEquals(products, request.getProducts());
        assertEquals(456L, request.getUserId());
    }

    @Test
    void testOrderRequest_gettersAndSetters() {
        // Arrange
        OrderRequest request = new OrderRequest();
        List<ProductModel> products = Collections.singletonList(ProductModel.builder().productId(1L).quantity(2).price(new BigDecimal("10.0")).build());
        Long userId = 999L;
        Long orderId = 555L;

        request.setOrderId(orderId);
        request.setProducts(products);
        request.setUserId(userId);

        assertEquals(orderId, request.getOrderId());
        assertEquals(products, request.getProducts());
        assertEquals(userId, request.getUserId());
    }
}