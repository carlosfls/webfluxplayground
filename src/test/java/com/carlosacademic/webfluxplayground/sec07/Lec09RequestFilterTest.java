package com.carlosacademic.webfluxplayground.sec07;

import com.carlosacademic.webfluxplayground.common.AbstractWebClientTest;
import com.carlosacademic.webfluxplayground.sec01.Product;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.util.UUID;

public class Lec09RequestFilterTest extends AbstractWebClientTest {

    private final WebClient client = createWebClient(b -> b.filter(tokenGenerator())
                                                            .filter(requestLogger()));
    private static final Logger LOGGER = LoggerFactory.getLogger(Lec09RequestFilterTest.class);

    @Test
    void exchangeFilter() {
        client.get()
                .uri("/demo02/lec09/product/{id}", 1)
                .attribute("enable-logging", true) //pasando atributos a un filtro
                .retrieve()
                .bodyToMono(Product .class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    //creamos un filtro para que antes de enviar la peticion se genere un token.
    //interface funcional para la creacion de un filtro para el web client el cual interceptara las peticiones enviadas
    private ExchangeFilterFunction tokenGenerator(){
        return (request, next) -> {
            var token = UUID.randomUUID().toString().replace("-","");
            var modifiedRequest = ClientRequest.from(request)
                    .headers(httpHeaders -> httpHeaders.setBearerAuth(token)).build();
            return next.exchange(modifiedRequest);
        };
    }

    private ExchangeFilterFunction requestLogger(){
        return (request, next) -> {
            //obteniendo el atributo
            var isEnabled = (Boolean) request.attributes().getOrDefault("enable-logging", false);
            if (isEnabled){
                LOGGER.info("Method: {},Url: {}", request.method(), request.url());
            }
            return next.exchange(request);
        };
    }
}
