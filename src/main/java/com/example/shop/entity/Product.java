package com.example.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @NotBlank(message = "The 'name' cannot be empty")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "price")
    private float price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Product(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + name + '\'' +
                ", price=" + price +
                ", category=" + category.getName() +
                '}';
    }
}
