package com.mangareader.compressservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/compress")
@RequiredArgsConstructor
public class CompressController {

    private final CompressService compressService;

    @PostMapping
    public Mono<Void> compress(@RequestPart("files") Flux<FilePart> filePartFlux, ServerHttpResponse response) {
        return compressService.compress(filePartFlux, response);
    }

    @PostMapping("/image")
    public Mono<Void> compressImage(@RequestPart("file") Mono<FilePart> filePartMono, ServerHttpResponse response) {
        return compressService.compressImage(filePartMono, response);
    }

}
