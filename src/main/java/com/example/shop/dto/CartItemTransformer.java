package com.example.shop.dto;

import com.example.shop.entity.CartItem;
import com.example.shop.entity.Product;
import com.example.shop.entity.User;

public class CartItemTransformer {

    public static CartItemDto convertToDto(CartItem cartItem){
        return new CartItemDto(
                cartItem.getId(),
                cartItem.getProduct().getId(),
                cartItem.getUser().getId(),
                cartItem.getQuantity());
    }

    public static CartItem convertToEntity(CartItemDto cartItemDto, Product product, User user){
        CartItem cartItem = new CartItem();

        cartItem.setId(cartItemDto.getId());
        cartItem.setProduct(product);
        cartItem.setUser(user);
        cartItem.setQuantity(cartItemDto.getQuantity());

        return cartItem;
    }

}
