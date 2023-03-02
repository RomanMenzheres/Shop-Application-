package com.example.shop.service;

import com.example.shop.entity.CartItem;
import com.example.shop.entity.User;

import java.util.List;

public interface CartItemService {

    CartItem create(CartItem cartItem);

    CartItem readById(long id);

    void updateQuantity(long productId, long userId, int quantity);

    void delete(long id);

    List<CartItem> getAll();

    List<CartItem> findCartItemByUser(User user);

}
