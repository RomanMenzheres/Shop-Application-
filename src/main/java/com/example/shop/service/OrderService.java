package com.example.shop.service;

import com.example.shop.entity.Order;
import com.example.shop.entity.User;
import com.example.shop.entity.enums.Status;

import java.util.List;

public interface OrderService {

    Order create(Order order);

    Order readById(long id);

    void delete(long id);

    List<Order> getAll();

    Order findActiveOrderByUser(User user);

}
