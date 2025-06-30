package com.example.springioc.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fullname")
    private String fullname;

    @Column(unique = true, nullable = false, name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","orders"})
    private List<CustomerOrder> orders;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","reviews"})
    private List<Review> reviews;
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","cart"})
    private Cart cart;

    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
