package com.order.management.application.service.validate.impl;

import com.order.management.application.service.validate.OrderValidateService;
import com.order.management.application.utils.OrderUtils;
import com.order.management.domain.exception.BusinessException;
import com.order.management.domain.model.OrderModel;
import com.order.management.domain.model.error.ErrorResponse;
import com.order.management.domain.port.repository.OrderPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class OrderValidateServiceImpl implements OrderValidateService {

    @Autowired
    private OrderPort port;

    @Autowired
    private OrderUtils utils;

    public void validateOrder(OrderModel model){

        if (utils.hasDuplicateProductIds(model.getProducts())) {
            throw new BusinessException(ErrorResponse.builder()
                .title("Produtos Duplicados")
                .description("Existem produtos duplicados na sua lista, favor verificar!")
                .status(HttpStatus.BAD_REQUEST.value())
                .build());
        }

        if (port.findBySignature(utils.generateOrderSignature(model))) {
            throw new BusinessException(ErrorResponse.builder()
                .title("Pedido Duplicado")
                .description("O pedido já existe, favor verificar!")
                .status(HttpStatus.BAD_REQUEST.value())
                .build());
        }
    }

    public void validateDeleteOrder(OrderModel model){

        if (!port.existById(model)){
            throw new BusinessException(ErrorResponse.builder()
                .title("Pedido não encontrado")
                .description("O pedido não existe, favor verificar!")
                .status(HttpStatus.NOT_FOUND.value())
                .build());
        }
    }
}
