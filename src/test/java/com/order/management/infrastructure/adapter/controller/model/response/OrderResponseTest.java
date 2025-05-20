package com.order.management.infrastructure.adapter.controller.model.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.management.domain.model.ProductModel;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderResponseTest {

    @Test
    void testBuilderAndGetters() {
        List<ProductModel> products = new ArrayList<>();
        products.add(new ProductModel());
        BigDecimal amount = new BigDecimal("100.50");

        OrderResponse response = OrderResponse.builder()
                .orderId(1L)
                .amount(amount)
                .products(products)
                .build();

        assertEquals(1L, response.getOrderId());
        assertEquals(amount, response.getAmount());
        assertEquals(products, response.getProducts());
    }

    @Test
    void testAllArgsConstructor() {
        List<ProductModel> products = new ArrayList<>();
        products.add(new ProductModel());
        BigDecimal amount = new BigDecimal("100.50");

        OrderResponse response = new OrderResponse(1L, amount, products);

        assertEquals(1L, response.getOrderId());
        assertEquals(amount, response.getAmount());
        assertEquals(products, response.getProducts());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        OrderResponse response = new OrderResponse();
        List<ProductModel> products = new ArrayList<>();
        products.add(new ProductModel());
        BigDecimal amount = new BigDecimal("100.50");

        response.setOrderId(1L);
        response.setAmount(amount);
        response.setProducts(products);

        assertEquals(1L, response.getOrderId());
        assertEquals(amount, response.getAmount());
        assertEquals(products, response.getProducts());
    }

    @Test
    void testEqualsAndHashCode() {
        List<ProductModel> products1 = new ArrayList<>();
        products1.add(new ProductModel());
        List<ProductModel> products2 = new ArrayList<>();
        products2.add(new ProductModel());
        BigDecimal amount = new BigDecimal("100.50");

        OrderResponse response1 = new OrderResponse(1L, amount, products1);
        OrderResponse response2 = new OrderResponse(1L, amount, products2);

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void testToString() {
        List<ProductModel> products = new ArrayList<>();
        products.add(new ProductModel());
        BigDecimal amount = new BigDecimal("100.50");

        OrderResponse response = new OrderResponse(1L, amount, products);
        String toString = response.toString();

        assertTrue(toString.contains("orderId=1"));
        assertTrue(toString.contains("amount=" + amount));
        assertTrue(toString.contains("products="));
    }

    @Test
    void testJsonIncludeNonNull() throws JsonProcessingException {
        OrderResponse response = new OrderResponse();
        response.setOrderId(1L);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(response);

        assertTrue(json.contains("\"orderId\":1"));
        assertFalse(json.contains("\"amount\":"));
        assertFalse(json.contains("\"products\":"));
    }

    @Test
    void testJsonIncludeAllFields() throws JsonProcessingException {
        List<ProductModel> products = new ArrayList<>();
        products.add(new ProductModel());
        BigDecimal amount = new BigDecimal("100.50");

        OrderResponse response = new OrderResponse(1L, amount, products);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(response);

        assertTrue(json.contains("\"orderId\":1"));
        assertTrue(json.contains("\"amount\":100.50"));
        assertTrue(json.contains("\"products\":"));
    }
}