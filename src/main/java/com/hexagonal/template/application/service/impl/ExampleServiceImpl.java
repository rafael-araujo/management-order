package com.hexagonal.template.application.service.impl;

import com.hexagonal.template.application.service.ExampleService;
import com.hexagonal.template.domain.port.repository.ExamplePort;
import com.hexagonal.template.infrastructure.adapter.controller.model.request.ExampleRequest;
import com.hexagonal.template.infrastructure.adapter.controller.model.response.ExampleResponse;
import com.hexagonal.template.infrastructure.internal.mapper.ExampleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExampleServiceImpl implements ExampleService {

     @Autowired
     private ExamplePort port;

     @Autowired
     private ExampleMapper mapper;

    @Override
    public ExampleResponse create(ExampleRequest request) {

        // Here, all the validations and external calls (Adapters Out) necessary
        // to be able to call the implementation of the business rules are inserted.
        return mapper.toResponse(port.create(mapper.toModel(request)));
    }

    @Override
    public void update(ExampleRequest request) {

    }

    @Override
    public List<ExampleResponse> findAll(ExampleRequest request) {
        return List.of();
    }

    @Override
    public ExampleResponse findById(ExampleRequest request) {
        return null;
    }

    @Override
    public void delete(ExampleRequest request) {

    }
}
