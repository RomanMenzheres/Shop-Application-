package com.example.shop.controller;

import com.example.shop.entity.Order;
import com.example.shop.entity.User;
import com.example.shop.security.LoginDetails;
import com.example.shop.service.CartItemService;
import com.example.shop.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {
    private final OrderService orderService;

    public ShoppingCartController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String shoppingCart(Model model,
                               @AuthenticationPrincipal LoginDetails loginDetails) {

        User user = loginDetails.getUser();
        Order order = orderService.findOpenOrderByUser(user);

        if (order == null) {
            order = new Order();
            order.setOwner(user);
            order = orderService.create(order);
        }

        model.addAttribute("cartItems", order.getProducts());
        model.addAttribute("order", order);

        return "shopping-cart";
    }

}
