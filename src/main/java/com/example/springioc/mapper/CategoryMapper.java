package com.example.springioc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.springioc.dto.CategoryDTO;
import com.example.springioc.entity.Category;
import com.example.springioc.entity.Product; // Product entity's ID'lerini CategoryDTO'ya maplemek için kullanılır

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "productIds", expression = "java(category.getProducts() != null ? category.getProducts().stream().map(Product::getId).toList() : null)")
    CategoryDTO toDTO(Category category);

    @Mapping(target = "products", ignore = true) // sadece id'ler DTO'da, entity'ye çevirirken manuel set edilmeli
    Category toEntity(CategoryDTO dto);
}
