package com.truestayhere.s3_stream_engine.domain.port.in;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.core.io.buffer.DataBuffer;

public interface UploadObjectUseCase {

    Mono<String> uploadObject(UploadObjectCommand command);

    record UploadObjectCommand(
            String fileName,
            Flux<DataBuffer> content,
            String contentType,
            long contentLength
    ) {}
}
