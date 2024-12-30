package com.carlosacademic.webfluxplayground.common.validators;

import com.carlosacademic.webfluxplayground.common.dto.CustomerDTO;
import com.carlosacademic.webfluxplayground.common.exeptions.ApplicationException;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class RequestValidator {

    public static UnaryOperator<Mono<CustomerDTO>> validate(){
        return mono -> mono.filter(hasName())
                .switchIfEmpty(ApplicationException.invalidInput("Name is required"))
                .filter(hasValidEmail())
                .switchIfEmpty(ApplicationException.invalidInput("Email is invalid"));
    }

    public static Predicate<CustomerDTO> hasName(){
        return dto -> !ObjectUtils.isEmpty(dto.getName());
    }

    public static Predicate<CustomerDTO> hasValidEmail(){
        return dto -> !ObjectUtils.isEmpty(dto.getEmail()) && dto.getEmail().contains("@");
    }
}
