package com.hexagonal.template.infrastructure.adapter.queue;

import com.hexagonal.template.domain.port.queue.OrderPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer implements OrderPort {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.queue.order.output}")
    public String OUTPUT_ORDER_QUEUE;

    public void sendOrderMessage(String message) {
        try {
            rabbitTemplate.convertAndSend(OUTPUT_ORDER_QUEUE, message);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
