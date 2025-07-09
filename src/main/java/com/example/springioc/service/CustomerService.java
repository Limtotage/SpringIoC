package com.example.springioc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springioc.components.AuthComponents;
import com.example.springioc.dto.CustomerDTO;
import com.example.springioc.entity.Customer;
import com.example.springioc.entity.MyUser;
import com.example.springioc.entity.Product;
import com.example.springioc.repository.ProductRepo;
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
    private ProductRepo productDB;

    @Autowired
    private CustomerMapper mapper;

    @Autowired
    private AuthComponents authComponents;

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
        boolean isAdmin = authComponents.isAdmin();
        Long userId = authComponents.getCurrentUserId();
        Customer ownerSeller = customerDB.findByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found for user ID: " + userId));
        Customer targetCustomer = customerDB.findById(Id)
                .orElseThrow(() -> new EntityNotFoundException("Customer Not Found"));

        if (!targetCustomer.getId().equals(ownerSeller.getId()) && !isAdmin) {
            throw new EntityNotFoundException("You can only update your own Customer account");
        } else {
            MyUser existUser = targetCustomer.getUser();
            existUser.setFullname(dto.getFullname());
            existUser.setUsername(dto.getUsername());
            List<Product> updatedProducts = dto.getProductsIds().stream()
                        .map(productId-> productDB.findById(productId)
                        .orElseThrow(() -> new EntityNotFoundException("Product not found for ID: " + productId)))
                        .toList();
            targetCustomer.getProducts().clear();
            targetCustomer.getProducts().addAll(updatedProducts);
            return mapper.toDTO(customerDB.save(targetCustomer));
        }
    }

    public void DeleteCustomer(Long id) {
        boolean isAdmin = authComponents.isAdmin();
        Long currentUserId = authComponents.getCurrentUserId();

        // Silinecek customer'ı user_id'ye göre bul
        Customer customerToDelete = customerDB.findByUser_Id(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found for user ID: " + id));

        // Eğer kullanıcı kendi hesabını siliyorsa veya admin ise izin ver
        if (!currentUserId.equals(id) && !isAdmin) {
            throw new EntityNotFoundException("You can only delete your own customer account");
        }

        customerDB.delete(customerToDelete);
    }

}
