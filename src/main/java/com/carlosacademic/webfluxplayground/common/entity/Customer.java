package com.carlosacademic.webfluxplayground.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@ToString
@NoArgsConstructor
@Setter
@Getter
@Table(name = "customer")
public class Customer {

    @Id
    private Integer id;
    private String name;
    private String email;
}
