package com.order.management.infrastructure.persistence.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductEntityTest {

    @Test
    void testNoArgsConstructor() {
        ProductEntity productEntity = new ProductEntity();
        assertNull(productEntity.getId());
        assertNull(productEntity.getProductId());
        assertNull(productEntity.getProductName());
        assertNull(productEntity.getOrdersAndProducts());
        assertNull(productEntity.getCreateDate());
        assertNull(productEntity.getUpdateDate());
        assertNull(productEntity.getRemoveDate());
        assertNull(productEntity.getDeleted());
        assertNull(productEntity.getUserId());
    }

    @Test
    void testAllArgsConstructor() {
        Long id = 1L;
        Long productId = 100L;
        String productName = "Test Product";
        List<OrderProductEntity> ordersAndProducts = new ArrayList<>();
        Instant createDate = Instant.now();
        Instant updateDate = Instant.now().plusSeconds(3600);
        Instant removeDate = Instant.now().plusSeconds(7200);
        Boolean deleted = false;
        Long userId = 2L;

        ProductEntity productEntity = new ProductEntity(
                id,
                productId,
                productName,
                ordersAndProducts,
                createDate,
                updateDate,
                removeDate,
                deleted,
                userId
        );

        assertEquals(id, productEntity.getId());
        assertEquals(productId, productEntity.getProductId());
        assertEquals(productName, productEntity.getProductName());
        assertEquals(ordersAndProducts, productEntity.getOrdersAndProducts());
        assertEquals(createDate, productEntity.getCreateDate());
        assertEquals(updateDate, productEntity.getUpdateDate());
        assertEquals(removeDate, productEntity.getRemoveDate());
        assertEquals(deleted, productEntity.getDeleted());
        assertEquals(userId, productEntity.getUserId());
    }

    @Test
    void testBuilder() {
        Long id = 2L;
        Long productId = 200L;
        String productName = "Another Product";
        List<OrderProductEntity> ordersAndProducts = List.of(new OrderProductEntity());
        Instant createDate = Instant.now().minusSeconds(1000);
        Instant updateDate = Instant.now();
        Instant removeDate = Instant.now().plusSeconds(1000);
        Boolean deleted = true;
        Long userId = 3L;

        ProductEntity productEntity = ProductEntity.builder()
                .id(id)
                .productId(productId)
                .productName(productName)
                .ordersAndProducts(ordersAndProducts)
                .createDate(createDate)
                .updateDate(updateDate)
                .removeDate(removeDate)
                .deleted(deleted)
                .userId(userId)
                .build();

        assertEquals(id, productEntity.getId());
        assertEquals(productId, productEntity.getProductId());
        assertEquals(productName, productEntity.getProductName());
        assertEquals(ordersAndProducts, productEntity.getOrdersAndProducts());
        assertEquals(createDate, productEntity.getCreateDate());
        assertEquals(updateDate, productEntity.getUpdateDate());
        assertEquals(removeDate, productEntity.getRemoveDate());
        assertTrue(productEntity.getDeleted());
        assertEquals(userId, productEntity.getUserId());
    }

    @Test
    void testGettersAndSetters() {
        ProductEntity productEntity = new ProductEntity();
        Long id = 3L;
        Long productId = 300L;
        String productName = "Yet Another Product";
        List<OrderProductEntity> ordersAndProducts = new ArrayList<>();
        Instant now = Instant.now();
        Long userId = 4L;
        Boolean isDeleted = false;

        productEntity.setId(id);
        productEntity.setProductId(productId);
        productEntity.setProductName(productName);
        productEntity.setOrdersAndProducts(ordersAndProducts);
        productEntity.setCreateDate(now);
        productEntity.setUpdateDate(now.plusSeconds(500));
        productEntity.setRemoveDate(now.plusSeconds(1000));
        productEntity.setDeleted(isDeleted);
        productEntity.setUserId(userId);

        assertEquals(id, productEntity.getId());
        assertEquals(productId, productEntity.getProductId());
        assertEquals(productName, productEntity.getProductName());
        assertEquals(ordersAndProducts, productEntity.getOrdersAndProducts());
        assertEquals(now, productEntity.getCreateDate());
        assertEquals(now.plusSeconds(500), productEntity.getUpdateDate());
        assertEquals(now.plusSeconds(1000), productEntity.getRemoveDate());
        assertFalse(productEntity.getDeleted());
        assertEquals(userId, productEntity.getUserId());

        productEntity.setDeleted(true);
        assertTrue(productEntity.getDeleted());
    }

    @Test
    void testEqualsAndHashCode() {
        Long id1 = 1L;
        Long id2 = 2L;
        Long productId1 = 100L;
        Long productId2 = 200L;
        String name1 = "Product A";
        String name2 = "Product B";

        ProductEntity product1 = ProductEntity.builder().id(id1).productId(productId1).productName(name1).build();
        ProductEntity product2 = ProductEntity.builder().id(id1).productId(productId1).productName(name1).build();
        ProductEntity product3 = ProductEntity.builder().id(id2).productId(productId1).productName(name1).build();
        ProductEntity product4 = ProductEntity.builder().id(id1).productId(productId2).productName(name1).build();
        ProductEntity product5 = ProductEntity.builder().id(id1).productId(productId1).productName(name2).build();

        assertEquals(product1, product2);
        assertEquals(product1.hashCode(), product2.hashCode());

        assertNotEquals(product1, product3);
        assertNotEquals(product1.hashCode(), product3.hashCode());

        assertNotEquals(product1, product4);
        assertNotEquals(product1.hashCode(), product4.hashCode());

        assertNotEquals(product1, product5);
        assertNotEquals(product1.hashCode(), product5.hashCode());

    }

    @Test
    void testToString() {
        ProductEntity productEntity = ProductEntity.builder()
                .id(1L)
                .productId(150L)
                .productName("Sample Product")
                .createDate(Instant.now())
                .userId(5L)
                .deleted(false)
                .build();
        String toStringResult = productEntity.toString();
        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("productId=150"));
        assertTrue(toStringResult.contains("productName=Sample Product"));
        assertTrue(toStringResult.contains("createDate="));
        assertTrue(toStringResult.contains("updateDate=null"));
        assertTrue(toStringResult.contains("removeDate=null"));
        assertTrue(toStringResult.contains("deleted=false"));
        assertTrue(toStringResult.contains("userId=5"));
    }
}