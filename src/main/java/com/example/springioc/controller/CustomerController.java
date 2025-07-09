package com.example.springioc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springioc.dto.CustomerDTO;
import com.example.springioc.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    @PostMapping
    public ResponseEntity<CustomerDTO> CreateCustomer(@RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.ok(customerService.RegisterCustomer(customerDTO));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> GetAllCustomers() {
        return ResponseEntity.ok(customerService.GetAllCustomers());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<CustomerDTO> UpdateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.ok(customerService.UpdateCustomer(id, customerDTO));
    }

    @PutMapping("/addProduct/{customerId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<?> AddProducts(@PathVariable Long customerId, @RequestBody List<Long> productIds) {
        customerService.AddProductToCustomer(customerId, productIds);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/removeProduct/{customerId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<?> RemoveProducts(@PathVariable Long customerId, @RequestBody List<Long> productIds) {
        customerService.RemoveProductFromCustomer(customerId, productIds);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<String> DeleteCustomer(@PathVariable Long id) {
        customerService.DeleteCustomer(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }
}
