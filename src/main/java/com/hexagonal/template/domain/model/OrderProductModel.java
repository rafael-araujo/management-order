package com.hexagonal.template.domain.model;

import com.hexagonal.template.infrastructure.persistence.entity.OrderEntity;
import com.hexagonal.template.infrastructure.persistence.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductModel {

    private Long id;
    private OrderEntity order;
    private ProductEntity product;
    private BigDecimal price;
    private Integer quantity;
}
