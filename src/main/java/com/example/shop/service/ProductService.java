package com.example.shop.service;

import com.example.shop.entity.Product;

import java.util.List;

public interface ProductService {

    Product create(Product product);

    Product readById(long id);

    Product update(Product product);

    void delete(long id);

    List<Product> getAll();

}
