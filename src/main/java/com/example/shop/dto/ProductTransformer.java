package com.example.shop.dto;

import com.example.shop.entity.Product;

public class ProductTransformer {

    public static ProductDto convertToDto(Product product){
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice()
        );
    }

}
