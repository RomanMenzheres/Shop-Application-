package com.example.shop.controller;

import com.example.shop.entity.CartItem;
import com.example.shop.entity.User;
import com.example.shop.security.LoginDetails;
import com.example.shop.service.CartItemService;
import com.example.shop.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

    private final CartItemService cartItemService;

    public ShoppingCartController(CartItemService cartItemService){
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public String shoppingCart(Model model,
                               @AuthenticationPrincipal LoginDetails loginDetails){

        User user = loginDetails.getUser();
        List<CartItem> cartItems = cartItemService.findCartItemByUser(user);

        model.addAttribute("cartItems", cartItems);

        return "shopping-cart";
    }

}
