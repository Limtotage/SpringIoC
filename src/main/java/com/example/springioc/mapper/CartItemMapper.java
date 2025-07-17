package com.example.springioc.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.example.springioc.dto.CartItemDTO;
import com.example.springioc.dto.CartItemDetailedDTO;
import com.example.springioc.entity.CartItem;
import com.example.springioc.entity.Product;
import com.example.springioc.enums.StockStatus;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(target = "productId", source = "product.id")
    CartItemDTO toDto(CartItem cartItem);

    @Mapping(target = "product", source = "productId", qualifiedByName = "mapIdtoProducts")
    CartItem toEntity(CartItemDTO cartItemDto);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "productPrice", source = "product.price")
    @Mapping(target = "categoryName", source = "product.category.name")
    @Mapping(target = "stockStatus", ignore = true)
    @Mapping(target = "stockQuantity", ignore = true)
    CartItemDetailedDTO toDetailedDTO(CartItem cartItem);

    List<CartItemDetailedDTO> toDetailedDTOList(List<CartItem> cartItems);

    @Named("mapIdtoProducts")
    default Product mapIdtoProducts(Long id) {
        if (id == null)
            return null;
        Product product = new Product();
        product.setId(id);
        return product;
    }

    @AfterMapping
    default void setStockStatus(@MappingTarget CartItemDetailedDTO dto, CartItem cartItem) {
        if (cartItem.getProduct() != null &&
                cartItem.getProduct().getStock() != null) {
            dto.setStockStatus(determineStockStatus(cartItem.getProduct().getStock().getStockQuantity()));
        }
    }

    @AfterMapping
    default void setStockQuantity(@MappingTarget CartItemDetailedDTO dto, CartItem cartItem) {
        if (cartItem.getProduct() != null &&
                cartItem.getProduct().getStock() != null) {
            dto.setStockQuantity(cartItem.getProduct().getStock().getStockQuantity());
        }
    }

    default StockStatus determineStockStatus(int quantity) {
        if (quantity == 0) {
            return StockStatus.OUT_OF_STOCK;
        } else if (quantity <= 10) {
            return StockStatus.LOW_STOCK;
        } else {
            return StockStatus.IN_STOCK;
        }
    }
}
