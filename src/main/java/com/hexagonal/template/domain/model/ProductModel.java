package com.hexagonal.template.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hexagonal.template.infrastructure.adapter.controller.validate.OnPost;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductModel {

    private Long id;

    @NotNull(message = "O ID do produto não pode ser nulo.", groups = OnPost.class)
    private Long productId;

    @NotBlank(message = "O nome do produto não pode estar em branco.", groups = OnPost.class)
    @NotNull(message = "O nome do produto não pode ser nulo.", groups = OnPost.class)
    private String productName;

    @NotNull(message = "O preço do produto não pode ser nulo.", groups = OnPost.class)
    @Min(value = 0, message = "O preço do produto deve ser maior ou igual a zero.", groups = OnPost.class)
    private BigDecimal price;

    @NotNull(message = "A quantidade do produto não pode ser nula.", groups = OnPost.class)
    @Min(value = 1, message = "A quantidade do produto deve ser maior que zero.", groups = OnPost.class)
    private Integer quantity;
}
