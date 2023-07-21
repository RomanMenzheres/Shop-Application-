package com.example.shop.dto;

import com.example.shop.entity.enums.PaymentMethod;
import com.example.shop.entity.enums.Status;

import java.util.List;

public class OrderDto {

    private long id;
    private float price;
    private String address;
    private String phone;
    private Status status;
    private PaymentMethod paymentMethod;
    private String creationDate;
    private String deliveryDate;
    private String cancelDate;
    private String comment;
    private UserDto owner;
    private List<CartItemDto> products;

    public OrderDto(long id, float price, String address, String phone, Status status, PaymentMethod paymentMethod, String creationDate, String deliveryDate, String cancelDate, String comment, UserDto owner, List<CartItemDto> products) {
        this.id = id;
        this.price = price;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.creationDate = creationDate;
        this.deliveryDate = deliveryDate;
        this.cancelDate = cancelDate;
        this.comment = comment;
        this.owner = owner;
        this.products = products;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }

    public List<CartItemDto> getProducts() {
        return products;
    }

    public void setProducts(List<CartItemDto> products) {
        this.products = products;
    }
}
