package com.example.springioc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springioc.entity.Cart;
import com.example.springioc.repository.CartRepo;


@Service
public class CartService {
    
    @Autowired
    private CartRepo cartDB;

    public Cart GetCartByID(Long ID){
        return cartDB.findByCustomerId(ID);
    }
    public Cart SaveCart(Cart cart){
        return cartDB.save(cart);
    }
}
