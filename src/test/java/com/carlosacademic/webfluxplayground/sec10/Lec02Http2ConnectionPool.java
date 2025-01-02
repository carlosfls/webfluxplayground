package com.carlosacademic.webfluxplayground.sec10;

import com.carlosacademic.webfluxplayground.common.AbstractWebClientTest;
import com.carlosacademic.webfluxplayground.common.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec02Http2ConnectionPool extends AbstractWebClientTest{

    private final WebClient http2WebClient = createWebClientHttp2();

    @Test
    void concurrentRequest(){
        var max = 1000;
        Flux.range(1, max)
                .flatMap(this::getProductId, max)
                .collectList()
                .as(StepVerifier::create)
                .assertNext(l -> Assertions.assertEquals(max, l.size()))
                .expectComplete()
                .verify();
    }

    private Mono<Product> getProductId(int id){
        return http2WebClient.get()
                .uri("/demo03/product/{id}", id)
                .retrieve()
                .bodyToMono(Product.class);

    }
}
