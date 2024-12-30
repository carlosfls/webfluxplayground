package com.carlosacademic.webfluxplayground.sec07;

import com.carlosacademic.webfluxplayground.common.dto.CalculatorResponseDTO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec05ErrorHandlingTest extends AbstractWebClientTest{

    private final WebClient client = createDefaultWebClient();
    private static final Logger logger = LoggerFactory.getLogger(Lec05ErrorHandlingTest.class);

    @Test
    void calculateProductSuccess() {
        client.get()
                .uri("/lec05/calculator/{a}/{b}", 10, 20)
                .header("operation", "+")
                .retrieve()
                .bodyToMono(CalculatorResponseDTO.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    void calculateProductHandlingError() {
        client.get()
                .uri("/lec05/calculator/{a}/{b}", 10, 20)
                .header("operation", "@")
                .retrieve()
                .bodyToMono(CalculatorResponseDTO.class)
                .doOnError(WebClientResponseException.BadRequest.class, ex -> logger.info("{}", ex.getResponseBodyAs(ProblemDetail.class)))
                .onErrorComplete()
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    void exchange() {
        client.get()
                .uri("/lec05/calculator/{a}/{b}", 10, 20)
                .header("operation", "@")//for success use + or -
                .exchangeToMono(this::decode)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    private Mono<CalculatorResponseDTO> decode(ClientResponse clientResponse){
        HttpStatusCode httpStatusCode = clientResponse.statusCode();
        logger.info("Status code {}", httpStatusCode);
        if (httpStatusCode.is4xxClientError()){
            return clientResponse.bodyToMono(ProblemDetail.class)
                    .doOnNext(pd -> logger.info("{}", pd))
                    .then(Mono.empty());
        }
        return clientResponse.bodyToMono(CalculatorResponseDTO.class);
    }
}
