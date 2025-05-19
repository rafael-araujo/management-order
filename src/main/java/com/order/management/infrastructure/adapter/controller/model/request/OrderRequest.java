package com.order.management.infrastructure.adapter.model.request;

import com.order.management.domain.model.ProductModel;
import com.order.management.infrastructure.adapter.controller.validate.OnPost;
import com.order.management.infrastructure.adapter.controller.validate.OnPut;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private Long orderId;

    @NotNull(message = "O products não pode ser nulo.", groups = {OnPost.class, OnPut.class})
    private List<ProductModel> products;

    @NotNull(message = "O userID não pode ser nulo.", groups = {OnPost.class, OnPut.class})
    private Long userId;

}
