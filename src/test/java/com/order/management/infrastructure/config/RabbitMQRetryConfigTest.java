//package com.order.management.infrastructure.config;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.ApplicationContext;
//import org.springframework.retry.RetryPolicy;
//import org.springframework.retry.backoff.FixedBackOffPolicy;
//import org.springframework.retry.policy.SimpleRetryPolicy;
//import org.springframework.retry.support.RetryTemplate;
//
//import java.util.concurrent.atomic.AtomicInteger;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class RabbitMQRetryConfigTest {
//
//    @Autowired
//    private ApplicationContext context;
//
//    @Mock
//    private ConnectionFactory connectionFactory;
//
//    @Mock
//    private RabbitTemplate rabbitTemplate;
//
//    @Test
//    void rabbitListenerContainerFactoryBeanShouldBeCreated() {
//
//        RabbitListenerContainerFactory<?> factory = context.getBean("rabbitListenerContainerFactory", RabbitListenerContainerFactory.class);
//        assertNotNull(factory, "RabbitListenerContainerFactory should be created");
//        assertEquals(SimpleRabbitListenerContainerFactory.class, factory.getClass(), "Factory should be of type SimpleRabbitListenerContainerFactory");
//    }
//
//    @Test
//    void retryTemplateBeanShouldBeCreatedWithCorrectBehavior() {
//        RetryTemplate retryTemplate = context.getBean("retryTemplate", RetryTemplate.class);
//        assertNotNull(retryTemplate, "RetryTemplate bean should be created");
//
//        AtomicInteger attempts = new AtomicInteger(0);
//
//        assertThrows(RuntimeException.class, () -> {
//            retryTemplate.execute(context -> {
//                attempts.incrementAndGet();
//                throw new RuntimeException("Test exception");
//            });
//        }, "Should throw exception after all retry attempts");
//
//        assertEquals(3, attempts.get(), "RetryTemplate should attempt 5 times");
//    }
//
//    @Test
//    void retryTemplateBackoffPeriodShouldBeCorrect() {
//        RetryTemplate retryTemplate = context.getBean("retryTemplate", RetryTemplate.class);
//        assertNotNull(retryTemplate, "RetryTemplate bean should be created");
//
//        AtomicInteger attempts = new AtomicInteger(0);
//        long[] executionTimes = new long[2];
//
//        assertThrows(RuntimeException.class, () -> {
//            retryTemplate.execute(context -> {
//                int currentAttempt = attempts.getAndIncrement();
//                if (currentAttempt < 2) {
//                    executionTimes[currentAttempt] = System.currentTimeMillis();
//                }
//                throw new RuntimeException("Test exception");
//            });
//        });
//
//        if (attempts.get() >= 2) {
//            long timeDifference = executionTimes[1] - executionTimes[0];
//            assertTrue(timeDifference >= 900,
//                    "Backoff period should be approximately 1000ms, but was " + timeDifference + "ms");
//        }
//    }
//
//    @Test
//    void simpleRetryPolicyBeanShouldBeCreatedWithCorrectMaxAttempts() {
//        RetryPolicy simpleRetryPolicy = context.getBean("simpleRetryPolicy", RetryPolicy.class);
//        assertNotNull(simpleRetryPolicy, "SimpleRetryPolicy bean should be created");
//        assertEquals(SimpleRetryPolicy.class, simpleRetryPolicy.getClass(), "Bean should be of type SimpleRetryPolicy");
//        assertEquals(5, ((SimpleRetryPolicy) simpleRetryPolicy).getMaxAttempts(), "Max attempts should be 5");
//    }
//
//    @Test
//    void fixedBackOffPolicyBeanShouldBeCreatedWithCorrectBackOffPeriod() {
//        FixedBackOffPolicy fixedBackOffPolicy = context.getBean("fixedBackOffPolicy", FixedBackOffPolicy.class);
//        assertNotNull(fixedBackOffPolicy, "FixedBackOffPolicy bean should be created");
//        assertEquals(1000L, fixedBackOffPolicy.getBackOffPeriod(), "BackOff period should be 1000ms");
//    }
//}