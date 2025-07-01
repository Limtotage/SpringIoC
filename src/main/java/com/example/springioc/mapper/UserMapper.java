package com.example.springioc.mapper;

import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.springioc.dto.UserDTO;
import com.example.springioc.entity.MyUser;
import com.example.springioc.entity.Role;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roleNames", expression = "java(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))")
    UserDTO toDTO(MyUser user);

    default void _forceImports() {
        Role dummy = new Role();
        var list = java.util.List.of(dummy);
        list.stream().map(Role::getName).collect(Collectors.toSet());
    }

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    MyUser toEntity(UserDTO dto);
}
