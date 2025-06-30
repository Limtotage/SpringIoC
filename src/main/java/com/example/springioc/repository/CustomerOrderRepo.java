package com.example.springioc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springioc.entity.CustomerOrder;


public interface CustomerOrderRepo extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByCustomerId(Long customerId);
}
