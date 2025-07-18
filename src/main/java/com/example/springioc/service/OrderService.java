package com.example.springioc.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springioc.dto.OrderDTO;
import com.example.springioc.dto.OrderItemDTO;
import com.example.springioc.entity.CartItem;
import com.example.springioc.entity.Customer;
import com.example.springioc.entity.Order;
import com.example.springioc.entity.OrderItemEmbedded;
import com.example.springioc.repository.CustomerRepo;


@Service
public class OrderService {
    @Autowired
    private CustomerRepo customerDB;

    public OrderDTO createOrderFromCart(Customer customer) {
        List<CartItem> cartItems = customer.getCart().getItems().stream()
                .toList();
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Sepet boş.");
        }

        List<OrderItemDTO> orderItems = new ArrayList<>();
        Double total = 0.0;

        for (CartItem item : cartItems) {
            OrderItemDTO dto = new OrderItemDTO();
            dto.setProductName(item.getProduct().getName());
            dto.setQuantity(item.getQuantity());
            dto.setSubtotal(item.getSubtotal());

            orderItems.add(dto);
            total = total + item.getSubtotal();
        }

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderDate(LocalDateTime.now());
        orderDTO.setItems(orderItems);
        orderDTO.setTotalPrice(total);
        orderDTO.setCustomerId(customer.getId());

        return orderDTO;
    }

    public Order convertToEntity(OrderDTO dto) {
        Order order = new Order();
        order.setOrderDate(dto.getOrderDate());
        order.setTotalPrice(dto.getTotalPrice());

        List<OrderItemEmbedded> embeddedItems = dto.getItems().stream().map(itemDTO -> {
            OrderItemEmbedded embedded = new OrderItemEmbedded();
            embedded.setProductName(itemDTO.getProductName());
            embedded.setQuantity(itemDTO.getQuantity());
            embedded.setSubtotal(itemDTO.getSubtotal());
            return embedded;
        }).collect(Collectors.toList());

        order.setItems(embeddedItems);
        order.setCustomer(customerDB.findById(dto.getCustomerId()).orElse(null));

        return order;
    }

}
