package com.example.springioc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springioc.entity.Customer;
import com.example.springioc.repository.CustomerRepo;


@Service
public class CustomerService {
    @Autowired
    private CustomerRepo customerDB;

    public Optional<Customer> GetCustomerByEmail(String email){
        return customerDB.findByEmail(email);
    }
    public List<Customer> GetAllCustomers(){
        return customerDB.findAll();
    }
    public Customer RegisterCusstomer(Customer entity){
        return customerDB.save(entity);
    }
}
