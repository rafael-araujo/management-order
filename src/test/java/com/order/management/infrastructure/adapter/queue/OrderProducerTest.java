package com.order.management.infrastructure.adapter.queue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT) // Allows unused mocks, useful for @Value
class OrderProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private OrderProducer producer;

    private final String outputQueueName = "order.output.queue";
    private final String testMessage = "Test order message";

    @Test
    void sendOrderMessage_validMessage_sendsToRabbitTemplate() {

        ReflectionTestUtils.setField(producer, "OUTPUT_ORDER_QUEUE", outputQueueName);

        producer.sendOrderMessage(testMessage);

        verify(rabbitTemplate, times(1)).convertAndSend(outputQueueName, testMessage);
    }

    @Test
    void sendOrderMessage_exceptionDuringSend_logsMessage() {

        ReflectionTestUtils.setField(producer, "OUTPUT_ORDER_QUEUE", outputQueueName);
        RuntimeException exception = new RuntimeException("Connection error");
        Mockito.doThrow(exception).when(rabbitTemplate).convertAndSend(outputQueueName, testMessage);

        producer.sendOrderMessage(testMessage);

        verify(rabbitTemplate, times(1)).convertAndSend(outputQueueName, testMessage);
    }
}