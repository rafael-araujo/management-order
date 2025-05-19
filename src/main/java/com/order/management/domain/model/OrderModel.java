package com.order.management.domain.model;

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
public class OrderModel {

    private Long orderId;
    private BigDecimal amount;
    private List<ProductModel> products;
    private Long userId;

}
