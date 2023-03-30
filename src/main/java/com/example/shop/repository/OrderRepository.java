package com.example.shop.repository;

import com.example.shop.entity.Order;
import com.example.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findOrderByOwner(User owner);
}
