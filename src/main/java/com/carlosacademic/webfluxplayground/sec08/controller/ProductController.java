package com.carlosacademic.webfluxplayground.sec08.controller;

import com.carlosacademic.webfluxplayground.common.dto.ProductDTO;
import com.carlosacademic.webfluxplayground.sec08.dto.ProductUploadedCountDto;
import com.carlosacademic.webfluxplayground.sec08.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "/upload", consumes = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<ProductUploadedCountDto> uploadProducts(@RequestBody Flux<ProductDTO> products){
        System.out.println("Post executed");
        return productService.saveProducts(products)
                .then(productService.getProductsCount())
                .map(count -> new ProductUploadedCountDto(UUID.randomUUID(), count));

    }

    //APPLICATION_NDJSON_VALUE ES USADO PARA COMMUNICATION SERVICE-SERVICE PARA FRONTEND DEBEMOS USAR TEXT_EVENT_STREAM
    @PostMapping(value = "/upload-and-get", consumes = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ProductDTO> uploadAndGetProducts(@RequestBody Flux<ProductDTO> products){
        return productService.saveProducts(products);

    }

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ProductDTO> downloadProducts(){
        return productService.getAll();
    }
}
