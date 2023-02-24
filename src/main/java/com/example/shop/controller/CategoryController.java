package com.example.shop.controller;

import com.example.shop.entity.Category;
import com.example.shop.entity.Product;
import com.example.shop.service.CategoryService;
import com.example.shop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("/{category_id}/delete")
    public String delete(@PathVariable("category_id") long category_id){
        categoryService.delete(category_id);
        return "redirect:/menu";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("category", new Category());
        return "create-category";
    }

    @PostMapping("/create")
    public String create(@Validated @ModelAttribute("category") Category category, BindingResult bindingResult)  {
        if (bindingResult.hasErrors()){
            return "create-category";
        }

        categoryService.create(category);
        return "redirect:/menu";
    }
}
