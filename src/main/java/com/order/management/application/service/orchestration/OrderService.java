package com.order.management.application.service.orchestration;

import com.order.management.infrastructure.adapter.controller.model.request.OrderRequest;
import com.order.management.infrastructure.adapter.controller.model.response.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse create(OrderRequest request);
    List<OrderResponse> findAll();
    OrderResponse findById(OrderRequest request);
    void delete(OrderRequest request);
}
