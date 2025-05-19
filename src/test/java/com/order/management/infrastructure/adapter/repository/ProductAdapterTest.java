//package com.order.management.infrastructure.adapter.repository;
//
//import com.order.management.application.mapper.ProductMapper;
//import com.order.management.domain.model.ProductModel;
//import com.order.management.infrastructure.persistence.entity.ProductEntity;
//import com.order.management.infrastructure.persistence.repository.ProductJpaRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.junit.jupiter.MockitoSettings;
//import org.mockito.quality.Strictness;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
//class ProductAdapterTest {
//
//    @Mock
//    private ProductJpaRepository repository;
//
//    @Mock
//    private ProductMapper mapper;
//
//    @InjectMocks
//    private ProductAdapter adapter;
//
//    @Test
//    void create_validModel_savesProductAndReturnsMappedModel() {
//
//        ProductModel model = ProductModel.builder().productId(1L).productName("Test Product").build();
//        ProductEntity entityToSave = ProductEntity.builder().productId(1L).productName("Test Product").build();
//        ProductEntity savedEntity = ProductEntity.builder().id(100L).productId(1L).productName("Test Product").build();
//        ProductModel expectedModel = ProductModel.builder().id(100L).productId(1L).productName("Test Product").build();
//
//        when(mapper.toEntity(model)).thenReturn(entityToSave);
//        when(repository.save(entityToSave)).thenReturn(savedEntity);
//        when(mapper.toModel(savedEntity)).thenReturn(expectedModel);
//
//        ProductModel result = adapter.create(model);
//
//        assertEquals(expectedModel, result);
//        verify(mapper).toEntity(model);
//        verify(repository).save(entityToSave);
//        verify(mapper).toModel(savedEntity);
//        assertNotNull(entityToSave.getCreateDate());
//        assertFalse(entityToSave.getDeleted());
//    }
//
//    @Test
//    void findAll_existingProducts_returnsMappedProductModels() {
//
//        List<ProductEntity> entities = Collections.singletonList(
//                ProductEntity.builder().id(1L).productId(1L).productName("Product A").build()
//        );
//        List<ProductModel> expectedModels = Collections.singletonList(
//                ProductModel.builder().id(1L).productId(1L).productName("Product A").build()
//        );
//
//        when(repository.findAll()).thenReturn(entities);
//        when(mapper.toModel(entities.getFirst())).thenReturn(expectedModels.getFirst());
//
//        List<ProductModel> result = adapter.findAll(ProductModel.builder().build());
//
//        assertEquals(expectedModels, result);
//        verify(repository).findAll();
//        verify(mapper).toModel(entities.getFirst());
//    }
//
//    @Test
//    void findById_existingProductNotDeleted_returnsMappedModel() {
//        ProductModel model = ProductModel.builder().productId(1L).build();
//        ProductEntity entity = ProductEntity.builder().id(100L).productId(1L).productName("Test Product").deleted(false).build();
//        ProductModel expectedModel = ProductModel.builder().id(100L).productId(1L).productName("Test Product").build();
//
//        when(repository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(entity));
//        when(mapper.toModel(entity)).thenReturn(expectedModel);
//
//        ProductModel result = adapter.findById(model);
//
//        assertEquals(expectedModel, result);
//        verify(repository).findByIdAndDeletedFalse(1L);
//        verify(mapper).toModel(entity);
//    }
//
//    @Test
//    void findById_nonExistingProduct_returnsNull() {
//
//        ProductModel model = ProductModel.builder().productId(1L).build();
//        when(repository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());
//
//        ProductModel result = adapter.findById(model);
//
//        assertNull(result);
//        verify(repository).findByIdAndDeletedFalse(1L);
//    }
//
//    @Test
//    void delete_existingProduct_setsDeletedTrueAndRemoveDate() {
//
//        ProductModel model = ProductModel.builder().productId(1L).build();
//        ProductEntity entityToDelete = ProductEntity.builder().productId(1L).build();
//
//        when(mapper.toEntity(model)).thenReturn(entityToDelete);
//        when(repository.save(entityToDelete)).thenReturn(entityToDelete); // Simulate save
//
//        adapter.delete(model);
//
//        verify(mapper).toEntity(model);
//        verify(repository).save(entityToDelete);
//        assertTrue(entityToDelete.getDeleted());
//        assertNotNull(entityToDelete.getRemoveDate());
//    }
//
//    @Test
//    void existProduct_productExists_returnsTrue() {
//
//        ProductModel model = ProductModel.builder().productId(1L).build();
//        when(repository.existsByProductId(1L)).thenReturn(true);
//
//        Boolean result = adapter.existProduct(model);
//
//        assertTrue(result);
//        verify(repository).existsByProductId(1L);
//    }
//
//    @Test
//    void existProduct_productDoesNotExist_returnsFalse() {
//
//        ProductModel model = ProductModel.builder().productId(1L).build();
//        when(repository.existsByProductId(1L)).thenReturn(false);
//
//        Boolean result = adapter.existProduct(model);
//
//        assertFalse(result);
//        verify(repository).existsByProductId(1L);
//    }
//}