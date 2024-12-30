package com.carlosacademic.webfluxplayground.common.mapper;

import com.carlosacademic.webfluxplayground.common.dto.CustomerDTO;
import com.carlosacademic.webfluxplayground.common.entity.Customer;

public class CustomerMapper {

    public static Customer toEntity(CustomerDTO dto){
        var entity = new Customer();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());

        return entity;
    }

    public static CustomerDTO toDto(Customer customer){
        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail()
        );
    }
}
