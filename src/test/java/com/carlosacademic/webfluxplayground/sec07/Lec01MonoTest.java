package com.carlosacademic.webfluxplayground.sec07;

import com.carlosacademic.webfluxplayground.sec01.Product;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

public class Lec01MonoTest extends AbstractWebClientTest{

    private final WebClient client = createDefaultWebClient();

    @Test
    void simpleGet() throws InterruptedException {
        client.get()
                .uri("/lec01/product/1")
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .subscribe();

        Thread.sleep(Duration.ofSeconds(2));
    }

    @Test
    void concurrentRequest() throws InterruptedException {
        for (int i = 1; i<=100; i++){
            client.get()
                    .uri("/lec01/product/{i}", i)
                    .retrieve()
                    .bodyToMono(Product.class)
                    .doOnNext(print())
                    .subscribe();
        }
        Thread.sleep(Duration.ofSeconds(2));
    }
}
