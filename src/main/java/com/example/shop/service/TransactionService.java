package com.example.shop.service;

import com.example.shop.entity.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    Transaction create(Transaction transaction);

    Transaction readById(String id);

    void delete(String id);

    List<Transaction> getAll();

    LocalDateTime parseToLocalDateTime(String date);

}
