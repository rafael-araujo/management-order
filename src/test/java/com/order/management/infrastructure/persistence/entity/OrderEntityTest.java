package com.order.management.infrastructure.persistence.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderEntityTest {

    @Test
    void testNoArgsConstructor() {
        OrderEntity orderEntity = new OrderEntity();
        assertNull(orderEntity.getId());
        assertNull(orderEntity.getOrdersAndProducts());
        assertNull(orderEntity.getAmount());
        assertNull(orderEntity.getSignature());
        assertNull(orderEntity.getCreateDate());
        assertNull(orderEntity.getUpdateDate());
        assertNull(orderEntity.getRemoveDate());
        assertNull(orderEntity.getDeleted());
        assertNull(orderEntity.getUserId());
    }

    @Test
    void testAllArgsConstructor() {
        Long id = 1L;
        List<OrderProductEntity> ordersAndProducts = new ArrayList<>();
        BigDecimal amount = new BigDecimal("100.00");
        String signature = "testSignature";
        Instant createDate = Instant.now();
        Instant updateDate = Instant.now().plusSeconds(3600);
        Instant removeDate = Instant.now().plusSeconds(7200);
        Boolean deleted = false;
        Long userId = 2L;

        OrderEntity orderEntity = new OrderEntity(
                id,
                ordersAndProducts,
                amount,
                signature,
                createDate,
                updateDate,
                removeDate,
                deleted,
                userId
        );

        assertEquals(id, orderEntity.getId());
        assertEquals(ordersAndProducts, orderEntity.getOrdersAndProducts());
        assertEquals(amount, orderEntity.getAmount());
        assertEquals(signature, orderEntity.getSignature());
        assertEquals(createDate, orderEntity.getCreateDate());
        assertEquals(updateDate, orderEntity.getUpdateDate());
        assertEquals(removeDate, orderEntity.getRemoveDate());
        assertEquals(deleted, orderEntity.getDeleted());
        assertEquals(userId, orderEntity.getUserId());
    }

    @Test
    void testBuilder() {
        Long id = 1L;
        List<OrderProductEntity> ordersAndProducts = new ArrayList<>();
        BigDecimal amount = new BigDecimal("100.00");
        String signature = "testSignature";
        Instant createDate = Instant.now();
        Instant updateDate = Instant.now().plusSeconds(3600);
        Instant removeDate = Instant.now().plusSeconds(7200);
        Boolean deleted = true;
        Long userId = 2L;

        OrderEntity orderEntity = OrderEntity.builder()
                .id(id)
                .ordersAndProducts(ordersAndProducts)
                .amount(amount)
                .signature(signature)
                .createDate(createDate)
                .updateDate(updateDate)
                .removeDate(removeDate)
                .deleted(deleted)
                .userId(userId)
                .build();

        assertEquals(id, orderEntity.getId());
        assertEquals(ordersAndProducts, orderEntity.getOrdersAndProducts());
        assertEquals(amount, orderEntity.getAmount());
        assertEquals(signature, orderEntity.getSignature());
        assertEquals(createDate, orderEntity.getCreateDate());
        assertEquals(updateDate, orderEntity.getUpdateDate());
        assertEquals(removeDate, orderEntity.getRemoveDate());
        assertTrue(orderEntity.getDeleted());
        assertEquals(userId, orderEntity.getUserId());
    }

    @Test
    void testGettersAndSetters() {
        OrderEntity orderEntity = new OrderEntity();
        Long id = 3L;
        List<OrderProductEntity> ordersAndProducts = List.of(new OrderProductEntity(), new OrderProductEntity());
        BigDecimal amount = new BigDecimal("250.50");
        String signature = "anotherSignature";
        Instant now = Instant.now();
        Long userId = 5L;
        Boolean isDeleted = true;

        orderEntity.setId(id);
        orderEntity.setOrdersAndProducts(ordersAndProducts);
        orderEntity.setAmount(amount);
        orderEntity.setSignature(signature);
        orderEntity.setCreateDate(now);
        orderEntity.setUpdateDate(now.plusSeconds(1000));
        orderEntity.setRemoveDate(now.plusSeconds(2000));
        orderEntity.setDeleted(isDeleted);
        orderEntity.setUserId(userId);

        assertEquals(id, orderEntity.getId());
        assertEquals(ordersAndProducts, orderEntity.getOrdersAndProducts());
        assertEquals(amount, orderEntity.getAmount());
        assertEquals(signature, orderEntity.getSignature());
        assertEquals(now, orderEntity.getCreateDate());
        assertEquals(now.plusSeconds(1000), orderEntity.getUpdateDate());
        assertEquals(now.plusSeconds(2000), orderEntity.getRemoveDate());
        assertTrue(orderEntity.getDeleted());
        assertEquals(userId, orderEntity.getUserId());

        orderEntity.setDeleted(false);
        assertFalse(orderEntity.getDeleted());
    }

    @Test
    void testEqualsAndHashCode() {
        Long id1 = 1L;
        Long id2 = 2L;
        OrderEntity order1 = OrderEntity.builder().id(id1).amount(BigDecimal.TEN).build();
        OrderEntity order2 = OrderEntity.builder().id(id1).amount(BigDecimal.TEN).build();
        OrderEntity order3 = OrderEntity.builder().id(id2).amount(BigDecimal.TEN).build();
        OrderEntity order4 = OrderEntity.builder().id(id1).amount(BigDecimal.ONE).build();

        assertEquals(order1, order2);
        assertEquals(order1.hashCode(), order2.hashCode());

        assertNotEquals(order1, order3);
        assertNotEquals(order1.hashCode(), order3.hashCode());

        assertNotEquals(order1, order4);
        assertNotEquals(order1.hashCode(), order4.hashCode());

    }

    @Test
    void testToString() {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(1L)
                .amount(new BigDecimal("50.00"))
                .signature("testSig")
                .createDate(Instant.now())
                .userId(10L)
                .deleted(false)
                .build();
        String toStringResult = orderEntity.toString();
        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("amount=50.00"));
        assertTrue(toStringResult.contains("signature=testSig"));
        assertTrue(toStringResult.contains("createDate=")); // Instant.toString() will vary
        assertTrue(toStringResult.contains("updateDate=null"));
        assertTrue(toStringResult.contains("removeDate=null"));
        assertTrue(toStringResult.contains("deleted=false"));
        assertTrue(toStringResult.contains("userId=10"));
    }
}
