package com.example.springioc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springioc.components.AuthComponents;
import com.example.springioc.dto.CartItemDTO;
import com.example.springioc.dto.CartItemDetailedDTO;
import com.example.springioc.entity.Cart;
import com.example.springioc.entity.CartItem;
import com.example.springioc.entity.Stock;
import com.example.springioc.mapper.CartItemMapper;
import com.example.springioc.repository.CartItemRepo;
import com.example.springioc.repository.CustomerRepo;
import com.example.springioc.repository.ProductRepo;
import com.example.springioc.repository.StockRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepo cartItemDB;
    private final StockRepo stockDB;
    private final ProductRepo productDB;
    private final CartItemMapper cartItemMapper;
    private final AuthComponents authComponents;
    private final CustomerRepo customerDB;

    public CartItemDTO getCartItem(Long id) {
        return cartItemDB.findById(id)
                .map(cartItemMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found with ID: " + id));
    }

    public List<CartItemDetailedDTO> getAllCartItems(long user_id) {
        Cart cart = getCartByCustomer(user_id);
        return cart.getItems().stream()
                .map(cartItemMapper::toDetailedDTO)
                .toList();
    }

    public void DecreaseItemQuantity(Long id) {
        CartItem cartItem = cartItemDB.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found with ID: " + id));
        cartItem.setQuantity(cartItem.getQuantity() - 1);
        Stock stock = stockDB.findByProduct_Id(cartItem.getProduct().getId())
                .orElseThrow(() -> new EntityNotFoundException("Stock not found"));
        stock.setStockQuantity(stock.getStockQuantity() + 1);
        stockDB.save(stock);
        cartItemDB.save(cartItem);
    }

    public void IncreaseItemQuantity(Long id) {
        CartItem cartItem = cartItemDB.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found with ID: " + id));
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        Stock stock = stockDB.findByProduct_Id(cartItem.getProduct().getId())
                .orElseThrow(() -> new EntityNotFoundException("Stock not found"));
        if (stock.getStockQuantity() < 1) {
            throw new IllegalStateException("Insufficient stock");
        }
        stock.setStockQuantity(stock.getStockQuantity() - 1);
        stockDB.save(stock);
        cartItemDB.save(cartItem);
    }

    public void DeleteCartItem(Long id) {
        CartItem cartItem = cartItemDB.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found with ID: " + id));
        Stock stock = stockDB.findByProduct_Id(cartItem.getProduct().getId())
                .orElseThrow(() -> new EntityNotFoundException("Stock not found"));
        stock.setStockQuantity(stock.getStockQuantity() + cartItem.getQuantity());
        cartItemDB.deleteById(id);
    }

    private Cart getCartByCustomer(Long id) {
        boolean isAdmin = authComponents.isAdmin();
        Long userId = authComponents.getCurrentUserId();
        System.out.println(userId + " " + id);
        if (!userId.equals(id) && !isAdmin) {
            throw new EntityNotFoundException("You can only Edit your own Cart account");
        }
        return customerDB.findByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found for user ID: " + userId))
                .getCart();

    }
}
