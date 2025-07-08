package com.example.springioc.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.example.springioc.dto.SellerDTO;
import com.example.springioc.entity.Product;
import com.example.springioc.entity.Seller;

@Mapper(componentModel = "spring")
public interface SellerMapper {

    @Mapping(source = "products", target = "productsIds",qualifiedByName="mapProductsToIds")
    SellerDTO toDTO(Seller seller);

    @Mapping(source = "productsIds", target = "products",qualifiedByName="mapIdsToProducts")
    Seller toEntity(SellerDTO dto);

    @Named("mapProductsToIds")
    default List<Long> mapProductsToIds(List<Product> products) {
        if (products == null) return null;
        return products.stream().map(Product::getId).toList();
    }
    @Named("mapIdsToProducts")
    default List<Product> mapIdsToProducts(List<Long> ids){
        if(ids==null) return null;
        return ids.stream().map(id->
            {Product p = new Product();
            p.setId(id);
            return p;}
        ).toList();
    }
}
