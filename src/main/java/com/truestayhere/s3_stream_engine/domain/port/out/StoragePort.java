package com.truestayhere.s3_stream_engine.domain.port.out;

import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StoragePort {

    /**
     * Uploads an object to the storage.
     * @param objectName    The object's name.
     * @param content       A reactive stream of the object's content.
     * @param contentType   The type of the content.
     * @param contentLength The length of the content.
     * @return A Mono completes when upload is finished.
     */
    Mono<Void> upload(String objectName, Flux<DataBuffer> content,
                      String contentType, long contentLength);
}
