package com.example.springioc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.springioc.dto.ProductDTO;
import com.example.springioc.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryId", source = "category.id")
    ProductDTO toDTO(Product product);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "seller", ignore = true)
    Product toEntity(ProductDTO dto);


}
