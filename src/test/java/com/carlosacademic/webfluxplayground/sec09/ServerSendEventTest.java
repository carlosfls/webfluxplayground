package com.carlosacademic.webfluxplayground.sec09;

import com.carlosacademic.webfluxplayground.common.dto.ProductDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

@AutoConfigureWebTestClient
@SpringBootTest(properties = "sec=sec09")
public class ServerSendEventTest {

    private static final Logger log = LoggerFactory.getLogger(ServerSendEventTest.class);

    @Autowired
    private WebTestClient webTestClient;

    //It uses the product generator service
    @Test
    void testServerSentEvents(){
        webTestClient.get()
                .uri("/products/stream/filter/80")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isEqualTo(200)
                .returnResult(ProductDTO.class)
                .getResponseBody()
                .take(3)
                .doOnNext(productDTO -> log.info("{}", productDTO))
                .collectList()
                .as(StepVerifier::create)
                .assertNext(list -> {
                    Assertions.assertEquals(3, list.size());
                    Assertions.assertTrue(list.stream().allMatch(p -> p.getPrice()<=80));
                })
                .expectComplete()
                .verify();
    }
}
