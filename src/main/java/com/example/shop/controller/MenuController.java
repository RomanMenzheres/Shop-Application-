package com.example.shop.controller;

import com.example.shop.dto.CartItemDto;
import com.example.shop.entity.Category;
import com.example.shop.security.LoginDetails;
import com.example.shop.service.CategoryService;
import com.example.shop.service.ProductService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu")
public class MenuController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public MenuController(ProductService productService, CategoryService categoryService){
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String menu(Model model, @AuthenticationPrincipal LoginDetails loginDetails){
        model.addAttribute("selectedCategory", null);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("products", productService.getAll());
        model.addAttribute("cartItem", new CartItemDto());
        model.addAttribute("currentlyLoggedInUserId", loginDetails.getUser().getId());
        return "menu";
    }

    @GetMapping("/{category_id}")
    private String sortedMenu(Model model,
                              @AuthenticationPrincipal LoginDetails loginDetails,
                              @PathVariable("category_id") long category_id){
        Category category = categoryService.readById(category_id);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("products", productService.getAllByCategory(category));
        model.addAttribute("cartItem", new CartItemDto());
        model.addAttribute("currentlyLoggedInUserId", loginDetails.getUser().getId());
        return "menu";
    }
}
