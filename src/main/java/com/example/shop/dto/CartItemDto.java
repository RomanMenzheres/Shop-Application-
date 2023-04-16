package com.example.shop.dto;

public class CartItemDto {

    private long id;

    private ProductDto product;

    private int quantity;

    private long orderId;

    public CartItemDto(){}

    public CartItemDto(long id, ProductDto product, long orderId, int quantity){
        this.id = id;
        this.product = product;
        this.orderId = orderId;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ProductDto getProductId() {
        return product;
    }

    public void setProductId(ProductDto product) {
        this.product = product;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
