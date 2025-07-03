package com.example.springioc.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryID")
    private Long id;

    @Column(name = "CategoryName")
    private String name;


    @Column(name="CategoryDescription")
    private String description;

    @Column(name="IsApproved")
    private Boolean isApproved;

    @OneToMany(mappedBy="category")
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","products"})
    private List<Product> products = new ArrayList<>();

}
