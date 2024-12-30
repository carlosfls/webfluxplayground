package com.carlosacademic.webfluxplayground.sec07;

import com.carlosacademic.webfluxplayground.sec01.Product;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.util.Map;

public class Lec04HeaderTest extends AbstractWebClientTest{

    private final WebClient client = createWebClient(b ->
            b.defaultHeader("caller-id","product-service"));

    @Test
    void getProductByIdAndSendHeader() {
        client.get()
                .uri("/lec04/product/{id}", 1)
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    void getProductByIdAndOverrideHeader() {
        client.get()
                .uri("/lec04/product/{id}", 1)
                .header("caller-id", "new-header")
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    void getProductByIdAndSendHeaderMap() {
        var headersMap = Map.of(
                "caller-id", "new-value",
                "some-key", "some-value");

        client.get()
                .uri("/lec04/product/{id}", 1)
                .headers(h -> h.setAll(headersMap))
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }
}
