package com.hexagonal.template.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Nomes das Exchanges e Filas
    @Value("${spring.rabbitmq.queue.order.input}")
    public String INPUT_ORDER_QUEUE;

    @Value("${spring.rabbitmq.queue.order.output}")
    public String OUTPUT_ORDER_QUEUE;

    @Value("${spring.rabbitmq.exchange.order}")
    public String DEAD_LETTER_EXCHANGE;

    @Value("${spring.rabbitmq.queue.order.input-dlq}")
    public String DEAD_LETTER_QUEUE;

    @Value("${spring.rabbitmq.routing-key.order}")
    public String DEAD_LETTER_ROUTING_KEY;

    // Declaração da Dead-Letter Exchange
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }

    // Declaração da Dead-Letter Queue
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }

    // Binding da Dead-Letter Queue à Dead-Letter Exchange
    @Bean
    public Binding deadLetterBinding(Queue deadLetterQueue, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(DEAD_LETTER_ROUTING_KEY);
    }

    // Declaração da fila principal INPUT_ORDER configurada para usar a DLX
    @Bean
    public Queue inputOrderQueue() {
        return QueueBuilder.durable(INPUT_ORDER_QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY)
                .build();
    }

    // Declaração da fila OUTPUT_ORDER
    @Bean
    public Queue outputOrderQueue() {
        return new Queue(OUTPUT_ORDER_QUEUE, true, false, false); // nome, durable, exclusive, auto-delete
    }
}