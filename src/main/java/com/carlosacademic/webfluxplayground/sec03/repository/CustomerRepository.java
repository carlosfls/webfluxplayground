package com.carlosacademic.webfluxplayground.sec03.repository;

import com.carlosacademic.webfluxplayground.common.entity.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {

    Flux<Customer> findAllBy(Pageable pageable);

    @Modifying
    @Query("delete from customer where id=:id")
    Mono<Boolean> deleteCustomerById(Integer id);
}
