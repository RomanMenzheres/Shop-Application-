package com.example.shop.dto;

import com.example.shop.entity.CartItem;
import com.example.shop.entity.Order;
import com.example.shop.entity.Product;

public class CartItemTransformer {

    public static CartItemDto convertToDto(CartItem cartItem){
        return new CartItemDto(
                cartItem.getId(),
                ProductTransformer.convertToDto(cartItem.getProduct()),
                cartItem.getOrder().getId(),
                cartItem.getQuantity());
    }

    public static CartItem convertToEntity(CartItemDto cartItemDto, Product product, Order order){
        CartItem cartItem = new CartItem();

        cartItem.setId(cartItemDto.getId());
        cartItem.setProduct(product);
        cartItem.setOrder(order);
        cartItem.setQuantity(cartItemDto.getQuantity());

        return cartItem;
    }

}
