package com.example.springioc.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.example.springioc.dto.CustomerDTO;
import com.example.springioc.entity.Customer;
import com.example.springioc.entity.Product;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(target = "fullname", source = "user.fullname")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "productsIds", source = "products", qualifiedByName = "mapProductsToIds")
    CustomerDTO toDTO(Customer customer);

    @Mapping(target = "user.fullname", source = "fullname")
    @Mapping(target = "user.username", source = "username")
    @Mapping(target = "products", source = "productsIds", qualifiedByName = "mapIdsToProducts")
    Customer toEntity(CustomerDTO dto);

    @Named("mapProductsToIds")
    default List<Long> mapProductsToIds(List<Product> products) {
        if (products == null)
            return null;
        return products.stream().map(Product::getId).toList();
    }

    @Named("mapIdsToProducts")
    default List<Product> mapIdsToProducts(List<Long> ids) {
        if (ids == null)
            return null;
        return ids.stream().map(id -> {
            Product p = new Product();
            p.setId(id);
            return p;
        }).toList();
    }
}