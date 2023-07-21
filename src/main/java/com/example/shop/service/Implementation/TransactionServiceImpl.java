package com.example.shop.service.Implementation;

import com.example.shop.entity.Transaction;
import com.example.shop.repository.TransactionRepository;
import com.example.shop.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service("TransactionService")
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }


    @Override
    public Transaction create(Transaction transaction) {
        if (transaction != null) {
            return transactionRepository.save(transaction);
        }
        throw new NullPointerException("Transaction cannot be 'null'");
    }

    @Override
    public Transaction readById(String id) {
        return transactionRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Transaction with id " + id + " not found"));
    }

    @Override
    public void delete(String id) {
        transactionRepository.delete(readById(id));
    }

    @Override
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @Override
    public LocalDateTime parseToLocalDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("Europe/Kiev"));
        return zonedDateTime.toLocalDateTime();
    }
}
