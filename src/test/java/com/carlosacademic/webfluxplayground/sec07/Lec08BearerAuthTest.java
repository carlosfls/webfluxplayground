package com.carlosacademic.webfluxplayground.sec07;

import com.carlosacademic.webfluxplayground.sec01.Product;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class Lec08BearerAuthTest extends AbstractWebClientTest{

    private final WebClient client = createWebClient(b ->
            b.defaultHeaders(h -> h.setBearerAuth("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")));

    @Test
    void bearerAuth() {
        client.get()
                .uri("/lec08/product/{id}", 1)
                .retrieve()
                .bodyToMono(Product .class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }
}
