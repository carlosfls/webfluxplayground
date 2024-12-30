package com.carlosacademic.webfluxplayground.sec01;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/traditional")
public class TraditionalWebController {

    private final RestClient restClient = RestClient.builder()
            .baseUrl("http://localhost:7070")
            .build();

    @GetMapping("/products")
    public List<Product> getProducts(){
        List<Product> productList = this.restClient.get()
                .uri("/demo01/products")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        log.info("received {}", productList);
        return productList;
    }
}
