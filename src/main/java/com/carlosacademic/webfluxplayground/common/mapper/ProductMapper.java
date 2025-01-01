package com.carlosacademic.webfluxplayground.common.mapper;

import com.carlosacademic.webfluxplayground.common.dto.ProductDTO;
import com.carlosacademic.webfluxplayground.common.entity.Product;

public class ProductMapper {

    public static Product toEntity(ProductDTO dto){
        var entity = new Product();
        entity.setId(dto.getId());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());

        return entity;
    }

    public static ProductDTO toDto(Product product){
        return new ProductDTO(
                product.getId(),
                product.getDescription(),
                product.getPrice()
        );
    }
}
