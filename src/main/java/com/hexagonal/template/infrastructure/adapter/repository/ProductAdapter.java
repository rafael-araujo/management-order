package com.hexagonal.template.infrastructure.adapter.repository;

import com.hexagonal.template.application.mapper.ProductMapper;
import com.hexagonal.template.domain.model.ProductModel;
import com.hexagonal.template.domain.port.repository.ProductPort;
import com.hexagonal.template.infrastructure.persistence.entity.ProductEntity;
import com.hexagonal.template.infrastructure.persistence.repository.ProductJpaRepository;
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
    public void update(ProductModel model) {
        ProductEntity entity = mapper.toEntity(model);
        entity.setUpdateDate(Instant.now());
        repository.save(mapper.toEntity(model));
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
