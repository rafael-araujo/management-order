package com.order.management.application.service.validate;

import com.order.management.domain.model.OrderModel;

public interface OrderValidateService {

    void validateOrder(OrderModel model);

    void validateDeleteOrder(OrderModel model);
}
