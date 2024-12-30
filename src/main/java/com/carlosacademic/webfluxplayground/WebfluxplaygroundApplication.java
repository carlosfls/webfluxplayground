package com.carlosacademic.webfluxplayground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.carlosacademic.webfluxplayground.${sec}",
        "com.carlosacademic.webfluxplayground.common"
})
@EnableR2dbcRepositories(basePackages = {
        "com.carlosacademic.webfluxplayground.${sec}",
        "com.carlosacademic.webfluxplayground.common"
})
public class WebfluxplaygroundApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxplaygroundApplication.class, args);
    }

}
