package com.carlosacademic.webfluxplayground.sec04.controller;

import com.carlosacademic.webfluxplayground.common.dto.CustomerDTO;
import com.carlosacademic.webfluxplayground.common.exeptions.ApplicationException;
import com.carlosacademic.webfluxplayground.common.services.CustomerService;
import com.carlosacademic.webfluxplayground.common.validators.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<CustomerDTO> getAll(){
        return customerService.getAll();
    }

    @GetMapping(value = "/paginated", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<CustomerDTO> getAllPaginated(@RequestParam(defaultValue = "0") Integer page,
                                             @RequestParam(defaultValue = "3") Integer size){
        return customerService.getAllPaginated(page, size);
    }

    @GetMapping("/{id}")
    public Mono<CustomerDTO> getById(@PathVariable Integer id){
        return customerService.getById(id)
                .switchIfEmpty(ApplicationException.notFound("Customer" + id + "not found"));

    }

    @PostMapping
    public Mono<CustomerDTO> create(@RequestBody Mono<CustomerDTO> dto){
        return dto.transform(RequestValidator.validate())
                .as(customerService::create);

    }

    @PutMapping("/{id}")
    public Mono<CustomerDTO> update(@PathVariable Integer id, @RequestBody Mono<CustomerDTO> dto){
        return dto.transform(RequestValidator.validate())
                .as(dtoValidated -> customerService.update(id, dto))
                .switchIfEmpty(ApplicationException.notFound("Customer not found"));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable Integer id){
        return customerService.deleteById(id)
                .filter(b -> b)
                .switchIfEmpty(ApplicationException.notFound("Customer id not found"))
                .then();
    }
}
