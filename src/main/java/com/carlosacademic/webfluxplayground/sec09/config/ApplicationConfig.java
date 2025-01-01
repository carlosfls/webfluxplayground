package com.carlosacademic.webfluxplayground.sec09.config;

import com.carlosacademic.webfluxplayground.common.dto.ProductDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Sinks;

@Configuration
public class ApplicationConfig {

    @Bean
    public Sinks.Many<ProductDTO> productSink(){
        return Sinks.many()
                .replay()
                .limit(1);
    }
}
