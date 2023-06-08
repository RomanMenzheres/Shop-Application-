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
                order.getDateAsString(order.getCreationDate()),
                order.getDateAsString(order.getDeliveryDate()),
                order.getDateAsString(order.getCancelDate()),
                order.getComment(),
                UserTransformer.convertToDto(order.getOwner()),
                order.getProducts().stream().map(CartItemTransformer::convertToDto).toList()
        );
    }

}
