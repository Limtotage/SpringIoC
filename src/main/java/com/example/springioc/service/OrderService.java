package com.example.springioc.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springioc.entity.Cart;
import com.example.springioc.entity.CartItem;
import com.example.springioc.entity.Customer;
import com.example.springioc.entity.Order;
import com.example.springioc.repository.OrderRepo;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Transactional
    public void saveOrder(Cart cart, Customer customer) {
        Order order = new Order();
        order.setCustomer(customer);

        Set<CartItem> purchasedItems = new HashSet<>();

        for (CartItem item : cart.getItems()) {
            CartItem copy = new CartItem();
            copy.setProduct(item.getProduct());
            copy.setQuantity(item.getQuantity());
            copy.setItemTotal(item.getSubtotal());
            copy.setCompleted(true);

            item.setCompleted(true);
            copy.setCart(cart); // 🔥 BU SATIR EKSİKSE HATA ALIRSIN
            copy.setOrder(order);
            purchasedItems.add(copy);
        }

        order.setOrderHistory(purchasedItems);
        order.setOrderDate(LocalDateTime.now());
        orderRepo.save(order);
    }
}
