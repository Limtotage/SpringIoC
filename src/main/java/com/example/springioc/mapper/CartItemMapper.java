package com.example.springioc.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.example.springioc.dto.CartItemDTO;
import com.example.springioc.dto.CartItemDetailedDTO;
import com.example.springioc.entity.CartItem;
import com.example.springioc.entity.Product;

@Mapper(componentModel= "spring")
public interface CartItemMapper {

    @Mapping(target = "productId", source = "product.id")
    CartItemDTO toDto(CartItem cartItem);

    @Mapping(target = "product", source = "productId" , qualifiedByName = "mapIdtoProducts")
    CartItem toEntity(CartItemDTO cartItemDto);


    


    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "productPrice", source = "product.price")
    CartItemDetailedDTO toDetailedDTO(CartItem cartItem);

    List<CartItemDetailedDTO> toDetailedDTOList(List<CartItem> cartItems);



    @Named("mapIdtoProducts")
    default Product mapIdtoProducts(Long id) {
        if (id == null) return null;
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
