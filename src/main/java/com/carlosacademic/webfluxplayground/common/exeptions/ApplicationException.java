package com.carlosacademic.webfluxplayground.common.exeptions;

import reactor.core.publisher.Mono;

public class ApplicationException {

    public static <T> Mono<T> notFound(String message){
        return Mono.error(new NotFoundException(message));
    }

    public static <T> Mono<T> invalidInput(String message){
        return Mono.error(new InvalidInputException(message));
    }
}
