package com.carlosacademic.webfluxplayground.sec09.controller;

import com.carlosacademic.webfluxplayground.common.dto.ProductDTO;
import com.carlosacademic.webfluxplayground.sec09.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public Mono<ProductDTO> save(@RequestBody Mono<ProductDTO> mono){
        return productService.saveProduct(mono);
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDTO> productsStream(){
        return productService.productStream();
    }

    @GetMapping(value = "/stream/filter/{maxPrice}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDTO> productsStreamFiltered(@PathVariable Integer maxPrice){
        return productService.productStream()
                .filter(dto -> dto.getPrice() <= maxPrice);
    }
}
