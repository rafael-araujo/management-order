package com.hexagonal.template.infrastructure.adapter.queue;

import com.google.gson.Gson;
import com.hexagonal.template.application.service.orchestration.OrderQueueService;
import com.hexagonal.template.domain.exception.BusinessException;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    @Autowired
    private OrderQueueService service;

    @RabbitListener(queues = "${spring.rabbitmq.queue.order.input}")
    public void receiveOrderMessage(String message) {
        try {
            service.create(message);
        }
        catch (BusinessException e) {
                throw new AmqpRejectAndDontRequeueException("Erro de negócio: " + new Gson().toJson(e.getErrorResponse()));
        }
        catch (Exception e) {
                throw new AmqpRejectAndDontRequeueException("Internal Server Error: " + e.getMessage());
        }
    }
}
