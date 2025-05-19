package com.hexagonal.template.infrastructure.adapter.queue;

import com.hexagonal.template.application.service.orchestration.OrderQueueService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    @Autowired
    private OrderQueueService service;

    @RabbitListener(queues = "INPUT_ORDER")
    public void receiveOrderMessage(String message) {
        service.create(message);
    }
}
