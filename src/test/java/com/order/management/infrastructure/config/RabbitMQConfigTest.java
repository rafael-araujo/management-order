package com.order.management.infrastructure.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RabbitMQConfigTest {

    private RabbitMQConfig rabbitMQConfig;
    private final String INPUT_ORDER_QUEUE = "input-order-queue";
    private final String OUTPUT_ORDER_QUEUE = "output-order-queue";
    private final String DEAD_LETTER_EXCHANGE = "order-exchange";
    private final String DEAD_LETTER_QUEUE = "input-order-dlq";
    private final String DEAD_LETTER_ROUTING_KEY = "order-routing-key";

    @BeforeEach
    void setUp() {
        rabbitMQConfig = new RabbitMQConfig();
        ReflectionTestUtils.setField(rabbitMQConfig, "INPUT_ORDER_QUEUE", INPUT_ORDER_QUEUE);
        ReflectionTestUtils.setField(rabbitMQConfig, "OUTPUT_ORDER_QUEUE", OUTPUT_ORDER_QUEUE);
        ReflectionTestUtils.setField(rabbitMQConfig, "DEAD_LETTER_EXCHANGE", DEAD_LETTER_EXCHANGE);
        ReflectionTestUtils.setField(rabbitMQConfig, "DEAD_LETTER_QUEUE", DEAD_LETTER_QUEUE);
        ReflectionTestUtils.setField(rabbitMQConfig, "DEAD_LETTER_ROUTING_KEY", DEAD_LETTER_ROUTING_KEY);
    }

    @Test
    void deadLetterExchange() {
        DirectExchange exchange = rabbitMQConfig.deadLetterExchange();

        assertNotNull(exchange);
        assertEquals(DEAD_LETTER_EXCHANGE, exchange.getName());
        assertTrue(exchange.isDurable());
        assertFalse(exchange.isAutoDelete());
    }

    @Test
    void deadLetterQueue() {
        Queue queue = rabbitMQConfig.deadLetterQueue();

        assertNotNull(queue);
        assertEquals(DEAD_LETTER_QUEUE, queue.getName());
        assertTrue(queue.isDurable());
        assertFalse(queue.isAutoDelete());
        assertFalse(queue.isExclusive());
    }

    @Test
    void deadLetterBinding() {
        Queue queue = rabbitMQConfig.deadLetterQueue();
        DirectExchange exchange = rabbitMQConfig.deadLetterExchange();
        Binding binding = rabbitMQConfig.deadLetterBinding(queue, exchange);

        assertNotNull(binding);
        assertEquals(queue.getName(), binding.getDestination());
        assertEquals(exchange.getName(), binding.getExchange());
        assertEquals(DEAD_LETTER_ROUTING_KEY, binding.getRoutingKey());
        assertEquals(Binding.DestinationType.QUEUE, binding.getDestinationType());
    }

    @Test
    void inputOrderQueue() {
        Queue queue = rabbitMQConfig.inputOrderQueue();

        assertNotNull(queue);
        assertEquals(INPUT_ORDER_QUEUE, queue.getName());
        assertTrue(queue.isDurable());
        assertFalse(queue.isExclusive());
        assertFalse(queue.isAutoDelete());

        Map<String, Object> arguments = queue.getArguments();
        assertNotNull(arguments);
        assertEquals(DEAD_LETTER_EXCHANGE, arguments.get("x-dead-letter-exchange"));
        assertEquals(DEAD_LETTER_ROUTING_KEY, arguments.get("x-dead-letter-routing-key"));
    }

    @Test
    void outputOrderQueue() {
        Queue queue = rabbitMQConfig.outputOrderQueue();

        assertNotNull(queue);
        assertEquals(OUTPUT_ORDER_QUEUE, queue.getName());
        assertTrue(queue.isDurable());
        assertFalse(queue.isExclusive());
        assertFalse(queue.isAutoDelete());
    }
}