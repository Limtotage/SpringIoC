package com.example.springioc.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springioc.dto.CustomerDTO;
import com.example.springioc.service.CustomerService;

import jakarta.validation.constraints.Email;

@RestController
@Validated
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDTO> CreateCustomer(@RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.ok(customerService.RegisterCustomer(customerDTO));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> GetAllCustomers(){
        return ResponseEntity.ok(customerService.GetAllCustomers());
    }
    @GetMapping("/by-email")//Eğer geçerli değilse, Spring otomatik olarak 400 Bad Request döner.
    public ResponseEntity<Optional<CustomerDTO>> GetCustomerByEmail(@RequestParam @Email String email){
        return ResponseEntity.ok(customerService.GetCustomerByEmail(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> UpdateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.ok(customerService.UpdateCustomer(id, customerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteCustomer(@PathVariable Long id) {
        customerService.DeleteCustomer(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }
}
