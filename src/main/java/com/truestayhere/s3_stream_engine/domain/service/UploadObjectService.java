package com.truestayhere.s3_stream_engine.domain.service;


import com.truestayhere.s3_stream_engine.domain.port.in.UploadObjectUseCase;
import com.truestayhere.s3_stream_engine.domain.port.out.StoragePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadObjectService implements UploadObjectUseCase {

    private final StoragePort storagePort;

    @Override
    public Mono<String> uploadObject(UploadObjectCommand command) {
        String objectId = UUID.randomUUID().toString();

        // (Add later) check permission

        // (Add later) save metadata

        return storagePort.upload(
                objectId,
                command.content(),
                command.contentType(),
                command.contentLength()
        )
                .then(Mono.just(objectId));
    }
}
