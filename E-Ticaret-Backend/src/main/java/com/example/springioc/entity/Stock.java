package com.example.springioc.entity;

import com.example.springioc.enums.StockStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int stockQuantity=0;

    @Enumerated(EnumType.STRING)
    private StockStatus stockStatus;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
