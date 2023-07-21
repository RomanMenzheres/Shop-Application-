package com.example.shop.service;

import com.example.shop.entity.Order;
import com.example.shop.entity.User;
import org.aspectj.weaver.ast.Or;

import java.util.List;

public interface OrderService {

    Order create(Order order);

    Order readById(long id);

    Order update(Order order);

    void delete(long id);

    List<Order> getAll();

    Order findOpenOrderByUser(User user);

    List<Order> findNotOpenOrderByUser(User user);

    Order findOrderForPaymentByUser(User user);

    List<Order> findOrdersForConfirmation();

    List<Order> findConfirmedOrders();
    List<Order> findDeliveringOrders();

    List<Order> findFinishedOrders();

}
