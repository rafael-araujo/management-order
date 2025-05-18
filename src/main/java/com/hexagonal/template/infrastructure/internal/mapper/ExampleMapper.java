package com.hexagonal.template.infrastructure.internal.mapper;

import com.hexagonal.template.domain.model.ExampleModel;
import com.hexagonal.template.infrastructure.adapter.controller.model.request.ExampleRequest;
import com.hexagonal.template.infrastructure.adapter.controller.model.response.ExampleResponse;
import com.hexagonal.template.infrastructure.external.persistence.entity.ExampleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExampleMapper {

    ExampleEntity toEntity(ExampleModel model);
    ExampleModel toModel(ExampleEntity entity);
    ExampleModel toModel(ExampleRequest entity);
    ExampleResponse toResponse(ExampleModel model);


}
