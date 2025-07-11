package com.example.springioc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springioc.components.AuthComponents;
import com.example.springioc.dto.CustomerDTO;
import com.example.springioc.entity.Customer;
import com.example.springioc.entity.MyUser;
import com.example.springioc.entity.Product;
import com.example.springioc.mapper.CustomerMapper;
import com.example.springioc.repository.CustomerRepo;
import com.example.springioc.repository.ProductRepo;

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
        Customer ownerCustomer = customerDB.findByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found for user ID: " + userId));
        Customer targetCustomer = customerDB.findById(Id)
                .orElseThrow(() -> new EntityNotFoundException("Customer Not Found"));

        if (!targetCustomer.getId().equals(ownerCustomer.getId()) && !isAdmin) {
            throw new EntityNotFoundException("You can only update your own Customer account");
        } else {
            MyUser existUser = targetCustomer.getUser();
            existUser.setFullname(dto.getFullname());
            existUser.setUsername(dto.getUsername());
            List<Product> updatedProducts = dto.getProductsIds().stream()
                    .map(productId -> productDB.findById(productId)
                            .orElseThrow(() -> new EntityNotFoundException("Product not found for ID: " + productId)))
                    .toList();
            targetCustomer.getProducts().clear();
            targetCustomer.getProducts().addAll(updatedProducts);
            return mapper.toDTO(customerDB.save(targetCustomer));
        }
    }

    @Transactional
    public void AddProductToCustomer(Long userID, List<Long> productIds) {
        boolean isAdmin = authComponents.isAdmin();
        Long userId = authComponents.getCurrentUserId();
        Customer customer = customerDB.findByUser_Id(userID)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found for ID: " + userID));
        System.out.println("Current User ID: " + userId + ", Customer User ID: " + userID);
        if (!userId.equals(customer.getUser().getId()) && !isAdmin) {
            throw new EntityNotFoundException("You can only update your own Customer account");
        }
        List<Product> products = productDB.findAllById(productIds);

        for (Product product : products) {
            product.getCustomers().add(customer);
            customer.getProducts().add(product);
        }
        productDB.saveAll(products);
    }

    @Transactional
    public void RemoveProductFromCustomer(Long userID, List<Long> productIdsToRemove) {
        boolean isAdmin = authComponents.isAdmin();
        Long userId = authComponents.getCurrentUserId();
        Customer customer = customerDB.findByUser_Id(userID)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found for ID: " + userID));
        System.out.println("Current User ID: " + userId + ", Customer User ID: " + userID);
        if (!userId.equals(customer.getUser().getId()) && !isAdmin) {
            throw new EntityNotFoundException("You can only update your own Customer account");
        }
        Set<Product> productsToRemove = customer.getProducts().stream()
                .filter(p -> productIdsToRemove.contains(p.getId()))
                .collect(Collectors.toSet());
        customer.getProducts().removeAll(productsToRemove);
        for (Product product : productsToRemove) {
            product.getCustomers().remove(customer);
        }
        customerDB.save(customer);
    }

    @Transactional
    public void DeleteCustomer(Long id) {
        boolean isAdmin = authComponents.isAdmin();
        Long currentUserId = authComponents.getCurrentUserId();

        Customer customerToDelete = customerDB.findByUser_Id(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found for user ID: " + id));

        if (!currentUserId.equals(id) && !isAdmin) {
            throw new EntityNotFoundException("You can only delete your own customer account");
        }
        if (customerToDelete.getProducts() != null && !customerToDelete.getProducts().isEmpty()) {
            for (Product product : new ArrayList<>(customerToDelete.getProducts())) {
                product.getCustomers().remove(customerToDelete); // çift yönlü ilişki siliniyor
            }
            customerToDelete.getProducts().clear(); // tek yönlü ilişki temizleniyor
            customerDB.save(customerToDelete); // flush öncesi durum
        }

        customerDB.delete(customerToDelete);
    }

    @Transactional
    public void DeleteCustomerAdmin(Long id) {
        Customer customerToDelete = customerDB.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found for user ID: " + id));

        if (customerToDelete.getProducts() != null && !customerToDelete.getProducts().isEmpty()) {
            for (Product product : new ArrayList<>(customerToDelete.getProducts())) {
                product.getCustomers().remove(customerToDelete); // çift yönlü ilişki siliniyor
            }
            customerToDelete.getProducts().clear(); // tek yönlü ilişki temizleniyor
            customerDB.save(customerToDelete); // flush öncesi durum
        }
        customerDB.delete(customerToDelete);
    }

}
