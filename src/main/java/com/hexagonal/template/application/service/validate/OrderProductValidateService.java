package com.hexagonal.template.application.service.validate;

import com.hexagonal.template.domain.model.ProductModel;

public interface OrderProductValidateService {

    Boolean existsByProductIdAndPriceAndQuantity(ProductModel model);
}
