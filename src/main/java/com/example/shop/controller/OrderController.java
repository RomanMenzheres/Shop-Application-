package com.example.shop.controller;

import com.example.shop.entity.Order;
import com.example.shop.entity.User;
import com.example.shop.entity.enums.PaymentMethod;
import com.example.shop.security.LoginDetails;
import com.example.shop.service.CartItemService;
import com.example.shop.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final CartItemService cartItemService;

    OrderController(OrderService orderService, CartItemService cartItemService){
        this.orderService = orderService;
        this.cartItemService = cartItemService;
    }

    @GetMapping("/{order_id}/checkout")
    public String checkout(@PathVariable("order_id") long order_id, Model model){

        model.addAttribute("order", orderService.readById(order_id));
        model.addAttribute("paymentMethods", PaymentMethod.getAll());

        return "checkout";
    }

    @PostMapping("/preparation")
    public String preparation(@ModelAttribute Order order, @AuthenticationPrincipal LoginDetails loginDetails){

        User owner = loginDetails.getUser();

        order.setOwner(owner);
        Order savedOrder = orderService.create(order);

        cartItemService.findCartItemByUser(order.getOwner()).forEach(cartItem -> {
            cartItem.setOrder(savedOrder);
            cartItemService.update(cartItem);
        });

        return "redirect:/order/" + savedOrder.getId() + "/checkout";

    }

    @GetMapping
    public String checkout(Model model){

        model.addAttribute("order", new Order());
        model.addAttribute("paymentMethods", PaymentMethod.getAll());

        return "checkout";
    }

}
