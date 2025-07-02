package com.example.springioc.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.springioc.dto.CategoryDTO;
import com.example.springioc.dto.ProductDTO;
import com.example.springioc.dto.SellerDTO;
import com.example.springioc.entity.Category;
import com.example.springioc.entity.Product;
import com.example.springioc.entity.Seller;

@Mapper(componentModel = "spring")
public interface SellerMapper {

    @Mapping(source = "categories", target = "categories")
    @Mapping(source = "products", target = "products")
    @Mapping(target = "password", ignore = true)
    SellerDTO toDTO(Seller seller);

    @Mapping(target = "categories", source = "categories")
    @Mapping(target = "products", source = "products")
    @Mapping(target = "password", ignore = true)
    Seller toEntity(SellerDTO dto);

    List<CategoryDTO> toCategoryDTOList(List<Category> categories);

    List<Category> toCategoryEntityList(List<CategoryDTO> dtos);

    List<ProductDTO> toProductDTOList(List<Product> products);

    List<Product> toProductEntityList(List<ProductDTO> dtos);
}
