package com.hexagonal.template.application.service.orchestration.impl;

import com.hexagonal.template.application.mapper.OrderMapper;
import com.hexagonal.template.application.service.orchestration.OrderService;
import com.hexagonal.template.application.service.validate.OrderValidateService;
import com.hexagonal.template.domain.exception.BusinessException;
import com.hexagonal.template.domain.model.OrderModel;
import com.hexagonal.template.domain.model.error.ErrorResponse;
import com.hexagonal.template.domain.port.repository.OrderPort;
import com.hexagonal.template.infrastructure.adapter.controller.model.request.OrderRequest;
import com.hexagonal.template.infrastructure.adapter.controller.model.response.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

     @Autowired
     private OrderPort port;

     @Autowired
     private OrderMapper mapper;

     @Autowired
     private OrderValidateService validateService;

    @Override
    public OrderResponse create(OrderRequest request) {
        OrderModel model = mapper.toModel(request);
        validateService.validateOrder(model);
        return mapper.toResponse(port.create(model));
    }

    @Override
    public void update(OrderRequest request) {

        try {
            port.update(mapper.toModel(request));
        } catch (Exception e) {
            ErrorResponse error = ErrorResponse.builder()
                    .title("Erro no Pedido")
                    .description("Houve um erro ao tentar atualizar o Pedido: " + request.getOrderId())
                    .status(HttpStatus.BAD_GATEWAY.value())
                    .build();
            throw new BusinessException(error);
        }
    }

    @Override
    public List<OrderResponse> findAll() {
        return List.of();
    }

    @Override
    public OrderResponse findById(OrderRequest request) {
        OrderModel model = mapper.toModel(request);
        return mapper.toResponse(port.findById(model));
    }

    @Override
    public void delete(OrderRequest request) {
        OrderModel model = mapper.toModel(request);
        validateService.validateDeleteOrder(model);
        port.delete(model);
    }
}
