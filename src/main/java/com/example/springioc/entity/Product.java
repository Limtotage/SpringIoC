package com.example.springioc.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "products")
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private Long id;

    @Column(name = "ProductName")
    private String name;

    @Column(name = "ProductPrice")
    private BigDecimal price;

    @Column(name = "ProductStock")
    private int stock;

    @Column(name = "ProductImage")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "Seller_ID")
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "Category_ID")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "customer_ID")
    private Customer customer;

}
