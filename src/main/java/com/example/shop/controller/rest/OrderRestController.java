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

    @GetMapping("/delivering")
    public List<OrderDto> getDeliveringOrders(){
        return orderService.findDeliveringOrders().stream().map(OrderTransformer::convertToDto).toList();
    }

    @GetMapping("/finished")
    public List<OrderDto> getFinishedOrders(){
        return orderService.findFinishedOrders().stream().map(OrderTransformer::convertToDto).toList();
    }

    @PostMapping("/confirm/{id}")
    public String confirm(@PathVariable("id") long orderId){

        Order order = orderService.readById(orderId);

        String orderStatus = order.getStatus().toString();

        if (orderStatus.equals("CONFIRMED")){
            order.setDeliveryDate(LocalDateTime.now());
            order.setStatus(Status.DELIVERING);
        } else if (orderStatus.equals("PROCESSING") ||
                orderStatus.equals("PAID") ||
                orderStatus.equals("UPDATED")){
            order.setStatus(Status.CONFIRMED);
        } else if (orderStatus.equals("DELIVERING")){
            order.setStatus(Status.DELIVERED);
        } else {
            return "Order with id " + orderId + " can not be confirmed!";
        }

        orderService.update(order);

        return "Order with id " + orderId + " was successfully gain status " + order.getStatus() + ", status before confirmation " + orderStatus;
    }

    @PostMapping("/cancel/{id}")
    public String cancel(@PathVariable("id") long orderId){

        Order order = orderService.readById(orderId);

        String orderStatus = order.getStatus().toString();

        if (orderStatus.equals("OPEN") ||
                orderStatus.equals("DELIVERED") ||
                orderStatus.equals("CANCELED")){
            return "Order with id " + orderId + " can not be cancelled, It's status is " + orderStatus;
        }

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
