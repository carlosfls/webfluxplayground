package com.carlosacademic.webfluxplayground.sec08;

import com.carlosacademic.webfluxplayground.common.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class UploadDownloadProductTest {

    private static final Logger log = LoggerFactory.getLogger(UploadDownloadProductTest.class);

    private final ProductClient productClient = new ProductClient();

    @Test
    void testStreamingUpload(){
        var flux = Flux.range(1, 10000)
                .map(i -> new ProductDTO(null, "product-"+i, i))
                .doOnNext(p -> log.info("received: {}", p));

        productClient.uploadProducts(flux)
                .doOnNext(count -> log.info("received: "+count))
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    void testStreamingUploadAndGet(){
        var flux = Flux.range(1, 10000)
                .map(i -> new ProductDTO(null, "product-"+i, i));

        productClient.uploadAndGetProducts(flux)
                .doOnNext(p -> log.info("uploaded: "+p))
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    void testStreamingDownload(){
      productClient.downloadProducts()
              .doOnNext(p -> log.info("received: "+ p))
              .then()
              .as(StepVerifier::create)
              .expectComplete()
              .verify();
    }

}
