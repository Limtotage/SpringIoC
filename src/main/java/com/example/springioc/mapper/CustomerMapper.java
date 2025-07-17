package com.example.springioc.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.example.springioc.dto.CustomerDTO;
import com.example.springioc.entity.CartItem;
import com.example.springioc.entity.Customer;
import com.example.springioc.entity.Product;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(target = "fullname", source = "user.fullname")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "productsIds", ignore = true)
    CustomerDTO toDTO(Customer customer);

    @Mapping(target = "user.fullname", source = "fullname")
    @Mapping(target = "user.username", source = "username")
    Customer toEntity(CustomerDTO dto);

    @AfterMapping
    default void setStockStatus(@MappingTarget CustomerDTO dto, Customer customer) {
        if (customer.getCart() != null) {
            List<Long> productIds = customer.getCart().getItems().stream()
                    .map(cartItem -> cartItem.getProduct().getId())
                    .collect(Collectors.toList());
            dto.setProductsIds(productIds);
        }
    }
}