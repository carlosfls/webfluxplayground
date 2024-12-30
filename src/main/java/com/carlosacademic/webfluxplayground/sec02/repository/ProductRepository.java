package com.carlosacademic.webfluxplayground.sec02.repository;

import com.carlosacademic.webfluxplayground.common.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {

    Flux<Product> findAllBy(Pageable pageable);

    Flux<Product> findAllByPriceBetween(int from, int to);
}
