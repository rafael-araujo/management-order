package com.hexagonal.template.domain.port.repository;

import com.hexagonal.template.domain.model.ExampleModel;

import java.util.List;

public interface ExamplePort {

    ExampleModel create(ExampleModel model);
    void update(ExampleModel model);
    List<ExampleModel> findAll(ExampleModel model);
    ExampleModel findById(ExampleModel model);
    void delete(ExampleModel model);
}
