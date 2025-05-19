package com.order.management.domain.port.repository;

import com.order.management.domain.model.ProductModel;

import java.util.List;

public interface ProductPort {

    ProductModel create(ProductModel model);
    void update(ProductModel model);
    List<ProductModel> findAll(ProductModel model);
    ProductModel findById(ProductModel model);
    void delete(ProductModel model);
    Boolean existProduct(ProductModel model);

}
