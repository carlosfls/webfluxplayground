package com.carlosacademic.webfluxplayground.sec02;

import com.carlosacademic.webfluxplayground.common.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import reactor.test.StepVerifier;

public class ProductRepositoryTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(ProductRepositoryTest.class);

    @Autowired
    private ProductRepository productRepository;

    @Test
    void findAllProductsBetweenPrice(){
        this.productRepository.findAllByPriceBetween(500, 1000)
                .doOnNext(p -> log.info("{}", p))
                .as(StepVerifier::create)
                .expectNextCount(3)
                .expectComplete()
                .verify();
    }

    @Test
    void findAll(){
        this.productRepository.findAllBy(PageRequest.of(0, 3)
                .withSort(Sort.by("price").ascending()))
                .doOnNext(p -> log.info("{}", p))
                .as(StepVerifier::create)
                .assertNext(p -> Assertions.assertEquals("apple tv", p.getDescription()))
                .assertNext(p -> Assertions.assertEquals("airpods pro", p.getDescription()))
                .assertNext(p -> Assertions.assertEquals("homepod", p.getDescription()))
                .expectComplete()
                .verify();
    }
}
