//package com.order.management.infrastructure.adapter.repository;
//
//import com.order.management.application.mapper.OrderMapper;
//import com.order.management.application.utils.OrderUtils;
//import com.order.management.domain.model.OrderModel;
//import com.order.management.domain.model.ProductModel;
//import com.order.management.infrastructure.adapter.model.request.OrderRequest;
//import com.order.management.infrastructure.persistence.entity.OrderEntity;
//import com.order.management.infrastructure.persistence.entity.OrderProductEntity;
//import com.order.management.infrastructure.persistence.entity.ProductEntity;
//import com.order.management.infrastructure.persistence.repository.OrderJpaRepository;
//import com.order.management.infrastructure.persistence.repository.ProductJpaRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.junit.jupiter.MockitoSettings;
//import org.mockito.quality.Strictness;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
//class OrderAdapterTest {
//
//    @Mock
//    private OrderJpaRepository orderRepository;
//
//    @Mock
//    private ProductJpaRepository productRepository;
//
//    @Mock
//    private OrderMapper mapper;
//
//    @Mock
//    private OrderUtils utils;
//
//    @InjectMocks
//    private OrderAdapter adapter;
//
//    private final Integer TIME_VERIFICATION = 60;
//
//    @Test
//    void create_validModel_savesOrderAndProducts() {
//
//        ReflectionTestUtils.setField(adapter, "TIME_VERIFICATION_ORDER_IN_SECONDS", TIME_VERIFICATION);
//        OrderModel model = OrderModel.builder()
//                .userId(1L)
//                .products(List.of(
//                        ProductModel.builder().productId(10L).quantity(2).price(BigDecimal.TEN).build()
//                ))
//                .build();
//        OrderEntity entityToSave = OrderEntity.builder()
//                .ordersAndProducts(List.of(OrderProductEntity.builder()
//                    .product(ProductEntity.builder()
//                        .productId(10L)
//                        .build())
//                    .quantity(2)
//                    .price(BigDecimal.TEN)
//                    .build()))
//                .userId(1L)
//                .build();
//        OrderEntity savedEntity = OrderEntity.builder().id(100L).userId(1L).amount(BigDecimal.valueOf(20)).build();
//        OrderModel expectedModel = OrderModel.builder().orderId(100L).userId(1L).amount(BigDecimal.valueOf(20)).build();
//        String signature = "test-signature";
//
//        when(mapper.toEntity(model)).thenReturn(entityToSave);
//        when(utils.generateOrderSignature(model)).thenReturn(signature);
//        when(orderRepository.save(entityToSave)).thenReturn(savedEntity);
//        when(mapper.toModel(savedEntity)).thenReturn(expectedModel);
//
//        ArgumentCaptor<OrderEntity> orderEntityCaptor = ArgumentCaptor.forClass(OrderEntity.class);
//        ArgumentCaptor<ProductEntity> productEntityCaptor = ArgumentCaptor.forClass(ProductEntity.class);
//
//        OrderModel result = adapter.create(model);
//
//        assertEquals(expectedModel, result);
//        verify(orderRepository).save(orderEntityCaptor.capture());
//        OrderEntity capturedOrderEntity = orderEntityCaptor.getValue();
//        assertEquals(1L, capturedOrderEntity.getUserId());
//        assertEquals(new BigDecimal("20.0"), capturedOrderEntity.getAmount());
//        assertEquals(signature, capturedOrderEntity.getSignature());
//        assertFalse(capturedOrderEntity.getDeleted());
//        assertNotNull(capturedOrderEntity.getCreateDate());
//
//        verify(productRepository).save(productEntityCaptor.capture());
//        ProductEntity capturedProductEntity = productEntityCaptor.getValue();
//        assertEquals(10L, capturedProductEntity.getProductId());
//        assertFalse(capturedProductEntity.getDeleted());
//        assertNotNull(capturedProductEntity.getCreateDate());
//    }
//
//    @Test
//    void findAll_existingOrders_returnsMappedOrderModels() {
//
//        List<OrderEntity> entities = List.of(
//                OrderEntity.builder().id(1L).userId(1L).build(),
//                OrderEntity.builder().id(2L).userId(2L).build()
//        );
//        List<OrderModel> expectedModels = List.of(
//                OrderModel.builder().orderId(1L).userId(1L).build(),
//                OrderModel.builder().orderId(2L).userId(2L).build()
//        );
//        when(orderRepository.findAll()).thenReturn(entities);
//        when(mapper.toModel(entities.get(0))).thenReturn(expectedModels.get(0));
//        when(mapper.toModel(entities.get(1))).thenReturn(expectedModels.get(1));
//
//        List<OrderModel> result = adapter.findAll(OrderModel.builder().build());
//
//        assertEquals(expectedModels, result);
//        verify(orderRepository).findAll();
//        verify(mapper, times(2)).toModel(any(OrderEntity.class));
//    }
//
//    @Test
//    void findById_existingOrderNotDeleted_returnsMappedOrderModel() {
//
//        OrderModel model = OrderModel.builder().orderId(1L).build();
//        OrderEntity entity = OrderEntity.builder().id(1L).userId(1L).deleted(false).build();
//        OrderModel expectedModel = OrderModel.builder().orderId(1L).userId(1L).build();
//        when(orderRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(entity));
//        when(mapper.toModel(entity)).thenReturn(expectedModel);
//
//        OrderModel result = adapter.findById(model);
//
//        assertEquals(expectedModel, result);
//        verify(orderRepository).findByIdAndDeletedFalse(1L);
//        verify(mapper).toModel(entity);
//    }
//
//    @Test
//    void findById_nonExistingOrder_returnsEmptyOrderModel() {
//        // Arrange
//        OrderModel model = OrderModel.builder().orderId(1L).build();
//        when(orderRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());
//        OrderModel expectedModel = OrderModel.builder().build();
//
//        OrderModel result = adapter.findById(model);
//
//        assertEquals(expectedModel, result);
//        verify(orderRepository).findByIdAndDeletedFalse(1L);
//        verify(mapper, never()).toModel((OrderRequest) any());
//    }
//
//    @Test
//    void delete_existingOrderNotDeleted_setsDeletedTrueAndRemoveDate() {
//
//        OrderModel model = OrderModel.builder().orderId(1L).build();
//        OrderEntity entity = OrderEntity.builder().id(1L).deleted(false).build();
//        when(orderRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(entity));
//        ArgumentCaptor<OrderEntity> orderEntityCaptor = ArgumentCaptor.forClass(OrderEntity.class);
//
//        adapter.delete(model);
//
//        verify(orderRepository).findByIdAndDeletedFalse(1L);
//        verify(orderRepository).save(orderEntityCaptor.capture());
//        OrderEntity capturedEntity = orderEntityCaptor.getValue();
//        assertTrue(capturedEntity.getDeleted());
//        assertNotNull(capturedEntity.getRemoveDate());
//    }
//
//    @Test
//    void delete_nonExistingOrder_doesNothing() {
//
//        OrderModel model = OrderModel.builder().orderId(1L).build();
//        when(orderRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());
//
//        adapter.delete(model);
//
//        verify(orderRepository).findByIdAndDeletedFalse(1L);
//        verify(orderRepository, never()).save(any());
//    }
//
//    @Test
//    void findBySignature_signatureExistsWithinTimeFrame_returnsTrue() {
//
//        ReflectionTestUtils.setField(adapter, "TIME_VERIFICATION_ORDER_IN_SECONDS", TIME_VERIFICATION);
//        String signature = "test-signature";
//        when(orderRepository.findBySignature(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
//
//        Boolean result = adapter.findBySignature(signature);
//
//        assertTrue(result);
//        verify(orderRepository).findBySignature(Mockito.any(), Mockito.any(), Mockito.any());
//    }
//
//    @Test
//    void findBySignature_signatureDoesNotExistWithinTimeFrame_returnsFalse() {
//
//        ReflectionTestUtils.setField(adapter, "TIME_VERIFICATION_ORDER_IN_SECONDS", TIME_VERIFICATION);
//        String signature = "test-signature";
//        when(orderRepository.findBySignature(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(false);
//
//        Boolean result = adapter.findBySignature(signature);
//
//        assertFalse(result);
//        verify(orderRepository).findBySignature(Mockito.any(), Mockito.any(), Mockito.any());
//    }
//
//    @Test
//    void existById_existingOrderNotDeleted_returnsTrue() {
//
//        OrderModel model = OrderModel.builder().orderId(1L).build();
//        when(orderRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(OrderEntity.builder().build()));
//
//        Boolean result = adapter.existById(model);
//
//        assertTrue(result);
//        verify(orderRepository).findByIdAndDeletedFalse(1L);
//    }
//
//    @Test
//    void existById_nonExistingOrder_returnsFalse() {
//
//        OrderModel model = OrderModel.builder().orderId(1L).build();
//        when(orderRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());
//
//        Boolean result = adapter.existById(model);
//
//        assertFalse(result);
//        verify(orderRepository).findByIdAndDeletedFalse(1L);
//    }
//}
