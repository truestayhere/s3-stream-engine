package com.truestayhere.s3_stream_engine.adapters.out.storage;

import com.truestayhere.s3_stream_engine.config.MinioProperties;
import com.truestayhere.s3_stream_engine.domain.exception.StorageException;
import com.truestayhere.s3_stream_engine.domain.port.out.StoragePort;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
@Slf4j
public class MinioStorageAdapter implements StoragePort {
    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @PostConstruct
    private void ensureBucketExists() {
        Mono.fromRunnable(() -> {
                    try {
                        String bucketName = minioProperties.bucket();
                        log.info("Checking i bucket '{}' exists...", bucketName);
                        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
                        if (!found) {
                            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                            log.info("Bucket '{}' created.", bucketName);
                        } else {
                            log.info("Bucket '{}' already exists.", bucketName);
                        }
                    } catch (Exception e) {
                        log.error("Could not initialize MiniO bucket '{}'.", minioProperties.bucket(), e);
                        throw new RuntimeException("Failed to initialize MiniO bucket on startup", e);
                    }
                })
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();
    }

    @Override
    public Mono<Void> upload(String objectName, Flux<DataBuffer> content,
                             String contentType, long contentLength) {

        Mono<InputStream> inputStreamMono = DataBufferUtils.join(content)
                .map(DataBuffer::asInputStream);

        return inputStreamMono
                .flatMap(inputStream -> Mono.fromRunnable(() -> {
                    try {
                        minioClient.putObject(
                                PutObjectArgs.builder()
                                        .bucket(minioProperties.bucket())
                                        .object(objectName)
                                        .stream(inputStream, contentLength, -1)
                                        .contentType(contentType)
                                        .build());
                        log.info("Successfully uploaded object: {}", objectName);
                    } catch (Exception e) {
                        log.error("Error while uploading object {}", objectName, e);
                        throw new StorageException("Failed to upload object", e);
                    }
                }))
                .subscribeOn(Schedulers.boundedElastic())
                .then();
    }

}
