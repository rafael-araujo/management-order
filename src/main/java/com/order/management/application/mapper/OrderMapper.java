package com.order.management.application.mapper;

import com.order.management.domain.model.OrderModel;
import com.order.management.domain.model.ProductModel;
import com.order.management.infrastructure.adapter.controller.model.request.OrderRequest;
import com.order.management.infrastructure.adapter.controller.model.response.OrderResponse;
import com.order.management.infrastructure.persistence.entity.OrderEntity;
import com.order.management.infrastructure.persistence.entity.OrderProductEntity;
import com.order.management.infrastructure.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

//    OrderModel toModel(OrderEntity entity);
    OrderModel toModel(OrderRequest request);
    OrderResponse toResponse(OrderModel model);

    default OrderEntity toEntity(OrderModel model) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserId(model.getUserId());

        List<OrderProductEntity> orderProductEntities = new ArrayList<>();

        for (ProductModel productModel : model.getProducts()) {
            ProductEntity productEntity = ProductEntity.builder()
                    .productName(productModel.getProductName())
                    .productId(productModel.getProductId())
                    .build();

            OrderProductEntity orderProductEntity = new OrderProductEntity();
            orderProductEntity.setProduct(productEntity);
            orderProductEntity.setOrder(orderEntity);
            orderProductEntity.setPrice(productModel.getPrice());
            orderProductEntity.setQuantity(productModel.getQuantity());

            orderProductEntities.add(orderProductEntity);
        }

        orderEntity.setOrdersAndProducts(orderProductEntities);

        return orderEntity;
    }

    default OrderModel toModel(OrderEntity entity) {
        OrderModel model = OrderModel.builder().build();

        model.setOrderId(entity.getId());
        model.setAmount(entity.getAmount());
        model.setUserId(entity.getUserId());
        model.setProducts(new ArrayList<>());

        entity.getOrdersAndProducts().forEach( item -> {
            ProductModel product = ProductModel.builder()
                .productId(item.getProduct().getProductId())
                .productName(item.getProduct().getProductName())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .build();

            model.getProducts().add(product);
        });

        return model;
    }

}
