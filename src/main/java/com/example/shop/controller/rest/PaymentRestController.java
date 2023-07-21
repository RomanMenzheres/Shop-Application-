package com.example.shop.controller.rest;

import com.example.shop.entity.Transaction;
import com.example.shop.security.LoginDetails;
import com.example.shop.service.OrderService;
import com.example.shop.service.TransactionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payment")
public class PaymentRestController {

    private final TransactionService transactionService;
    private final OrderService orderService;

    public PaymentRestController(TransactionService transactionService, OrderService orderService){
        this.transactionService = transactionService;
        this.orderService = orderService;
    }

    @PostMapping
    public String payment(@RequestParam("transactionId") String transactionId,
                          @RequestParam("status") String status,
                          @RequestParam("createdTime") String createdTime,
                          @RequestParam("endTime") String endTime,
                          @AuthenticationPrincipal LoginDetails loginDetails){

        Transaction transaction = new Transaction();

        if (transactionId.toCharArray().length > 254){
            transactionId = transactionId.substring(0, 254);
        }

        transaction.setId(transactionId);
        transaction.setStatus(status);
        transaction.setCreationDate(transactionService.parseToLocalDateTime(createdTime));
        transaction.setEndDate(transactionService.parseToLocalDateTime(endTime));
        transaction.setOrder(orderService.findOrderForPaymentByUser(loginDetails.getUser()));

        transactionService.create(transaction);

        return "success";
    }
}
