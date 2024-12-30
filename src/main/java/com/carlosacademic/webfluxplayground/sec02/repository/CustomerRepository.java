package com.carlosacademic.webfluxplayground.sec02.repository;

import com.carlosacademic.webfluxplayground.common.entity.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {

    Flux<Customer> findByName(String name);

    Flux<Customer> findAllByEmailEndingWith(String email);
}
