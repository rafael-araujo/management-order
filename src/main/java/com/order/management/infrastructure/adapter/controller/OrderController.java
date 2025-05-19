package com.order.management.infrastructure.adapter.controller;

import com.order.management.application.service.orchestration.OrderService;
import com.order.management.infrastructure.adapter.controller.model.request.OrderRequest;
import com.order.management.infrastructure.adapter.controller.model.response.OrderResponse;
import com.order.management.infrastructure.adapter.controller.validate.OnPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping
    public ResponseEntity<?> create(@Validated(OnPost.class) @RequestBody OrderRequest request) {
        OrderResponse response = service.create(request);
        return ResponseEntity.created(URI.create(
                String.format("/api/order/%d", response.getOrderId()))).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> findById(@PathVariable("orderId") Long orderId) {
        return new ResponseEntity<>(service.findById(OrderRequest
                .builder().orderId(orderId).build()), HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> delete(@PathVariable("orderId") Long orderId) {
        service.delete(OrderRequest.builder().orderId(orderId).build());
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
