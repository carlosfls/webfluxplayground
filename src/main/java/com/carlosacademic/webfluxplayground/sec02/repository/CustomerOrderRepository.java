package com.carlosacademic.webfluxplayground.sec02.repository;

import com.carlosacademic.webfluxplayground.common.dto.OrderDetailsDTO;
import com.carlosacademic.webfluxplayground.common.entity.CustomerOrder;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface CustomerOrderRepository extends ReactiveCrudRepository<CustomerOrder, UUID> {

    //projections with r2dbc
    @Query("""
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
    """)
    Flux<OrderDetailsDTO> getOrderDetailsByProduct(String description);
}
