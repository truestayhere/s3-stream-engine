package com.truestayhere.s3_stream_engine.config;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "minio")
public record MinioProperties(
        @NotEmpty
        String url,

        @NotEmpty
        String accessKey,

        @NotEmpty
        String secretKey,

        @NotEmpty
        String bucket
) { }
