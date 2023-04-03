package com.example.shop.controller;

import com.example.shop.entity.Order;
import com.example.shop.entity.enums.PaymentMethod;
import com.example.shop.entity.enums.Status;
import com.example.shop.security.LoginDetails;
import com.example.shop.service.CartItemService;
import com.example.shop.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final CartItemService cartItemService;

    OrderController(OrderService orderService, CartItemService cartItemService){
        this.orderService = orderService;
        this.cartItemService = cartItemService;
    }

    @GetMapping("/checkout")
    public String checkout(Model model, @AuthenticationPrincipal LoginDetails loginDetails){

        model.addAttribute("order", orderService.findOpenOrderByUser(loginDetails.getUser()));
        model.addAttribute("paymentMethods", PaymentMethod.getAll());

        return "checkout";
    }

    @PostMapping("/preparation")
    public String preparation(@ModelAttribute Order additionalInfo, @AuthenticationPrincipal LoginDetails loginDetails){

        Order order = orderService.findOpenOrderByUser(loginDetails.getUser());
        order.setComment(additionalInfo.getComment());
        order.setPrice(additionalInfo.getPrice());

        orderService.update(order);

        return "redirect:/order/checkout";

    }

    @PostMapping("/checkout")
    public String checkout(@AuthenticationPrincipal LoginDetails loginDetails,
                           @ModelAttribute Order additionalInfo,
                           RedirectAttributes redirectAttributes){

        Order order = orderService.findOpenOrderByUser(loginDetails.getUser());

        if (String.valueOf(additionalInfo.getPaymentMethod()).equals("CASH")){

            order.setStatus(Status.PROCESSING);
            order.setAddress(additionalInfo.getAddress());
            order.setPhone(additionalInfo.getPhone());
            order.setPaymentMethod(additionalInfo.getPaymentMethod());
            order.setCreationDate(LocalDate.now());

            orderService.update(order);

            redirectAttributes.addFlashAttribute("thanks", true);

            return "redirect:/profile";

        } else {

        }

        return "checkout";
    }

}
