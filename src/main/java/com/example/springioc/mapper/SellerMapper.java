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

    @Mapping(source = "products", target = "products")
    SellerDTO toDTO(Seller seller);

    @Mapping(target = "products", source = "products")
    Seller toEntity(SellerDTO dto);

    List<ProductDTO> toProductDTOList(List<Product> products);

    List<Product> toProductEntityList(List<ProductDTO> dtos);
}
