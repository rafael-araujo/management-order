package com.order.management.infrastructure.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RabbitMQRetryConfig {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(10);
        factory.setAdviceChain(retryAdvice());
        return factory;
    }

    @Bean
    public org.springframework.retry.interceptor.RetryOperationsInterceptor retryAdvice() {
        org.springframework.retry.interceptor.RetryOperationsInterceptor interceptor =
                new org.springframework.retry.interceptor.RetryOperationsInterceptor();
        interceptor.setRetryOperations(retryTemplate());
        return interceptor;
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(simpleRetryPolicy());
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy());
        return retryTemplate;
    }

    @Bean
    public RetryPolicy simpleRetryPolicy() {
        SimpleRetryPolicy policy = new SimpleRetryPolicy();
        policy.setMaxAttempts(5);
        return policy;
    }

    @Bean
    public FixedBackOffPolicy fixedBackOffPolicy() {
        FixedBackOffPolicy policy = new FixedBackOffPolicy();
        policy.setBackOffPeriod(1000);
        return policy;
    }
}