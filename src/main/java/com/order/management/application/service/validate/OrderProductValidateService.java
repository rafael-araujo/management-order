package com.order.management.application.service.validate;

import com.order.management.domain.model.ProductModel;

public interface OrderProductValidateService {

    Boolean existsByProductIdAndPriceAndQuantity(ProductModel model);
}
