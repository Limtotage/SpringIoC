package com.example.springioc.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springioc.dto.OrderDTO;
import com.example.springioc.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/orders")
public class OrderController {
    private final OrderService orderService;
    
    @GetMapping("/{id}")
    public ResponseEntity<List<OrderDTO>> GetAllOrders(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getOrdersByCustomerId(id));
    }
}
