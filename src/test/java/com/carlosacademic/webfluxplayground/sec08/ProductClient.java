package com.carlosacademic.webfluxplayground.sec08;

import com.carlosacademic.webfluxplayground.common.dto.ProductDTO;
import com.carlosacademic.webfluxplayground.sec08.dto.ProductUploadedCountDto;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ProductClient {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080")
            .build();

    public Mono<ProductUploadedCountDto> uploadProducts(Flux<ProductDTO> products){
        return webClient.post()
                .uri("/products/upload")
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(products, ProductDTO.class)
                .retrieve()
                .bodyToMono(ProductUploadedCountDto.class);
    }

    public Flux<ProductDTO> uploadAndGetProducts(Flux<ProductDTO> products){
        return webClient.post()
                .uri("/products/upload-and-get")
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(products, ProductDTO.class)
                .accept(MediaType.APPLICATION_NDJSON)
                .retrieve()
                .bodyToFlux(ProductDTO.class);
    }

    public Flux<ProductDTO> downloadProducts(){
        return webClient.get()
                .uri("/products/download")
                .accept(MediaType.APPLICATION_NDJSON)
                .retrieve()
                .bodyToFlux(ProductDTO.class);
    }
}
