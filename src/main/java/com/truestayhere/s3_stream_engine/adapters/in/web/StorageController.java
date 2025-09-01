package com.truestayhere.s3_stream_engine.adapters.in.web;


import com.truestayhere.s3_stream_engine.adapters.in.web.dto.UploadResponseDto;
import com.truestayhere.s3_stream_engine.domain.port.in.UploadObjectUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/objects")
@RequiredArgsConstructor
public class StorageController {
    private final UploadObjectUseCase uploadObjectUseCase;

    @PostMapping
    public Mono<ResponseEntity<UploadResponseDto>> uploadObject(@RequestPart("file") Mono<FilePart> filePartMono) {
        return filePartMono.flatMap(filePart -> {
            var command = new UploadObjectUseCase.UploadObjectCommand(
                    filePart.filename(),
                    filePart.content(),
                    filePart.headers().getContentType().toString(),
                    filePart.headers().getContentLength()
            );

            return uploadObjectUseCase.uploadObject(command);
        })
                .map(generatedId -> ResponseEntity
                        .status(HttpStatus.CREATED) // 201
                        .body(new UploadResponseDto(generatedId)));

    }
}
