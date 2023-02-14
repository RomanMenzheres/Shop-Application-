package com.example.shop.service;

import com.example.shop.entity.Category;

import java.util.List;

public interface CategoryService {

    Category create(Category category);

    Category readById(long id);

    Category update(Category category);

    void delete(long id);

    List<Category> getAll();

}
