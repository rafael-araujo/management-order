package com.hexagonal.template.application.mapper;

import com.hexagonal.template.domain.model.ProductModel;
import com.hexagonal.template.infrastructure.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductModel toModel(ProductEntity entity);
    ProductEntity toEntity(ProductModel entity);

}
