package com.hexagonal.template.infrastructure.adapter.controller;

import com.hexagonal.template.infrastructure.adapter.controller.model.request.ExampleRequest;
import com.hexagonal.template.infrastructure.adapter.controller.model.response.ExampleResponse;

import com.hexagonal.template.infrastructure.adapter.controller.validate.OnPost;
import com.hexagonal.template.application.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/example")
public class ExampleController {

    @Autowired
    private ExampleService service;

    @PostMapping
    public ResponseEntity<?> create(@Validated(OnPost.class) @RequestBody ExampleRequest request) {
        ExampleResponse response = service.create(request);
        return ResponseEntity.created(URI.create(
                String.format("/api/example/%d", response.getId() ))).build();
    }
}
