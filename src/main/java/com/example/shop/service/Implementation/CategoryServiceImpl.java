package com.example.shop.service.Implementation;

import com.example.shop.entity.Category;
import com.example.shop.entity.Product;
import com.example.shop.entity.Role;
import com.example.shop.repository.CategoryRepository;
import com.example.shop.repository.RoleRepository;
import com.example.shop.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service("CategoryService")
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(Category category) {
        if (category != null) {
            return categoryRepository.save(category);
        }
        throw new NullPointerException("Category cannot be 'null'");
    }

    @Override
    public Category readById(long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Category with id " + id + " not found"));
    }

    @Override
    public Category update(Category category) {
        if (category != null) {
            readById(category.getId());
            return categoryRepository.save(category);
        }
        throw new NullPointerException("Category cannot be 'null'");
    }

    @Override
    public void delete(long id) {
        categoryRepository.delete(readById(id));
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> getSortedAll(){
        return categoryRepository.findAll().stream()
                .sorted(Comparator.comparing(Category::getName))
                .toList();
    }

}
