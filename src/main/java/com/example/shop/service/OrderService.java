package com.example.shop.service;

import com.example.shop.entity.Order;
import com.example.shop.entity.User;

import java.util.List;

public interface OrderService {

    Order create(Order order);

    Order readById(long id);

    Order update(Order order);

    void delete(long id);

    List<Order> getAll();

    Order findOpenOrderByUser(User user);

    List<Order> findNotOpenOrderByUser(User user);

}
