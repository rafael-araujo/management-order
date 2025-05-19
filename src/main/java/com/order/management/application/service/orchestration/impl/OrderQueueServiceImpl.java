package com.order.management.application.service.orchestration.impl;

import com.google.gson.Gson;
import com.order.management.application.mapper.OrderMapper;
import com.order.management.application.service.orchestration.OrderQueueService;
import com.order.management.application.service.validate.OrderValidateService;
import com.order.management.domain.model.OrderModel;
import com.order.management.domain.port.repository.OrderPort;
import com.order.management.infrastructure.adapter.controller.model.request.OrderRequest;
import com.order.management.infrastructure.adapter.queue.OrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderQueueServiceImpl implements OrderQueueService {

    @Autowired
    private OrderPort port;

    @Autowired
    private OrderMapper mapper;

    @Autowired
    private OrderValidateService validateService;

    @Autowired
    private OrderProducer producer;

    public void create(String message) {
        OrderModel model = mapper.toModel(new Gson().fromJson(message, OrderRequest.class));
        validateService.validateOrder(model);
        producer.sendOrderMessage(new Gson().toJson(mapper.toResponse(port.create(model))));
    }
}
