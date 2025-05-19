package com.hexagonal.template.domain.port.queue;

public interface OrderPort {

    void sendOrderMessage(String message);

}
