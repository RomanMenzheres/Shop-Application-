package com.example.shop.controller;

import com.example.shop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    public AdminController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/orders")
    public String orders(Model model){
        model.addAttribute("clicked", "orders");

        return "admin-orders";
    }

    @GetMapping("/products")
    public String products(Model model){
        model.addAttribute("clicked", "products");
        model.addAttribute("products", productService.getAll());

        return "admin-products";
    }
}
