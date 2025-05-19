package com.hexagonal.template.infrastructure.adapter.queue;

import com.hexagonal.template.domain.port.queue.OrderPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer implements OrderPort {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendOrderMessage(String message) {
        rabbitTemplate.convertAndSend("OUTPUT_ORDER", message);
    }
}
