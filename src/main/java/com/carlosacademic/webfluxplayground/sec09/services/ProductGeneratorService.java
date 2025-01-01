package com.carlosacademic.webfluxplayground.sec09.services;

import com.carlosacademic.webfluxplayground.common.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.random.RandomGenerator;

@Service
@RequiredArgsConstructor
public class ProductGeneratorService implements CommandLineRunner {

    private final ProductService productService;

    @Override
    public void run(String... args) {
        Flux.range(1, 1000)
                .delayElements(Duration.ofSeconds(1))
                .map(i -> new ProductDTO(null, "product-"+i, RandomGenerator.getDefault().nextInt(200)))
                .flatMap(productDTO -> productService.saveProduct(Mono.just(productDTO)))
                .subscribe();
    }
}
