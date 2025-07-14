package com.example.springioc.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.example.springioc.dto.CartDTO;
import com.example.springioc.entity.Cart;
import com.example.springioc.entity.CartItem;


@Mapper(componentModel = "spring")
public interface CartMapper {
    
    @Mapping(target="cartItemIds", source="items",qualifiedByName="mapCartItemstoIds")
    CartDTO toDto(Cart cart);

    @Mapping(target="items", source="cartItemIds", qualifiedByName="mapIdsToCartItems")
    Cart toEntity(CartDTO cartDto);

    @Named("mapCartItemstoIds")
    default List<Long> mapCartItemstoIds(Set<CartItem> items){
        if (items == null) return null;
        return items.stream().map(CartItem::getId).toList();
    }

    @Named("mapIdsToCartItems")
    default Set<CartItem> mapIdsToCartItems(List<Long> ids){
        if (ids == null) return null;
        return ids.stream().map(id -> {
            CartItem item = new CartItem();
            item.setId(id);
            return item;
        }).collect(Collectors.toSet());
    }
}
