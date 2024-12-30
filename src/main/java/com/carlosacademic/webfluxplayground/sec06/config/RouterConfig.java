package com.carlosacademic.webfluxplayground.sec06.config;

import com.carlosacademic.webfluxplayground.common.exeptions.InvalidInputException;
import com.carlosacademic.webfluxplayground.common.exeptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@RequiredArgsConstructor
@Configuration
public class RouterConfig {

    private final CustomerRequestHandler handler;
    private final ErrorExceptionHandler exceptionHandler;

    @Bean
    public RouterFunction<ServerResponse> router(){
        return RouterFunctions.route()
                .GET("/customers/paginated", handler::allCustomersPaginatedHandler)
                .GET("/customers/{id}", handler::getCustomerById)
                .GET("/customers", handler::allCustomersHandler)
                .POST("/customers", handler::createCustomer)
                .PUT("/customers/{id}", handler::updateCustomer)
                .DELETE("/customers/{id}", handler::deleteCustomerById)
                .onError(NotFoundException.class, exceptionHandler::handleNotFoundException)
                .onError(InvalidInputException.class, exceptionHandler::handleInvalidInputException)
                .build();
    }
}
