package com.example.springioc.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springioc.dto.PaymentRequestDTO;
import com.example.springioc.dto.PaymentResponseDTO;
import com.example.springioc.entity.Cart;
import com.example.springioc.entity.CartItem;
import com.example.springioc.entity.Customer;
import com.example.springioc.entity.Payment;
import com.example.springioc.entity.Product;
import com.example.springioc.entity.Stock;
import com.example.springioc.repository.CartRepo;
import com.example.springioc.repository.CustomerRepo;
import com.example.springioc.repository.PaymentRepo;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private CustomerRepo customerDB;

    @Autowired
    private PaymentRepo paymentDB;

    @Autowired
    private CartRepo cartDB;

    @PostMapping("/{customerId}")
    @Transactional
    public ResponseEntity<?> processPayment(
            @PathVariable Long customerId,
            @RequestBody PaymentRequestDTO paymentRequest) {

        Customer customer = customerDB.findByUser_Id(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Cart cart = customer.getCart();
        if (cart == null || cart.getItems().isEmpty()) {
            return ResponseEntity.badRequest().body("Cart is empty");
        }

        Payment payment = new Payment();
        payment.setCardNumber(paymentRequest.getCardNumber());
        payment.setCardHolderName(paymentRequest.getCardHolderName());
        payment.setExpiryMonth(paymentRequest.getExpiryMonth());
        payment.setExpiryYear(paymentRequest.getExpiryYear());
        payment.setCvc(paymentRequest.getCvc());
        payment.setPaymentDate(LocalDateTime.now());

        // Copy cart items and calculate total
        payment.setPurchasedItems(new ArrayList(cart.getItems()));
        payment.setTotalPaid(cart.getTotalPrice());

        // Stock güncelleme
        for (CartItem item : cart.getItems()) {
            Stock stock = item.getProduct().getStock();
            stock.setStockQuantity(stock.getStockQuantity() - item.getQuantity());
        }

        // Sepeti sıfırla
        cart.getItems().clear();
        cart.setTotalPrice(0.0);
        cartDB.save(cart);

        paymentDB.save(payment);

        PaymentResponseDTO response = new PaymentResponseDTO();
        // response.setMessage("Payment successful");
        // response.setPaymentDate(payment.getPaymentDate());
        // response.setTotalPaid(payment.getTotalPaid());
        return ResponseEntity.ok(response);
    }
}
