package com.example.springioc.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.example.springioc.dto.CategoryDTO;
import com.example.springioc.entity.Category;
import com.example.springioc.entity.Product;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "productsIds", source = "products", qualifiedByName = "mapProductsToIds")
    CategoryDTO toDTO(Category category);

    @Mapping(target = "products", source = "productsIds", qualifiedByName = "mapIdsToProducts")
    Category toEntity(CategoryDTO dto);

    @Named("mapProductsToIds")
    default List<Long> mapProductsToIds(List<Product> products) {
        if (products == null)
            return null;
        return products.stream().map(Product::getId).collect(Collectors.toList());
    }

    @Named("mapIdsToProducts")
    default List<Product> mapIdsToProducts(List<Long> ids) {
        if (ids == null)
            return null;
        return ids.stream().map(id -> {
            Product p = new Product();
            p.setId(id);
            return p;
        }).collect(Collectors.toList());
    }
}
