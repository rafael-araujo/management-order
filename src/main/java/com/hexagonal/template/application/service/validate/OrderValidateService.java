package com.hexagonal.template.application.service.validate;

import com.hexagonal.template.domain.model.OrderModel;

public interface OrderValidateService {

    void validateOrder(OrderModel model);
}
