package com.order.management.application.mapper;

import com.order.management.domain.model.ProductModel;
import com.order.management.infrastructure.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductModel toModel(ProductEntity entity);
    ProductEntity toEntity(ProductModel entity);

}
