package com.hexagonal.template.infrastructure.adapter.repository;

import com.hexagonal.template.domain.model.OrderModel;
import com.hexagonal.template.domain.port.repository.OrderPort;
import com.hexagonal.template.infrastructure.persistence.entity.OrderEntity;
import com.hexagonal.template.infrastructure.persistence.repository.OrderJpaRepository;
import com.hexagonal.template.application.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class OrderAdapter implements OrderPort {

    @Autowired
    private OrderJpaRepository repository;

    @Autowired
    private OrderMapper mapper;

    @Override
    public OrderModel create(OrderModel model) {

        Instant NOW = Instant.now();

        OrderEntity entity = mapper.toEntity(model);
        entity.setCreateDate(NOW);
        entity.setDeleted(false);
        entity.getOrdersAndProducts().forEach(item -> {
            item.getProduct().setCreateDate(NOW);
            item.getProduct().setDeleted(false);

            item.setCreateDate(NOW);
        });

        return mapper.toModel(repository.save(entity));
    }

    @Override
    public void update(OrderModel model) {
        OrderEntity entity = mapper.toEntity(model);
        entity.setUpdateDate(Instant.now());
        repository.save(mapper.toEntity(model));
    }

    @Override
    public List<OrderModel> findAll(OrderModel model) {
        return repository.findAll().stream().map(mapper::toModel).toList();
    }

    @Override
    public OrderModel findById(OrderModel model) {
        return mapper.toModel(repository.findById(model.getOrderId()).orElse(null));
    }

    @Override
    public void delete(OrderModel model) {
        OrderEntity entity = mapper.toEntity(model);
        entity.setRemoveDate(Instant.now());
        entity.setDeleted(true);
        repository.save(entity);
    }

//    public OrderModel existOrderWithParameters(OrderModel model){
//        OrderEntity entity = mapper.toEntity(model);
//        return mapper.toModel(repository.existOrderWithParameters(entity));
//    }
}
