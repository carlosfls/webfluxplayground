package com.carlosacademic.webfluxplayground.sec10;

import com.carlosacademic.webfluxplayground.common.AbstractWebClientTest;
import com.carlosacademic.webfluxplayground.common.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Importante esto es solo para servidores muy lentos en procesar el servidor que usamos demora 5 segundos
 * en procesar para este demo, normalmente no es necesario configurar nada. 500 es suficiente.
 */
public class Lec01HttpConnectionPool extends AbstractWebClientTest{

    //el web client por defecto envia 500 peticiones a la vez.
    //si queremos configurar esto debemos configurar el web client para que acepte mas.
    private final WebClient webClient = createDefaultWebClient();

    //web client customizado para enviar mas peticiones a la vez no 500 que es por defecto
    private final WebClient customizableWebClient = createWebClientCustomizable();

    @Test
    void concurrentRequest(){
        var max = 501;
        Flux.range(1, max)
                .flatMap(this::getProductId, max) //a flatmap le pasamos el parámetro max pues por defecto procesa 250 registros a la vez y modificamos eso
                .collectList()
                .as(StepVerifier::create)
                .assertNext(l -> Assertions.assertEquals(max, l.size()))
                .expectComplete()
                .verify();
    }

    /**
     * Podemos alternar entre el customizableWebClient y el webClient veras que el
     * web client demora el doble de tiempo para procesar los datos ya que envia 500
     * peticiones a la vez al servicio externo y como tenemos 501 debe hacer 2 peticiones.
     *
     * Sin embargo customizableWebClient lo configuramos para aceptar mas conexiones
     * y esta capacitado para aceptar mas peticiones 501 a la vez.
     *
     */
    private Mono<Product> getProductId(int id){
        return customizableWebClient.get()
                .uri("/demo03/product/{id}", id)
                .retrieve()
                .bodyToMono(Product.class);

    }
}
