package com.example.shop.service.Implementation;

import com.example.shop.entity.Order;
import com.example.shop.entity.User;
import com.example.shop.entity.enums.Status;
import com.example.shop.repository.OrderRepository;
import com.example.shop.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("OrderService")
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public Order create(Order order) {
        if (order != null) {
            return orderRepository.save(order);
        }
        throw new NullPointerException("Order cannot be 'null'");
    }

    @Override
    public Order readById(long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Order with id " + id + " not found"));
    }

    @Override
    public void delete(long id) {
        orderRepository.delete(readById(id));
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findActiveOrderByUser(User user) {
        return orderRepository.findOrderByOwner(user).stream().filter(order -> order.getStatus().equals(Status.OPEN)).findFirst()
                .orElse(null);
    }
}
