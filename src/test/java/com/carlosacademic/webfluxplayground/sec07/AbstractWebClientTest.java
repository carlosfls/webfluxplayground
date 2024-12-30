package com.carlosacademic.webfluxplayground.sec07;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;

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
                .baseUrl("http://localhost:7070/demo02");
        consumer.accept(builder);

        return builder.build();
    }
}
