package com.order.management.infrastructure.adapter.queue;

import com.google.gson.Gson;
import com.order.management.application.service.orchestration.OrderQueueService;
import com.order.management.domain.exception.BusinessException;
import com.order.management.domain.model.error.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderConsumerTest {

    @Mock
    private OrderQueueService service;

    @InjectMocks
    private OrderConsumer consumer;

    private final String validMessage = "{\"orderId\": 123, \"customerId\": 456}";
    private final Gson gson = new Gson();

    @Test
    void receiveOrderMessage_validMessage_callsServiceCreate() {
        // Arrange
        doNothing().when(service).create(validMessage);

        // Act
        consumer.receiveOrderMessage(validMessage);

        // Assert
        verify(service).create(validMessage);
    }

    @Test
    void receiveOrderMessage_businessException_throwsAmqpRejectAndDontRequeueException() {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .title("BUSINESS_ERROR")
                .description("Invalid order data")
                .build();
        BusinessException businessException = new BusinessException(errorResponse);
        doThrow(businessException).when(service).create(validMessage);


        AmqpRejectAndDontRequeueException thrownException = assertThrows(
                AmqpRejectAndDontRequeueException.class,
                () -> consumer.receiveOrderMessage(validMessage)
        );

        String expectedErrorMessage = "Erro de negócio: " + gson.toJson(errorResponse);
        assertEquals(expectedErrorMessage, thrownException.getMessage());
        verify(service).create(validMessage);
    }

    @Test
    void receiveOrderMessage_genericException_throwsAmqpRejectAndDontRequeueException() {

        String errorMessage = "Database connection failed";
        Exception genericException = new RuntimeException(errorMessage);
        doThrow(genericException).when(service).create(validMessage);

        AmqpRejectAndDontRequeueException thrownException = assertThrows(
                AmqpRejectAndDontRequeueException.class,
                () -> consumer.receiveOrderMessage(validMessage)
        );

        String expectedErrorMessage = "Internal Server Error: " + errorMessage;
        assertEquals(expectedErrorMessage, thrownException.getMessage());
        verify(service).create(validMessage);
    }
}