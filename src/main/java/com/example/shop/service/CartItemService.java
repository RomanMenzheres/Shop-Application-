package com.example.shop.service;

import com.example.shop.entity.CartItem;
import com.example.shop.entity.User;

import java.util.List;

public interface CartItemService {

    CartItem create(CartItem cartItem);

    CartItem readById(long id);

    CartItem update(CartItem cartItem);

    void updateQuantity(long productId, long orderId, int quantity);

    void delete(long id);

    List<CartItem> getAll();

}
