package com.carlosacademic.webfluxplayground.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.HttpProtocol;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.util.function.Consumer;

public class AbstractWebClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractWebClientTest.class);

    protected <T> Consumer<T> print(){
        return item -> LOGGER.info("Received: {}", item);
    }

    protected WebClient createDefaultWebClient(){
        return createWebClient(c -> {});
    }

    protected WebClient createWebClient(Consumer<WebClient.Builder> consumer){
        var builder = WebClient.builder()
                .baseUrl("http://localhost:7070");
        consumer.accept(builder);

        return builder.build();
    }

    //configuramos el web client para que envie mas peticiones concurrentes 1000 por default es 500
    protected WebClient createWebClientCustomizable(){
        var poolSize = 550;
        var provider = ConnectionProvider.builder("test")
                .lifo()
                .maxConnections(poolSize) //max de peticiones concurrentes
                .pendingAcquireMaxCount(poolSize * 2)//maximo de peticiones en cola si estan ocupados todos los hilos
                .build();

        //al no usar el por defecto debemos configurar cosas del Cliente.
        var httpClient = HttpClient.create(provider)
                .compress(true)
                .keepAlive(true);

        return WebClient.builder()
                .baseUrl("http://localhost:7070")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    /**
     * Cliente http2 en este caso es h2c (http2 clear) pues no usamos certificado digitales
     * en caso que tengamos certificados digitales es h2 (http2)
     * solo necesitamos el 1 en el pool size pues con http2 solo se necesita una conexión para
     * enviar multiples peticiones concurrentes
     *
     * Importante el servidor debe soportar http2 o la otra aplicación de spring debe tener
     * la propiedad habilitada para soportar http2.
     *
     */
    protected WebClient createWebClientHttp2(){
        var poolSize = 1;
        var provider = ConnectionProvider.builder("test")
                .lifo()
                .maxConnections(poolSize)
                .pendingAcquireMaxCount(poolSize * 2)
                .build();

        var httpClient = HttpClient.create(provider)
                .protocol(HttpProtocol.H2C) //ponemos el protocolo http2 el cual solo requiere una conexion para enviar muchas peticiones a la vez
                .compress(true)
                .keepAlive(true);

        return WebClient.builder()
                .baseUrl("http://localhost:7070")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
