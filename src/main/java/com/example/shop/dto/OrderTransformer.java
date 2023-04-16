package com.example.shop.dto;

import com.example.shop.entity.Order;

public class OrderTransformer {

    public static OrderDto convertToDto(Order order){
        return new OrderDto(
                order.getId(),
                order.getPrice(),
                order.getAddress(),
                order.getPhone(),
                order.getStatus(),
                order.getPaymentMethod(),
                order.getCreationDateAsString(),
                order.getDeliveryDateAsString(),
                order.getCancelDateAsString(),
                order.getComment(),
                UserTransformer.convertToDto(order.getOwner()),
                order.getProducts().stream().map(CartItemTransformer::convertToDto).toList()
        );
    }

}
