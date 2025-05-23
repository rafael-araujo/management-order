package com.order.management.infrastructure.adapter.controller.model.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.order.management.domain.model.ProductModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {

    private Long orderId;
    private BigDecimal amount;
    private List<ProductModel> products;

}
