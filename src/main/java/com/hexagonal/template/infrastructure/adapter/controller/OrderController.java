package com.hexagonal.template.infrastructure.adapter.controller;

import com.hexagonal.template.application.service.orchestration.OrderService;
import com.hexagonal.template.infrastructure.adapter.controller.model.request.OrderRequest;
import com.hexagonal.template.infrastructure.adapter.controller.model.response.OrderResponse;
import com.hexagonal.template.infrastructure.adapter.controller.validate.OnPost;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping
    public ResponseEntity<?> create(@Validated(OnPost.class) @RequestBody OrderRequest request) {
        OrderResponse response = service.create(request);
        return ResponseEntity.created(URI.create(
                String.format("/api/order/%d", response.getId() ))).build();
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<?> update(@PathParam("orderId") Long orderId) {
        service.update(OrderRequest.builder().orderId(orderId).build());
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<List<OrderResponse>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> findById(@PathParam("orderId") Long orderId) {
        return new ResponseEntity<>(service.findById(OrderRequest
                .builder().orderId(orderId).build()), HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> delete(@PathParam("orderId") Long orderId) {
        service.delete(OrderRequest.builder().orderId(orderId).build());
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
