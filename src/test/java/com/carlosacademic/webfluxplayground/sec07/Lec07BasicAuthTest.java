package com.carlosacademic.webfluxplayground.sec07;

import com.carlosacademic.webfluxplayground.common.AbstractWebClientTest;
import com.carlosacademic.webfluxplayground.sec01.Product;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class Lec07BasicAuthTest extends AbstractWebClientTest {

    private final WebClient client = createWebClient(b ->
            b.defaultHeaders(h -> h.setBasicAuth("java","secret")));

    @Test
    void basicAuth() {
        client.get()
                .uri("/demo02/lec07/product/{id}", 1)
                .retrieve()
                .bodyToMono(Product .class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }
}
