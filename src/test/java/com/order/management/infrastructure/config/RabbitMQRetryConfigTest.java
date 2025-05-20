package com.order.management.infrastructure.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RabbitMQRetryConfigTest {

    @Mock
    private ConnectionFactory connectionFactory;

    @InjectMocks
    private RabbitMQRetryConfig config;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(config, "connectionFactory", connectionFactory);
    }

    @Test
    void rabbitListenerContainerFactory() {
        RabbitListenerContainerFactory<?> factory = config.rabbitListenerContainerFactory();

        assertNotNull(factory);
        assertInstanceOf(SimpleRabbitListenerContainerFactory.class, factory);

        SimpleRabbitListenerContainerFactory simpleFactory = (SimpleRabbitListenerContainerFactory) factory;
        Object factoryConnectionFactory = ReflectionTestUtils.getField(simpleFactory, "connectionFactory");
        Object concurrentConsumers = ReflectionTestUtils.getField(simpleFactory, "concurrentConsumers");
        Object maxConcurrentConsumers = ReflectionTestUtils.getField(simpleFactory, "maxConcurrentConsumers");
        Object adviceChain = ReflectionTestUtils.getField(simpleFactory, "adviceChain");

        assertEquals(connectionFactory, factoryConnectionFactory);
        assertEquals(1, concurrentConsumers);
        assertEquals(10, maxConcurrentConsumers);
        assertNotNull(adviceChain);
        assertTrue(((Object[])adviceChain).length > 0);
        assertInstanceOf(RetryOperationsInterceptor.class, ((Object[]) adviceChain)[0]);
    }

    @Test
    void retryAdvice() {
        RetryOperationsInterceptor interceptor = config.retryAdvice();

        assertNotNull(interceptor);
        Object retryOperations = ReflectionTestUtils.getField(interceptor, "retryOperations");
        assertNotNull(retryOperations);
        assertInstanceOf(RetryTemplate.class, retryOperations);
    }

    @Test
    void retryTemplate() {
        RetryTemplate retryTemplate = config.retryTemplate();

        assertNotNull(retryTemplate);
        Object retryPolicy = ReflectionTestUtils.getField(retryTemplate, "retryPolicy");
        Object backOffPolicy = ReflectionTestUtils.getField(retryTemplate, "backOffPolicy");

        assertNotNull(retryPolicy);
        assertInstanceOf(SimpleRetryPolicy.class, retryPolicy);
        assertNotNull(backOffPolicy);
        assertInstanceOf(FixedBackOffPolicy.class, backOffPolicy);
    }

    @Test
    void simpleRetryPolicy() {
        RetryPolicy retryPolicy = config.simpleRetryPolicy();

        assertNotNull(retryPolicy);
        assertInstanceOf(SimpleRetryPolicy.class, retryPolicy);
        Object maxAttempts = ReflectionTestUtils.getField(retryPolicy, "maxAttempts");
        assertEquals(5, maxAttempts);
    }
}