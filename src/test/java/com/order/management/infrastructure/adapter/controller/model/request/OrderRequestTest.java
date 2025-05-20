package com.order.management.infrastructure.adapter.controller.model.request;

import com.order.management.domain.model.ProductModel;
import com.order.management.infrastructure.adapter.controller.validate.OnPost;
import com.order.management.infrastructure.adapter.controller.validate.OnPut;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OrderRequestTest {

    private Validator validatorForPost;
    private Validator validatorForPut;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validatorForPost = factory.getValidator();
        validatorForPut = factory.getValidator();
    }

    @Test
    void testValidOrderRequest() {
        List<ProductModel> products = new ArrayList<>();
        products.add(new ProductModel());

        OrderRequest orderRequest = OrderRequest.builder()
                .orderId(1L)
                .products(products)
                .userId(2L)
                .build();

        Set<ConstraintViolation<OrderRequest>> violationsForPost = validatorForPost.validate(orderRequest, OnPost.class);
        Set<ConstraintViolation<OrderRequest>> violationsForPut = validatorForPut.validate(orderRequest, OnPut.class);

        assertEquals(0, violationsForPost.size());
        assertEquals(0, violationsForPut.size());
        assertEquals(1L, orderRequest.getOrderId());
        assertEquals(products, orderRequest.getProducts());
        assertEquals(2L, orderRequest.getUserId());
    }

    @Test
    void testPostValidation_NullProducts() {
        OrderRequest orderRequest = OrderRequest.builder()
                .orderId(1L)
                .products(null)
                .userId(2L)
                .build();

        Set<ConstraintViolation<OrderRequest>> violations = validatorForPost.validate(orderRequest, OnPost.class);

        assertEquals(1, violations.size());
        ConstraintViolation<OrderRequest> violation = violations.iterator().next();
        assertEquals("products", violation.getPropertyPath().toString());
        assertEquals("O products não pode ser nulo.", violation.getMessage());
    }

    @Test
    void testPostValidation_NullUserId() {
        List<ProductModel> products = new ArrayList<>();
        products.add(new ProductModel());

        OrderRequest orderRequest = OrderRequest.builder()
                .orderId(1L)
                .products(products)
                .userId(null)
                .build();

        Set<ConstraintViolation<OrderRequest>> violations = validatorForPost.validate(orderRequest, OnPost.class);

        assertEquals(1, violations.size());
        ConstraintViolation<OrderRequest> violation = violations.iterator().next();
        assertEquals("userId", violation.getPropertyPath().toString());
        assertEquals("O userID não pode ser nulo.", violation.getMessage());
    }

    @Test
    void testPutValidation_NullProducts() {
        OrderRequest orderRequest = OrderRequest.builder()
                .orderId(1L)
                .products(null)
                .userId(2L)
                .build();

        Set<ConstraintViolation<OrderRequest>> violations = validatorForPut.validate(orderRequest, OnPut.class);

        assertEquals(1, violations.size());
        ConstraintViolation<OrderRequest> violation = violations.iterator().next();
        assertEquals("products", violation.getPropertyPath().toString());
        assertEquals("O products não pode ser nulo.", violation.getMessage());
    }

    @Test
    void testPutValidation_NullUserId() {
        List<ProductModel> products = new ArrayList<>();
        products.add(new ProductModel());

        OrderRequest orderRequest = OrderRequest.builder()
                .orderId(1L)
                .products(products)
                .userId(null)
                .build();

        Set<ConstraintViolation<OrderRequest>> violations = validatorForPut.validate(orderRequest, OnPut.class);

        assertEquals(1, violations.size());
        ConstraintViolation<OrderRequest> violation = violations.iterator().next();
        assertEquals("userId", violation.getPropertyPath().toString());
        assertEquals("O userID não pode ser nulo.", violation.getMessage());
    }

    @Test
    void testAllArgsConstructor() {
        List<ProductModel> products = new ArrayList<>();
        products.add(new ProductModel());

        OrderRequest orderRequest = new OrderRequest(1L, products, 2L);

        assertEquals(1L, orderRequest.getOrderId());
        assertEquals(products, orderRequest.getProducts());
        assertEquals(2L, orderRequest.getUserId());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        OrderRequest orderRequest = new OrderRequest();
        List<ProductModel> products = new ArrayList<>();
        products.add(new ProductModel());

        orderRequest.setOrderId(1L);
        orderRequest.setProducts(products);
        orderRequest.setUserId(2L);

        assertEquals(1L, orderRequest.getOrderId());
        assertEquals(products, orderRequest.getProducts());
        assertEquals(2L, orderRequest.getUserId());
    }

    @Test
    void testEqualsAndHashCode() {
        List<ProductModel> products1 = new ArrayList<>();
        products1.add(new ProductModel());

        List<ProductModel> products2 = new ArrayList<>();
        products2.add(new ProductModel());

        OrderRequest orderRequest1 = new OrderRequest(1L, products1, 2L);
        OrderRequest orderRequest2 = new OrderRequest(1L, products2, 2L);

        assertEquals(orderRequest1, orderRequest2);
        assertEquals(orderRequest1.hashCode(), orderRequest2.hashCode());
    }

    @Test
    void testToString() {
        List<ProductModel> products = new ArrayList<>();
        products.add(new ProductModel());

        OrderRequest orderRequest = new OrderRequest(1L, products, 2L);
        String toString = orderRequest.toString();

        assertTrue(toString.contains("orderId=1"));
        assertTrue(toString.contains("userId=2"));
        assertTrue(toString.contains("products="));
    }
}