package com.example.springioc.mapper;

import org.aspectj.lang.annotation.After;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.springioc.dto.CartItemDetailedDTO;
import com.example.springioc.dto.ProductDTO;
import com.example.springioc.entity.CartItem;
import com.example.springioc.entity.Product;
import com.example.springioc.enums.StockStatus;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "productStock", source = "stock.stockQuantity")
    @Mapping(target="stockStatus", ignore = true)
    ProductDTO toDTO(Product product);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "stock", ignore = true)
    Product toEntity(ProductDTO dto);

    @AfterMapping
    default void setStockStatus(@MappingTarget ProductDTO dto, Product product) {
        if (product.getStock() != null) {
            dto.setStockStatus(determineStockStatus(product.getStock().getStockQuantity()));
        }
    }

    default StockStatus determineStockStatus(int quantity) {
        if (quantity == 0) {
            return StockStatus.OUT_OF_STOCK;
        } else if (quantity <= 10) {
            return StockStatus.LOW_STOCK;
        } else {
            return StockStatus.IN_STOCK;
        }
    }

}
