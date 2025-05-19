package com.order.management.domain.port.repository;

import com.order.management.domain.model.OrderModel;

import java.util.List;

public interface OrderPort {

    OrderModel create(OrderModel model);
    List<OrderModel> findAll(OrderModel model);
    OrderModel findById(OrderModel model);
    void delete(OrderModel model);
    Boolean findBySignature(String signature);
    Boolean existById(OrderModel model);

}
