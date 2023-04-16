package com.example.shop.controller;

import com.example.shop.entity.Order;
import com.example.shop.entity.enums.Status;
import com.example.shop.security.LoginDetails;
import com.example.shop.service.OrderService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private final OrderService orderService;

    PaymentController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String payment(Model model, @AuthenticationPrincipal LoginDetails loginDetails) {

        Order order = orderService.findOrderForPaymentByUser(loginDetails.getUser());
        String address = order.getAddress();

        model.addAttribute("order", order);
        model.addAttribute("addressLine", address.substring(0, address.lastIndexOf(",")));
        model.addAttribute("city", "Rivne");
        model.addAttribute("OBL", "RIVNENSKA OBL");
        model.addAttribute("countryCode", "UA");

        return "payment";
    }

    @GetMapping("/success")
    public String success(RedirectAttributes redirectAttributes, @AuthenticationPrincipal LoginDetails loginDetails){

        Order order = orderService.findOrderForPaymentByUser(loginDetails.getUser());

        order.setStatus(Status.PAID);

        orderService.update(order);

        redirectAttributes.addFlashAttribute("thanks", true);

        return "redirect:/profile";
    }

    @GetMapping("/error")
    private String canceledPayment(@AuthenticationPrincipal LoginDetails loginDetails){

        Order order = orderService.findOrderForPaymentByUser(loginDetails.getUser());

        Order orderBeforePayment = new Order();
        orderBeforePayment.setId(order.getId());
        orderBeforePayment.setStatus(Status.OPEN);
        orderBeforePayment.setOwner(order.getOwner());
        orderBeforePayment.setPrice(order.getPrice());
        orderBeforePayment.setProducts(order.getProducts());

        orderService.update(orderBeforePayment);

        return "error";
    }

}
