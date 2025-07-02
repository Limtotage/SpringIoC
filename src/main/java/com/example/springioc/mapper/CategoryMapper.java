package com.example.springioc.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.springioc.dto.CategoryDTO;
import com.example.springioc.dto.ProductDTO;
import com.example.springioc.entity.Category;
import com.example.springioc.entity.Product;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "products", source = "products")
    CategoryDTO toDTO(Category category);

    @Mapping(target = "products", ignore = true) // sadece id'ler DTO'da, entity'ye çevirirken manuel set edilmeli
    Category toEntity(CategoryDTO dto);

    List<ProductDTO> toProductDTOList(List<Product> products);

    List<Product> toProductEntityList(List<ProductDTO> dtos);
}
