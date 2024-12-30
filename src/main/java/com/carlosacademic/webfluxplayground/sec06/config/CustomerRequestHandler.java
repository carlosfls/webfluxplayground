package com.carlosacademic.webfluxplayground.sec06.config;

import com.carlosacademic.webfluxplayground.common.dto.CustomerDTO;
import com.carlosacademic.webfluxplayground.common.exeptions.ApplicationException;
import com.carlosacademic.webfluxplayground.common.services.CustomerService;
import com.carlosacademic.webfluxplayground.common.validators.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomerRequestHandler {

    private final CustomerService customerService;

    public Mono<ServerResponse> allCustomersHandler(ServerRequest serverRequest){
        return customerService.getAll()
                .as(flux -> ServerResponse.ok()
                   .body(flux, CustomerDTO.class));
    }

    public Mono<ServerResponse> allCustomersPaginatedHandler(ServerRequest serverRequest){
        var page = serverRequest.queryParam("page").map(Integer::parseInt).orElse(0);
        var size = serverRequest.queryParam("size").map(Integer::parseInt).orElse(3);

        return customerService.getAllPaginated(page, size)
                .collectList()
                .flatMap(list -> ServerResponse.ok().bodyValue(list));
    }

    public Mono<ServerResponse> getCustomerById(ServerRequest serverRequest){
        String id = serverRequest.pathVariable("id");

        return customerService.getById(Integer.parseInt(id))
                .as(mono -> ServerResponse.ok().body(mono, CustomerDTO.class))
                .switchIfEmpty(ApplicationException.notFound("Customer with id "+id+" not found"));
    }

    public Mono<ServerResponse> createCustomer(ServerRequest serverRequest){
        return serverRequest.bodyToMono(CustomerDTO.class)
                .transform(RequestValidator.validate())
                .as(customerService::create)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> updateCustomer(ServerRequest serverRequest){
        String id = serverRequest.pathVariable("id");
        return serverRequest.bodyToMono(CustomerDTO.class)
                .transform(RequestValidator.validate())
                .as(validated -> customerService.update(Integer.parseInt(id), validated))
                .flatMap(ServerResponse.ok()::bodyValue)
                .switchIfEmpty(ApplicationException.notFound("Customer with id "+id+" not found"));
    }

    public Mono<ServerResponse> deleteCustomerById(ServerRequest serverRequest){
        String id = serverRequest.pathVariable("id");

        return customerService.deleteById(Integer.parseInt(id))
                .filter(mono -> mono)
                .then(ServerResponse.ok().build())
                .switchIfEmpty(ApplicationException.notFound("Customer with id "+id+" not found"));
    }
}
