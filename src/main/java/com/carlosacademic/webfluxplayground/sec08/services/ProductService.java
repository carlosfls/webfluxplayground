package com.carlosacademic.webfluxplayground.sec08.services;

import com.carlosacademic.webfluxplayground.common.dto.ProductDTO;
import com.carlosacademic.webfluxplayground.common.mapper.ProductMapper;
import com.carlosacademic.webfluxplayground.common.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Flux<ProductDTO> getAll(){
        return productRepository.findAll()
                .map(ProductMapper::toDto);
    }

    public Flux<ProductDTO> saveProducts(Flux<ProductDTO> products){
        return products.map(ProductMapper::toEntity)
                .as(productRepository::saveAll)
                .map(ProductMapper::toDto);
    }

    public Mono<Long> getProductsCount(){
        return productRepository.count();
    }
}
