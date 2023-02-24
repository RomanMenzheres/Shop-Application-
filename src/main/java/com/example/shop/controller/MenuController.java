package com.example.shop.controller;

import com.example.shop.entity.Category;
import com.example.shop.entity.Product;
import com.example.shop.service.CategoryService;
import com.example.shop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

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
    public String menu(Model model){
        model.addAttribute("selectedCategory", null);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("products", productService.getAll());
        return "menu";
    }

    @GetMapping("/{category_id}")
    private String sortedMenu(Model model, @PathVariable("category_id") long category_id){
        Category category = categoryService.readById(category_id);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("products", productService.getAllByCategory(category));
        return "menu";
    }
}
