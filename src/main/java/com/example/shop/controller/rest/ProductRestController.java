package com.example.shop.controller.rest;

import com.example.shop.entity.Product;
import com.example.shop.service.Implementation.FileService;
import com.example.shop.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("product")
public class ProductRestController {

    private final ProductService productService;
    private final FileService fileService;

    public ProductRestController(ProductService productService, FileService fileService){
        this.productService = productService;
        this.fileService = fileService;
    }

    @GetMapping("/{product_id}/delete")
    public String delete(@PathVariable("product_id") long product_id) throws IOException {
        Product product = productService.readById(product_id);

        fileService.delete(product.getName());

        productService.delete(product_id);
        return "Product with id " + product_id + " successfully deleted";
    }

}
