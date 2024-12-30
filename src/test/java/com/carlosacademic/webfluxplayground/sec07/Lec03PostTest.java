package com.carlosacademic.webfluxplayground.sec07;

import com.carlosacademic.webfluxplayground.sec01.Product;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec03PostTest extends AbstractWebClientTest{

    private final WebClient client = createDefaultWebClient();

    @Test
    void streamingGet() {
        var mono = Mono.just(new Product(null,"iphone",1000.0));
        client.post()
                .uri("/lec03/product")
                .body(mono, Product.class)
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }
}
