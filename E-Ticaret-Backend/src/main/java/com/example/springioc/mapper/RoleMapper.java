package com.example.springioc.mapper;

import org.mapstruct.Mapper;

import com.example.springioc.dto.RoleDTO;
import com.example.springioc.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDTO toDTO(Role role);
    Role toEntity(RoleDTO dto);
}
