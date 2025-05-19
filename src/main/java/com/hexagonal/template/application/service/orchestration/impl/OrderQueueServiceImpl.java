package com.hexagonal.template.application.service.orchestration.impl;

import com.google.gson.Gson;
import com.hexagonal.template.application.mapper.OrderMapper;
import com.hexagonal.template.application.service.orchestration.OrderQueueService;
import com.hexagonal.template.application.service.validate.OrderValidateService;
import com.hexagonal.template.domain.model.OrderModel;
import com.hexagonal.template.domain.port.repository.OrderPort;
import com.hexagonal.template.infrastructure.adapter.controller.model.request.OrderRequest;
import com.hexagonal.template.infrastructure.adapter.queue.OrderProducer;
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
