package com.order.management.infrastructure.config;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RabbitMQConfigTest {

    @Autowired
    private ApplicationContext context;

    @Value("${spring.rabbitmq.queue.order.input}")
    private String inputOrderQueueName;

    @Value("${spring.rabbitmq.queue.order.output}")
    private String outputOrderQueueName;

    @Value("${spring.rabbitmq.exchange.order}")
    private String deadLetterExchangeName;

    @Value("${spring.rabbitmq.queue.order.input-dlq}")
    private String deadLetterQueueName;

    @Value("${spring.rabbitmq.routing-key.order}")
    private String deadLetterRoutingKey;

    @Test
    void deadLetterExchangeBeanShouldBeCreated() {
        DirectExchange deadLetterExchange = context.getBean("deadLetterExchange", DirectExchange.class);
        assertNotNull(deadLetterExchange);
        assertEquals(deadLetterExchangeName, deadLetterExchange.getName());
    }

    @Test
    void deadLetterQueueBeanShouldBeCreated() {
        Queue deadLetterQueue = context.getBean("deadLetterQueue", Queue.class);
        assertNotNull(deadLetterQueue);
        assertEquals(deadLetterQueueName, deadLetterQueue.getName());
        assertTrue(deadLetterQueue.isDurable());
    }

    @Test
    void deadLetterBindingBeanShouldBeCreated() {
        Binding deadLetterBinding = context.getBean("deadLetterBinding", Binding.class);
        assertNotNull(deadLetterBinding);
        assertEquals(deadLetterQueueName, deadLetterBinding.getDestination());
        assertEquals(deadLetterExchangeName, deadLetterBinding.getExchange());
        assertEquals(deadLetterRoutingKey, deadLetterBinding.getRoutingKey());
    }

    @Test
    void inputOrderQueueBeanShouldBeCreatedWithDLXArgs() {
        Queue inputOrderQueue = context.getBean("inputOrderQueue", Queue.class);
        assertNotNull(inputOrderQueue);
        assertEquals(inputOrderQueueName, inputOrderQueue.getName());
        assertTrue(inputOrderQueue.isDurable());
        assertEquals(deadLetterExchangeName, inputOrderQueue.getArguments().get("x-dead-letter-exchange"));
        assertEquals(deadLetterRoutingKey, inputOrderQueue.getArguments().get("x-dead-letter-routing-key"));
    }

    @Test
    void outputOrderQueueBeanShouldBeCreated() {
        Queue outputOrderQueue = context.getBean("outputOrderQueue", Queue.class);
        assertNotNull(outputOrderQueue);
        assertEquals(outputOrderQueueName, outputOrderQueue.getName());
        assertTrue(outputOrderQueue.isDurable());
        assertFalse(outputOrderQueue.isExclusive());
        assertFalse(outputOrderQueue.isAutoDelete());
    }
}