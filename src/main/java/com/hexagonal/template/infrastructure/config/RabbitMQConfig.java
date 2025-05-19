package com.hexagonal.template.infrastructure.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue inputOrderQueue() {
        return new Queue("INPUT_ORDER", true, false, false); // nome, durable, exclusive, auto-delete
    }

    @Bean
    public Queue outputOrderQueue() {
        return new Queue("OUTPUT_ORDER", true, false, false); // nome, durable, exclusive, auto-delete
    }
}