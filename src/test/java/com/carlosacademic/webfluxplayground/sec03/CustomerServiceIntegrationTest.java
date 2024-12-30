package com.carlosacademic.webfluxplayground.sec03;

import com.carlosacademic.webfluxplayground.common.dto.CustomerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(properties = {
        "sec=sec03"
})
public class CustomerServiceIntegrationTest {

    @Autowired
    private WebTestClient client;

    @Test
    void testGetAllCustomers(){
        client.get()
                .uri("/customers")
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(CustomerDTO.class)
                .hasSize(10);
    }

    @Test
    void testGetAllCustomersPaginated(){
        client.get()
                .uri("/customers/paginated?page=0&size=2")
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0].id").isEqualTo(1)
                .jsonPath("$[1].id").isEqualTo(2);
    }

    @Test
    void createAndDeleteCostumer(){
        //create
        var dto = new CustomerDTO(null,"charles", "charles@gmail.com");

        client.post()
                .uri("/customers")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody()
                .jsonPath("$.id").isEqualTo(11)
                .jsonPath("$.email").isEqualTo("charles@gmail.com");

        //delete
        client.delete()
                .uri("/customers/11")
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody().isEmpty();
    }

    @Test
    void costumerNotFound(){
        client.get()
                .uri("/costumers/11")
                .exchange()
                .expectStatus().isEqualTo(404);
    }
}
