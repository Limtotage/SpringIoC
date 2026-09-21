package com.example.springioc.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springioc.components.AuthComponents;
import com.example.springioc.dto.OrderDTO;
import com.example.springioc.dto.OrderItemDTO;
import com.example.springioc.entity.CartItem;
import com.example.springioc.entity.Customer;
import com.example.springioc.entity.Order;
import com.example.springioc.entity.OrderItemEmbedded;
import com.example.springioc.repository.CustomerRepo;
import com.example.springioc.repository.OrderRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService {
    @Autowired
    private CustomerRepo customerDB;
    @Autowired
    private AuthComponents authComponents;

    @Autowired
    private OrderRepo orderDB;

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

    public List<OrderDTO> getOrdersByCustomerId(Long customerId) {
        boolean isAdmin = authComponents.isAdmin();
        List<Order> orders;
        if (isAdmin) {
            orders = orderDB.findByCustomerId(customerId);
        }
        else{
            Long userId = authComponents.getCurrentUserId();
            Customer customer = customerDB.findByUser_Id(userId).orElseThrow(()->(new EntityNotFoundException("Customer Not Found")));
            orders=orderDB.findByCustomerId(customer.getId());
        }

        return orders.stream().map(order -> {
            OrderDTO dto = new OrderDTO();
            dto.setId(order.getId());
            dto.setOrderDate(order.getOrderDate());
            dto.setTotalPrice(order.getTotalPrice());
            dto.setCustomerId(order.getCustomer().getId());

            List<OrderItemDTO> items = order.getItems().stream().map(item -> {
                OrderItemDTO itemDTO = new OrderItemDTO();
                itemDTO.setProductName(item.getProductName());
                itemDTO.setQuantity(item.getQuantity());
                itemDTO.setSubtotal(item.getSubtotal());
                return itemDTO;
            }).toList();

            dto.setItems(items);

            return dto;
        }).toList();

    }

}
