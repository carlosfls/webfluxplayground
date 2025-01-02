package com.carlosacademic.webfluxplayground.sec07;

import com.carlosacademic.webfluxplayground.common.AbstractWebClientTest;
import com.carlosacademic.webfluxplayground.common.dto.CalculatorResponseDTO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.util.Map;

public class Lec06QueryParamTest extends AbstractWebClientTest {

    private final WebClient client = createDefaultWebClient();
    private static final Logger logger = LoggerFactory.getLogger(Lec06QueryParamTest.class);

    @Test
    void queryParamsRequestWithUriBuilder() {
        var path = "/lec06/calculator";
        var queryParams =  "first={first}&second={second}&operation={operation}";
        client.get()
                .uri(uriBuilder -> uriBuilder.path(path).query(queryParams).build(10,20,"+"))
                .retrieve()
                .bodyToMono(CalculatorResponseDTO.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    void queryParamsRequestWithUriBuilderAndMap() {
        var path = "/lec06/calculator";
        var queryParams =  "first={first}&second={second}&operation={operation}";
        Map<String, Object> map = Map.of(
                "first", 10,
                "second",20,
                "operation","*");

        client.get()
                .uri(uriBuilder -> uriBuilder.path(path).query(queryParams).build(map))
                .retrieve()
                .bodyToMono(CalculatorResponseDTO.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }
}
