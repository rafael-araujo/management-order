package com.order.management.infrastructure.adapter.repository;

import com.order.management.application.mapper.ProductMapper;
import com.order.management.domain.model.ProductModel;
import com.order.management.domain.port.repository.ProductPort;
import com.order.management.infrastructure.persistence.entity.ProductEntity;
import com.order.management.infrastructure.persistence.repository.ProductJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class ProductAdapter implements ProductPort {

    @Autowired
    private ProductJpaRepository repository;

    @Autowired
    private ProductMapper mapper;

    @Override
    public ProductModel create(ProductModel model) {

        Instant NOW = Instant.now();

        ProductEntity entity = mapper.toEntity(model);
        entity.setCreateDate(NOW);
        entity.setDeleted(false);

        return mapper.toModel(repository.save(entity));
    }

    @Override
    public List<ProductModel> findAll(ProductModel model) {
        return repository.findAll().stream().map(mapper::toModel).toList();
    }

    @Override
    public ProductModel findById(ProductModel model) {
        return mapper.toModel(repository.findByIdAndDeletedFalse(model.getProductId()).orElse(null));
    }

    @Override
    public void delete(ProductModel model) {
        ProductEntity entity = mapper.toEntity(model);
        entity.setRemoveDate(Instant.now());
        entity.setDeleted(true);
        repository.save(entity);
    }

    public Boolean existProduct(ProductModel model) {
        return repository.existsByProductId(model.getProductId());
    }
}
