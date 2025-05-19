package com.order.management.domain.port.queue;

public interface OrderPort {

    void sendOrderMessage(String message);

}
