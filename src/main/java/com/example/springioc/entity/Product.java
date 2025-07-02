package com.example.springioc.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "products")
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

    @Column(name = "CreatedTime")
    private LocalDateTime createdAt;
    
    @Column(name = "UpdateTime")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "Category_ID")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
