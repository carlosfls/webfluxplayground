package com.carlosacademic.webfluxplayground.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductDTO {

    private Integer id;
    private String description;
    private Integer price;
}
