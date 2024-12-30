package com.carlosacademic.webfluxplayground.sec07;

import com.carlosacademic.webfluxplayground.sec01.Product;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class Lec02FluxTest extends AbstractWebClientTest{

    private final WebClient client = createDefaultWebClient();

    @Test
    void streamingGet() {
        client.get()
                .uri("/lec02/product/stream")
                .retrieve()
                .bodyToFlux(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }
}
