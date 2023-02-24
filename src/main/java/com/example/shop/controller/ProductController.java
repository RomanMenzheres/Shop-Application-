package com.example.shop.controller;

import com.example.shop.entity.Product;
import com.example.shop.service.CategoryService;
import com.example.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${productsImage.path}")
    private String productsImagePath;

    public ProductController(ProductService productService, CategoryService categoryService){
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/{product_id}/delete")
    public String delete(@PathVariable("product_id") long product_id){
        productService.delete(product_id);
        return "redirect:/menu";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("product", new Product());
        return "create-product";
    }

    @PostMapping("/create")
    public String create(@Validated @ModelAttribute("product") Product product,
                         BindingResult bindingResult,
                         Model model,
                         @RequestParam("filename")MultipartFile file) throws IOException {
        if (bindingResult.hasErrors()){
            model.addAttribute("categories", categoryService.getAll());
            return "create-product";
        }

        if (!file.isEmpty()){
            File uploadFolder = new File(productsImagePath);

            if (!uploadFolder.exists()){
                uploadFolder.mkdir();
            }

            String originFileName = file.getOriginalFilename();

            String fileName = product.getName() + originFileName.substring(originFileName.lastIndexOf('.'));

            file.transferTo(new File(productsImagePath + "/" +fileName));
        }

        productService.create(product);
        return "redirect:/menu";
    }

}
