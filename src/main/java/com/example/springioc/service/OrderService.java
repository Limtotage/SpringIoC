package com.example.springioc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springioc.entity.CustomerOrder;
import com.example.springioc.repository.CustomerOrderRepo;

@Service
public class OrderService {
    @Autowired
    private CustomerOrderRepo orderDB;

    public List<CustomerOrder> GetOrderByCustomerID(Long customerID){
        return orderDB.findByCustomerId(customerID);
    }
    public Optional<CustomerOrder> GetOrderById(Long ID){
        return orderDB.findById(ID);
    }
    public CustomerOrder CreateOrder(CustomerOrder entity){
        return orderDB.save(entity);
    }
}
