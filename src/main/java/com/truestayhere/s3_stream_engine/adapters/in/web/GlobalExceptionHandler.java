package com.truestayhere.s3_stream_engine.adapters.in.web;


import com.truestayhere.s3_stream_engine.adapters.in.web.dto.ErrorDto;
import com.truestayhere.s3_stream_engine.domain.exception.StorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(StorageException.class)
    public Mono<ResponseEntity<ErrorDto>> handleStorageException(
            StorageException ex, ServerWebExchange exchange) {
        log.error("Storage service unavailable: {}", ex.getMessage(), ex);

        ErrorDto errorDto = new ErrorDto(
                Instant.now(),
                HttpStatus.SERVICE_UNAVAILABLE.value(), // 503
                "Service Unavailable",
                "The storage service is temporarily unavailable.",
                exchange.getRequest().getPath().value()
        );

        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorDto));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorDto>> handleGenericException(Exception ex, ServerWebExchange exchange) {
        log.error("Ab unexpected error occurred: {}", ex.getMessage(), ex);

        ErrorDto errorDto = new ErrorDto(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500
                "Interval Server Error",
                "An unexpected error occurred.",
                exchange.getRequest().getPath().value()
        );

        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto));
    }

}
