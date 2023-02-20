package com.example.shop.service.Implementation;

import com.example.shop.entity.Category;
import com.example.shop.entity.Product;
import com.example.shop.repository.ProductRepository;
import com.example.shop.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("ProductService")
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product product) {
        if (product != null) {
            return productRepository.save(product);
        }
        throw new NullPointerException("Role cannot be 'null'");
    }

    @Override
    public Product readById(long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Product with id " + id + " not found"));
    }

    @Override
    public Product update(Product product) {
        if (product != null) {
            readById(product.getId());
            return productRepository.save(product);
        }
        throw new NullPointerException("Product cannot be 'null'");
    }

    @Override
    public void delete(long id) {
        productRepository.delete(readById(id));
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllByCategory(Category category) {
        return productRepository.findAll().stream()
                .filter(product -> product.getCategory().equals(category))
                .collect(Collectors.toList());
    }
}
