package com.example.springioc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.springioc.dto.CustomerDTO;
import com.example.springioc.entity.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDTO toDTO(Customer customer);

    @Mapping(target = "password", ignore = true)
    Customer toEntity(CustomerDTO dto);
}