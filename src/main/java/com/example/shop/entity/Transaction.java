package com.example.shop.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    private String id;

    @Column(name = "status")
    private String status;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public Transaction(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", creationDate=" + creationDate +
                ", endDate=" + endDate +
                ", order=" + order +
                '}';
    }
}
