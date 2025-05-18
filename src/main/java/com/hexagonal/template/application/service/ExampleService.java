package com.hexagonal.template.application.service;

import com.hexagonal.template.infrastructure.adapter.controller.model.request.ExampleRequest;
import com.hexagonal.template.infrastructure.adapter.controller.model.response.ExampleResponse;

import java.util.List;

public interface ExampleService {

    ExampleResponse create(ExampleRequest request);
    void update(ExampleRequest request);
    List<ExampleResponse> findAll(ExampleRequest request);
    ExampleResponse findById(ExampleRequest request);
    void delete(ExampleRequest request);
}
