package com.example.springioc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.springioc.dto.ProductDTO;
import com.example.springioc.entity.Product;
import com.example.springioc.enums.StockStatus;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "productStock", source = "stock.stockQuantity")
    ProductDTO toDTO(Product product);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "stock", ignore = true)
    Product toEntity(ProductDTO dto);


}
