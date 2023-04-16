package com.example.shop.controller.rest;

import com.example.shop.dto.OrderDto;
import com.example.shop.dto.OrderTransformer;
import com.example.shop.entity.Order;
import com.example.shop.entity.enums.Status;
import com.example.shop.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("order")
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/active")
    public List<OrderDto> getActiveOrders(){
        return orderService.findOrdersForConfirmation().stream().map(OrderTransformer::convertToDto).toList();
    }

    @GetMapping("/confirmed")
    public List<OrderDto> getConfirmedOrders(){
       return orderService.findConfirmedOrders().stream().map(OrderTransformer::convertToDto).toList();
    }

    @GetMapping("/finished")
    public List<OrderDto> getFinishedOrders(){
        return orderService.findFinishedOrders().stream().map(OrderTransformer::convertToDto).toList();
    }

    @PostMapping("/confirm/{id}")
    public String confirm(@PathVariable("id") long orderId){

        Order order = orderService.readById(orderId);

        String statusBeforeConfirmation = order.getStatus().toString();

        if (order.getStatus().equals(Status.CONFIRMED)){
            order.setDeliveryDate(LocalDateTime.now());
            order.setStatus(Status.DELIVERED);
        } else {
            order.setStatus(Status.CONFIRMED);
        }

        orderService.update(order);

        return "Order with id " + orderId + " was successfully gain status " + order.getStatus() + ", status before confirmation " + statusBeforeConfirmation;
    }

    @PostMapping("/cancel/{id}")
    public String cancel(@PathVariable("id") long orderId){

        Order order = orderService.readById(orderId);

        String statusBeforeCancellation = order.getStatus().toString();

        order.setCancelDate(LocalDateTime.now());
        order.setStatus(Status.CANCELED);

        orderService.update(order);

        return "Order with id " + orderId + " was successfully canceled, status before cancellation " + statusBeforeCancellation;
    }

    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable("id") long id){
        return OrderTransformer.convertToDto(orderService.readById(id));
    }

}
