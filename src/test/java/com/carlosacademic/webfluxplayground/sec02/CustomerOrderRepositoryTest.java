package com.carlosacademic.webfluxplayground.sec02;

import com.carlosacademic.webfluxplayground.common.dto.OrderDetailsDTO;
import com.carlosacademic.webfluxplayground.sec02.repository.CustomerOrderRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.test.StepVerifier;

public class CustomerOrderRepositoryTest extends AbstractTest{

    private static final Logger log = LoggerFactory.getLogger(CustomerOrderRepositoryTest.class);

    @Autowired
    private CustomerOrderRepository repository;

    @Autowired
    private DatabaseClient databaseClient;

    @Test
    void getOrderDetailsByProduct(){
        this.repository.getOrderDetailsByProduct("iphone 20")
                .doOnNext(od -> log.info("{}", od))
                .as(StepVerifier::create)
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }

    //otra forma de ejecutar las query's en r2dbc
    @Test
    void getOrderDetailsByProductWhitDatabaseClient(){
        var sql = """
            SELECT
                co.order_id,
                c.name AS customer_name,
                p.description AS product_name,
                co.amount,
                co.order_date
            FROM
                customer c
            INNER JOIN customer_order co ON c.id = co.customer_id
            INNER JOIN product p ON p.id = co.product_id
            WHERE
                p.description = :description
            ORDER BY co.amount DESC
                """;

        this.databaseClient.sql(sql)
                .bind("description", "iphone 20")
                .mapProperties(OrderDetailsDTO.class)
                .all()
                .doOnNext(od -> log.info("{}", od))
                .as(StepVerifier::create)
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }
}
