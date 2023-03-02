package com.example.shop.dto;

public class CartItemDto {

    private long id;

    private long productId;

    private long userId;

    private int quantity;

    public CartItemDto(){}

    public CartItemDto(long id, long productId, long userId, int quantity){
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
