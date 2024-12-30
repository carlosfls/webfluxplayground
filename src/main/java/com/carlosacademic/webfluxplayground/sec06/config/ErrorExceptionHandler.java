package com.carlosacademic.webfluxplayground.sec06.config;

import com.carlosacademic.webfluxplayground.common.exeptions.InvalidInputException;
import com.carlosacademic.webfluxplayground.common.exeptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Consumer;

@Slf4j
@Component
public class ErrorExceptionHandler {

    public Mono<ServerResponse> handleNotFoundException(NotFoundException e, ServerRequest serverRequest){
        log.error("Not found");
        return handleException(HttpStatus.NOT_FOUND, e, serverRequest,
                problemDetail -> problemDetail.setTitle("Resource Not found"));
    }

    public Mono<ServerResponse> handleInvalidInputException(InvalidInputException e, ServerRequest serverRequest){
        return handleException(HttpStatus.BAD_REQUEST, e, serverRequest,
                problemDetail -> problemDetail.setTitle("Invalid input fields"));
    }

    private Mono<ServerResponse> handleException(HttpStatus httpStatus, Exception ex, ServerRequest request, Consumer<ProblemDetail> consumer){
        var problem = ProblemDetail.forStatusAndDetail(httpStatus,ex.getMessage());
        problem.setInstance(URI.create(request.path()));
        consumer.accept(problem);
        return ServerResponse.status(httpStatus)
                .body(problem, ProblemDetail.class);
    }
}
