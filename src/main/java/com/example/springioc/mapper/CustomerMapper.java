package com.example.springioc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.springioc.dto.CustomerDTO;
import com.example.springioc.entity.Customer;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface CustomerMapper {
    CustomerDTO toDTO(Customer seller);

    Customer toEntity(CustomerDTO dto);
}