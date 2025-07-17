package com.example.springioc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springioc.components.AuthComponents;
import com.example.springioc.dto.CustomerDTO;
import com.example.springioc.entity.Cart;
import com.example.springioc.entity.CartItem;
import com.example.springioc.entity.Customer;
import com.example.springioc.entity.MyUser;
import com.example.springioc.entity.Stock;
import com.example.springioc.mapper.CustomerMapper;
import com.example.springioc.repository.CustomerRepo;
import com.example.springioc.repository.StockRepo;
import com.example.springioc.security.UserRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepo customerDB;
    private final UserRepo userDB;
    private final StockRepo stockDB;
    private final CustomerMapper mapper;
    private final AuthComponents authComponents;

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
            return mapper.toDTO(customerDB.save(targetCustomer));
        }
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
        Cart cart = customerDB.findByUser_Id(currentUserId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found for user ID: " + id))
                .getCart();
        for (CartItem item : cart.getItems()) {
            Stock stock = stockDB.findByProduct_Id(item.getProduct().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Stock not found"));
            stock.setStockQuantity(stock.getStockQuantity() + item.getQuantity());
            stockDB.save(stock);// Remove the association with the cart
        }

        customerDB.delete(customerToDelete);
    }

    @Transactional
    public void DeleteCustomerAdmin(Long id) {
        Customer customerToDelete = customerDB.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found for user ID: " + id));
        customerDB.delete(customerToDelete);
    }

}
