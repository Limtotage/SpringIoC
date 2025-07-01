package com.example.springioc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.springioc.dto.UserDTO;
import com.example.springioc.entity.MyUser;
import com.example.springioc.entity.Role; // Role entity's name'lerini UserDTO'ya maplemek için kullanılır

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roleNames", expression = "java(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))")
    UserDTO toDTO(MyUser user);
    
    @Mapping(target = "roles", ignore = true) 
    @Mapping(target = "password", ignore = true)
    MyUser toEntity(UserDTO dto);
}
