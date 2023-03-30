package com.example.shop.repository;

import com.example.shop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("UPDATE CartItem c SET c.quantity = ?1 WHERE c.product.id = ?2 AND c.order.id = ?3")
    @Modifying
    void updateQuantity(int quantity, long productId, long userId);
}
