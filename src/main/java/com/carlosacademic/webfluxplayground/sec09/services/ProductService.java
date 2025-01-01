package com.carlosacademic.webfluxplayground.sec09.services;

import com.carlosacademic.webfluxplayground.common.dto.ProductDTO;
import com.carlosacademic.webfluxplayground.common.mapper.ProductMapper;
import com.carlosacademic.webfluxplayground.common.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final Sinks.Many<ProductDTO> sink;

    public Mono<ProductDTO> saveProduct(Mono<ProductDTO> products){
        return products.map(ProductMapper::toEntity)
                .flatMap(productRepository::save)
                .map(ProductMapper::toDto)
                .doOnNext(sink::tryEmitNext);
    }

    public Flux<ProductDTO> productStream(){
        return sink.asFlux();
    }
}
