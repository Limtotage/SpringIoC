package com.example.springioc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springioc.dto.OrderDTO;
import com.example.springioc.dto.PaymentDTO;
import com.example.springioc.entity.Cart;
import com.example.springioc.entity.Customer;
import com.example.springioc.entity.Order;
import com.example.springioc.repository.CustomerRepo;
import com.example.springioc.repository.OrderRepo;
import com.example.springioc.service.CartService;
import com.example.springioc.service.OrderService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerRepo customerDB;
    @Autowired
    private OrderRepo orderDB;
    @Autowired
    private CartService cartService;

    @PostMapping("/{customerId}")
    @Transactional
    public ResponseEntity<PaymentDTO> pay(@PathVariable Long customerId, @RequestBody PaymentDTO dto) {
        Customer customer = customerDB.findByUser_Id(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        Cart cart = customer.getCart();

        if (cart == null || cart.getItems().isEmpty()) {
            dto.setMessage("Cart is empty");
            return ResponseEntity.badRequest().body(dto);
        }
        OrderDTO orderDTO = orderService.createOrderFromCart( customer);
        Order order = orderService.convertToEntity(orderDTO);
        orderDB.save(order);
        dto.setMessage("Ödeme başarıyla tamamlandı");
        cartService.ConfirmCart(cart);
        return ResponseEntity.ok(dto);
    }
}
