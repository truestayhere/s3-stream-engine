package com.truestayhere.s3_stream_engine.adapters.in.web.dto;

import java.time.Instant;

public record ErrorDto(
        Instant timeStamp,
        int status,
        String error,
        String message,
        String path
) {
}
