package com.hexagonal.template.application.service.validate.impl;

import com.hexagonal.template.application.service.validate.OrderValidateService;
import com.hexagonal.template.domain.model.OrderModel;
import com.hexagonal.template.domain.port.repository.OrderPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderValidateServiceImpl implements OrderValidateService {

    @Autowired
    private OrderPort orderPort;

    public void validateOrder(OrderModel model){

    }
}
