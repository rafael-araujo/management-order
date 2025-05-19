package com.hexagonal.template.domain.port.repository;

import com.hexagonal.template.domain.model.OrderModel;

import java.util.List;

public interface OrderPort {

    OrderModel create(OrderModel model);
    void update(OrderModel model);
    List<OrderModel> findAll(OrderModel model);
    OrderModel findById(OrderModel model);
    void delete(OrderModel model);
    Boolean findBySignature(String signature);

}
