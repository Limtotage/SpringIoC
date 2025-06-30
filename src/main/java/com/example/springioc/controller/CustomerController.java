package com.example.springioc.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springioc.entity.Customer;
import com.example.springioc.service.CustomerService;

import jakarta.validation.constraints.Email;

@RestController
@Validated
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> GetAllCustomers(){
        return ResponseEntity.ok(customerService.GetAllCustomers());
    }

    @PostMapping("/register")
    public ResponseEntity<Customer> RegisterCustomer(@RequestBody Customer customer){
        return ResponseEntity.ok(customerService.RegisterCustomer(customer));
    }
    @GetMapping("/by-email")//Eğer geçerli değilse, Spring otomatik olarak 400 Bad Request döner.
    public ResponseEntity<Optional<Customer>> GetCustomerByEmail(@RequestParam @Email String email){
        return ResponseEntity.ok(customerService.GetCustomerByEmail(email));
    }
}
