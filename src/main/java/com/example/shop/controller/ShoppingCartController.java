package com.example.shop.controller;

import com.example.shop.entity.CartItem;
import com.example.shop.entity.Order;
import com.example.shop.entity.User;
import com.example.shop.security.LoginDetails;
import com.example.shop.service.CartItemService;
import com.example.shop.service.OrderService;
import com.example.shop.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

    private final CartItemService cartItemService;
    private final OrderService orderService;

    public ShoppingCartController(CartItemService cartItemService, OrderService orderService) {
        this.cartItemService = cartItemService;
        this.orderService = orderService;
    }

    @GetMapping
    public String shoppingCart(Model model,
                               @AuthenticationPrincipal LoginDetails loginDetails) {

        User user = loginDetails.getUser();
        Order order = orderService.findActiveOrderByUser(user);

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
