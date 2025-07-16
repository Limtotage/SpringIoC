package com.example.springioc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springioc.components.AuthComponents;
import com.example.springioc.dto.ProductDTO;
import com.example.springioc.entity.Category;
import com.example.springioc.entity.Product;
import com.example.springioc.entity.Seller;
import com.example.springioc.entity.Stock;
import com.example.springioc.mapper.ProductMapper;
import com.example.springioc.repository.CategoryRepo;
import com.example.springioc.repository.ProductRepo;
import com.example.springioc.repository.SellerRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productDB;
    private final CategoryRepo categoryDB;
    private final ProductMapper mapper;
    private final AuthComponents authComponents;
    private final SellerRepo sellerDB;

    public List<ProductDTO> getProductsBySeller(Long userId) {
        Seller seller = sellerDB.findByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found for user ID: " + userId));

        return productDB.findBySeller(seller).stream().map(mapper::toDTO).toList();
    }

    public List<ProductDTO> GetAllProducts() {
        return productDB.findAll().stream().map(mapper::toDTO).toList();
    }

    public List<ProductDTO> GetProductsByCategory(String categoryName) {
        Category category = categoryDB.findByName(categoryName)
                .orElseThrow(() -> new EntityNotFoundException("Category Not Found"));
        return productDB.findByCategory(category).stream().map(mapper::toDTO).toList();
    }

    public ProductDTO CreateProduct(ProductDTO productDTO) {
        if (productDTO.getCategoryName() == null || productDTO.getCategoryName().isBlank()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }
        Product product = mapper.toEntity(productDTO);

        Category category = categoryDB.findByName(productDTO.getCategoryName())
                .orElseThrow(() -> new EntityNotFoundException("Category Not Found"));

        Long userId = authComponents.getCurrentUserId();

        Seller seller = sellerDB.findByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found for user ID: " + userId));

        product.setSeller(seller);
        product.setCategory(category);

        Stock stock = new Stock();
        stock.setStockQuantity(productDTO.getProductStock() != null ? productDTO.getProductStock() : 0);
        stock.setProduct(product);
        product.setStock(stock);

        Product saved = productDB.save(product);

        return mapper.toDTO(saved);
    }

    public ProductDTO UpdateProduct(Long Id, ProductDTO dto) {
        Product existProduct = productDB.findById(Id).orElseThrow(() -> new RuntimeException("Product Not Found"));
        Long userId = authComponents.getCurrentUserId();
        if (!existProduct.getSeller().getUser().getId().equals(userId)) {
            throw new SecurityException("You are not allowed to update this product");
        }
        existProduct.setName(dto.getName());
        existProduct.setPrice(dto.getPrice());
        Category category = categoryDB.findByName(dto.getCategoryName())
                .orElseThrow(() -> new EntityNotFoundException("Category Not Found"));
        if (dto.getProductStock() != null && existProduct.getStock() != null) {
            existProduct.getStock().setStockQuantity(dto.getProductStock());
        }
        existProduct.setCategory(category);
        return mapper.toDTO(productDB.save(existProduct));
    }

    public void DeleteProduct(Long Id) {
        boolean isAdmin = authComponents.isAdmin();
        Long currentUserId = authComponents.getCurrentUserId();

        Product existProduct = productDB.findById(Id).orElseThrow(() -> new RuntimeException("Product Not Found"));

        if (!currentUserId.equals(existProduct.getSeller().getUser().getId()) && !isAdmin) {
            throw new EntityNotFoundException("You can only delete your own product");
        }
        existProduct.setSeller(null);
        productDB.save(existProduct);
        productDB.delete(existProduct);
    }
}
