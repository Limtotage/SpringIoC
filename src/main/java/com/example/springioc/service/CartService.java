package com.example.springioc.service;

import org.springframework.stereotype.Service;

import com.example.springioc.components.AuthComponents;
import com.example.springioc.dto.CartDTO;
import com.example.springioc.entity.Cart;
import com.example.springioc.entity.CartItem;
import com.example.springioc.entity.Product;
import com.example.springioc.entity.Stock;
import com.example.springioc.mapper.CartMapper;
import com.example.springioc.repository.CartItemRepo;
import com.example.springioc.repository.CartRepo;
import com.example.springioc.repository.CustomerRepo;
import com.example.springioc.repository.ProductRepo;
import com.example.springioc.repository.StockRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepo cartItemDB;
    private final CartRepo cartDB;
    private final ProductRepo productDB;
    private final CustomerRepo customerDB;
    private final StockRepo stockDB;
    private final CartMapper cartMapper;
    private final AuthComponents authComponents;

    public CartDTO getCart(Cart cart) {
        cart.calculateTotalPrice();
        return cartMapper.toDto(cart);
    }

    public String addItemToCart(Cart cart, Long productId, int quantity) {
        String msg = "";
        Product product = productDB.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId));
        Stock stock = stockDB.findByProduct_Id(productId)
                .orElseThrow(() -> new EntityNotFoundException("Stock not found for product ID: " + productId));
        if (stock.getStockQuantity() <= 0) {
            msg = "Stokta yeterli ürün yok.";
            return msg;
        }
        if (stock.getStockQuantity() < quantity) {
            msg = "Stokta yeterli ürün yok. " + stock.getStockQuantity() + " adet eklendi.";
        }
        int addedQuantity = Math.min(quantity, stock.getStockQuantity());

        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + addedQuantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(addedQuantity);
            newItem.setCart(cart);
            cart.getItems().add(newItem);
        }
        cart.calculateTotalPrice();

        stock.setStockQuantity(stock.getStockQuantity() - addedQuantity);
        stockDB.save(stock);

        cartDB.save(cart);
        return msg.isEmpty() ? "Item added to cart successfully" : msg;
    }

    public void RemoveItemFromCart(Cart cart, Long productId) {
        CartItem itemToRemove = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
        if (itemToRemove != null) {
            int quantity = itemToRemove.getQuantity();
            Stock stock = stockDB.findByProduct_Id(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Stock not found for product ID: " + productId));
            stock.setStockQuantity(stock.getStockQuantity() + quantity);
            stockDB.save(stock);
            cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
            cart.calculateTotalPrice();
            cartDB.save(cart);
        }
    }

    public void clearCart(Cart cart) {
        if (cart.getItems().isEmpty())
            return;
        for (CartItem item : cart.getItems()) {
            Stock stock = stockDB.findByProduct_Id(item.getProduct().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Stock not found"));
            stock.setStockQuantity(stock.getStockQuantity() + item.getQuantity());
            stockDB.save(stock);
        }
        cart.getItems().clear();
        cart.setTotalPrice(0.0);
        cartDB.save(cart);
    }

    public void ConfirmCart(Cart cart) {
        if (cart.getItems().isEmpty())
            return;
        cart.getItems().clear();
        cart.setTotalPrice(0.0);
        cartDB.save(cart);
    }
}
