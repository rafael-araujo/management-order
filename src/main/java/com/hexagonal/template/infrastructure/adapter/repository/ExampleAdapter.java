package com.hexagonal.template.infrastructure.adapter.repository;

import com.hexagonal.template.domain.model.ExampleModel;
import com.hexagonal.template.domain.port.repository.ExamplePort;
import com.hexagonal.template.infrastructure.external.persistence.repository.ExampleJpaRepository;
import com.hexagonal.template.infrastructure.internal.mapper.ExampleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExampleAdapter implements ExamplePort {

    @Autowired
    private ExampleJpaRepository repository;

    @Autowired
    private ExampleMapper mapper;

    @Override
    public ExampleModel create(ExampleModel model) {
        return mapper.toModel(repository.save(mapper.toEntity(model)));
    }

    @Override
    public void update(ExampleModel model) {
        mapper.toModel(repository.save(mapper.toEntity(model)));
    }

    @Override
    public List<ExampleModel> findAll(ExampleModel model) {
        return repository.findAll().stream().map(mapper::toModel).toList();
    }

    @Override
    public ExampleModel findById(ExampleModel model) {
        return mapper.toModel(repository.findById(model.getId()).orElse(null));
    }

    @Override
    public void delete(ExampleModel model) {

        //To use with LOGIC EXCLUSION
        //repository.save(mapper.toEntity(model));

        //To use with PHYSIC EXCLUSION
        repository.delete(mapper.toEntity(model));
    }
}
