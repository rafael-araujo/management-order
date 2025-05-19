package com.order.management.infrastructure.adapter.model.response;

import com.order.management.domain.model.ProductModel;
import com.order.management.infrastructure.adapter.controller.model.response.OrderResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class OrderResponseTest {

    @Test
    void testNoArgsConstructor() {
        OrderResponse response = new OrderResponse();
        assertNull(response.getOrderId());
        assertNull(response.getAmount());
        assertNull(response.getProducts());
    }

    @Test
    void testAllArgsConstructor() {
        Long orderId = 123L;
        BigDecimal amount = new BigDecimal("100.50");
        List<ProductModel> products = Collections.singletonList(ProductModel.builder().productId(1L).quantity(2).price(new BigDecimal("50.25")).build());
        OrderResponse response = new OrderResponse(orderId, amount, products);
        assertEquals(orderId, response.getOrderId());
        assertEquals(amount, response.getAmount());
        assertEquals(products, response.getProducts());
    }

    @Test
    void testBuilder() {
        Long orderId = 456L;
        BigDecimal amount = new BigDecimal("75.20");
        List<ProductModel> products = Collections.singletonList(ProductModel.builder().productId(2L).quantity(1).price(new BigDecimal("75.20")).build());
        OrderResponse response = OrderResponse.builder()
                .orderId(orderId)
                .amount(amount)
                .products(products)
                .build();
        assertEquals(orderId, response.getOrderId());
        assertEquals(amount, response.getAmount());
        assertEquals(products, response.getProducts());
    }

    @Test
    void testGettersAndSetters() {
        OrderResponse response = new OrderResponse();
        Long orderId = 789L;
        BigDecimal amount = new BigDecimal("200.00");
        List<ProductModel> products = Collections.singletonList(ProductModel.builder().productId(3L).quantity(3).price(new BigDecimal("66.67")).build());

        response.setOrderId(orderId);
        response.setAmount(amount);
        response.setProducts(products);

        assertEquals(orderId, response.getOrderId());
        assertEquals(amount, response.getAmount());
        assertEquals(products, response.getProducts());
    }
}