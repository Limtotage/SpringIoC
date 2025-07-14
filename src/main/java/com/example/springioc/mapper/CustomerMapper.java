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
    CustomerDTO toDTO(Customer customer);

    @Mapping(target = "user.fullname", source = "fullname")
    @Mapping(target = "user.username", source = "username")
    Customer toEntity(CustomerDTO dto);

}