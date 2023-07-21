package com.example.shop.controller;

import com.example.shop.entity.Product;
import com.example.shop.service.CategoryService;
import com.example.shop.service.Implementation.FileService;
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
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    private final FileService fileService;

    public ProductController(ProductService productService,
                             CategoryService categoryService,
                             FileService fileService){
        this.productService = productService;
        this.categoryService = categoryService;
        this.fileService= fileService;
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("admin", false);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("product", new Product());
        return "create-product";
    }

    @GetMapping("/admin/create")
    public String adminCreate(Model model){
        model.addAttribute("admin", true);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("product", new Product());
        return "create-product";
    }

    @PostMapping("/create")
    public String create(@Validated @ModelAttribute("product") Product product,
                         BindingResult bindingResult,
                         Model model,
                         @RequestParam("filename")MultipartFile file,
                         @RequestParam("admin") String admin) throws IOException {
        if (bindingResult.hasErrors()){
            model.addAttribute("categories", categoryService.getAll());
            return "create-product";
        }

        if (!file.isEmpty()){
            fileService.create(product.getName(), file);
        }

        productService.create(product);

        if (admin.equals("admin")){
            return "redirect:/admin/products";
        } else {
            return "redirect:/menu";
        }
    }

    @GetMapping("/{product_id}/update")
    public String update(@PathVariable("product_id") long product_id, Model model){
        model.addAttribute("admin", false);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("product", productService.readById(product_id));
        return "update-product";
    }

    @GetMapping("/admin/{product_id}/update")
    public String adminUpdate(@PathVariable("product_id") long product_id, Model model){
        model.addAttribute("admin", true);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("product", productService.readById(product_id));
        return "update-product";
    }

    @PostMapping("/{product_id}/update")
    public String update(@PathVariable("product_id") long product_id,
                         @Validated @ModelAttribute("product") Product product,
                         BindingResult bindingResult,
                         Model model,
                         @RequestParam("filename")MultipartFile file,
                         @RequestParam("admin") String admin) throws IOException {
        if (bindingResult.hasErrors()){
            System.out.println(bindingResult);
            model.addAttribute("categories", categoryService.getAll());
            return "update-product";
        }

        if (!file.isEmpty()){
            fileService.delete(productService.readById(product_id).getName());
            fileService.create(product.getName(), file);
        }

        product.setId(product_id);
        productService.update(product);

        System.out.println(admin);

        if (admin.equals("admin")){
            return "redirect:/admin/products";
        } else {
            return "redirect:/menu";
        }
    }
}
