package com.hexagonal.template.infrastructure.adapter.repository;

import com.hexagonal.template.application.mapper.OrderMapper;
import com.hexagonal.template.application.utils.OrderUtils;
import com.hexagonal.template.domain.model.OrderModel;
import com.hexagonal.template.domain.port.repository.OrderPort;
import com.hexagonal.template.infrastructure.persistence.entity.OrderEntity;
import com.hexagonal.template.infrastructure.persistence.entity.OrderProductEntity;
import com.hexagonal.template.infrastructure.persistence.repository.OrderJpaRepository;
import com.hexagonal.template.infrastructure.persistence.repository.ProductJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
public class OrderAdapter implements OrderPort {

    @Autowired
    private OrderJpaRepository orderRepository;

    @Autowired
    private ProductJpaRepository productRepository;

    @Autowired
    private OrderMapper mapper;

    @Autowired
    private OrderUtils utils;

    @Value("${management.parameters.time-verification}")
    private Integer TIME_VERIFICATION_ORDER_IN_SECONDS;

    @Override
    @Transactional
    public OrderModel create(OrderModel model) {

        Instant NOW = Instant.now();
        BigDecimal amount = new BigDecimal("0.0");

        OrderEntity entity = mapper.toEntity(model);
        entity.setCreateDate(NOW);
        entity.setDeleted(false);
        entity.setSignature(utils.generateOrderSignature(model));

        for (OrderProductEntity item : entity.getOrdersAndProducts()) {
            item.getProduct().setCreateDate(NOW);
            item.getProduct().setDeleted(false);

            productRepository.save(item.getProduct());

            amount = amount.add(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
            item.setCreateDate(NOW);
            item.setDeleted(false);
        }

        entity.setAmount(amount);

        return mapper.toModel(orderRepository.save(entity));
    }

    @Override
    public void update(OrderModel model) {
        OrderEntity entity = mapper.toEntity(model);
        entity.setUpdateDate(Instant.now());
        orderRepository.save(mapper.toEntity(model));
    }

    @Override
    public List<OrderModel> findAll(OrderModel model) {
        return orderRepository.findAll().stream().map(mapper::toModel).toList();
    }

    @Override
    public OrderModel findById(OrderModel model) {

        return orderRepository.findByIdAndDeletedFalse(model.getOrderId())
                .map(mapper::toModel)
                .orElseGet(() -> OrderModel.builder().build());
    }

    @Override
    public void delete(OrderModel model) {
        Optional<OrderEntity> entity = orderRepository.findByIdAndDeletedFalse(model.getOrderId());
        if (entity.isPresent()) {
            entity.get().setRemoveDate(Instant.now());
            entity.get().setDeleted(true);
            orderRepository.save(entity.get());
        }
    }

    public Boolean findBySignature(String signature){
        Instant timeMax = Instant.now();
        Instant timeMin = timeMax.minusSeconds(TIME_VERIFICATION_ORDER_IN_SECONDS);
        return orderRepository.findBySignature(signature, timeMin, timeMax);
    }

    public Boolean existById(OrderModel model){
        return orderRepository.findByIdAndDeletedFalse(model.getOrderId()).isPresent();
    }
}
