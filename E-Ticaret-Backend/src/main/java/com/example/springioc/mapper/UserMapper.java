package com.example.springioc.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.example.springioc.dto.UserDTO;
import com.example.springioc.entity.MyUser;
import com.example.springioc.entity.Role;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    @Mapping(target = "roleNames",source=".", qualifiedByName="RoleGetName" )
    UserDTO toDTO(MyUser user);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    MyUser toEntity(UserDTO dto);

    @Named("RoleGetName")
    default Set<String> getRoleNames(MyUser user) {
        return user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}