package com.example.shop.entity;

import com.example.shop.entity.enums.PaymentMethod;
import com.example.shop.entity.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "price")
    private float price;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "comment")
    private String comment;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    @Column(name = "cancel_date")
    private LocalDateTime cancelDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<CartItem> products;

    public Order() {
        status = Status.OPEN;
    }

    public List<CartItem> getProducts() {
        return products;
    }

    public void setProducts(List<CartItem> products) {
        this.products = products;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getDateAsString(LocalDateTime date){
        if (date == null){
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.format(formatter);
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDateTime getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(LocalDateTime cancelDate) {
        this.cancelDate = cancelDate;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", price=" + price +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", comment='" + comment + '\'' +
                ", status=" + status +
                ", paymentMethod=" + paymentMethod +
                ", creationDate=" + creationDate +
                ", owner=" + owner.getEmail() +
                ", products=" + products.stream().map(CartItem::getProduct).map(Product::getName).toList() +
                '}';
    }
}
