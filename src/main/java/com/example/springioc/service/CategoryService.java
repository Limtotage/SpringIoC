package com.example.springioc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springioc.entity.Category;
import com.example.springioc.repository.CategoryRepo;


@Service
public class CategoryService {
    @Autowired
    private CategoryRepo categoryDB;

    public List<Category> GetAllCategories(){
        return categoryDB.findAll();
    }

    public Optional<Category> GetCategoryByID(Long ID){
        return categoryDB.findById(ID);
    } 

    public Category CreateCategory(Category category){
        return categoryDB.save(category);
    }
}
