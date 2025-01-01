package com.carlosacademic.webfluxplayground.common.services;

import com.carlosacademic.webfluxplayground.common.dto.CustomerDTO;
import com.carlosacademic.webfluxplayground.common.mapper.CustomerMapper;
import com.carlosacademic.webfluxplayground.common.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository repository;

    public Flux<CustomerDTO> getAll(){
        return repository.findAll()
                .map(CustomerMapper::toDto);
    }

    public Flux<CustomerDTO> getAllPaginated(Integer page, Integer size){
        return repository.findAllBy(PageRequest.of(page, size))
                .map(CustomerMapper::toDto);
    }

    public Mono<CustomerDTO> getById(Integer id){
        return repository.findById(id)
                .map(CustomerMapper::toDto);
    }

    public Mono<CustomerDTO> create(Mono<CustomerDTO> mono){
        return mono.map(CustomerMapper::toEntity)
                .flatMap(repository::save)
                .map(CustomerMapper::toDto);
    }

    public Mono<CustomerDTO> update(Integer id, Mono<CustomerDTO> mono){
        return repository.findById(id)
                .flatMap(c -> mono)
                .map(CustomerMapper::toEntity)
                .doOnNext(customer -> customer.setId(id))
                .flatMap(repository::save)
                .map(CustomerMapper::toDto);
    }

    public Mono<Boolean> deleteById(Integer id){
        return repository.deleteCustomerById(id);
    }
}
