package com.example.springioc.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springioc.dto.CustomerDTO;
import com.example.springioc.entity.Customer;
import com.example.springioc.mapper.CustomerMapper;
import com.example.springioc.repository.CustomerRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {
    @Autowired
    private CustomerRepo customerDB;
    
    @Autowired
    private CustomerMapper mapper;

    public Optional<CustomerDTO> GetCustomerByEmail(String email) {
        return customerDB.findByEmail(email).map(mapper::toDTO);
    }

    public List<CustomerDTO> GetAllCustomers() {
        return customerDB.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public CustomerDTO GetCustomerByID(Long Id) {
        Customer customer = customerDB.findById(Id)
                .orElseThrow(() -> new EntityNotFoundException("Customer Not Found"));
        return mapper.toDTO(customer);
    }

    public CustomerDTO RegisterCustomer(CustomerDTO customerDTO) {
        Customer customer = mapper.toEntity(customerDTO);
        Customer saved = customerDB.save(customer);
        return mapper.toDTO(saved);
    }

    public CustomerDTO UpdateCustomer(Long Id, CustomerDTO dto) {
        Customer existCustomer = customerDB.findById(Id)
                .orElseThrow(() -> new EntityNotFoundException("Customer Not Found"));
        existCustomer.setFullname(dto.getFullname());
        existCustomer.setEmail(dto.getEmail());
        existCustomer.setPhone(dto.getPhone());
        existCustomer.setAddress(dto.getAddress());
        existCustomer.setUpdatedAt(LocalDateTime.now());
        return mapper.toDTO(customerDB.save(existCustomer));
    }

    public void DeleteCustomer(Long Id) {
        customerDB.deleteById(Id);
    }
}
