package com.mangareader.storage.discord;

import com.mangareader.common.storage.CloudFile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/storage/discord")
@RequiredArgsConstructor
public class DiscordStorageController {

    private final DiscordStorageService discordStorageService;

    @PostMapping
    public Mono<CloudFile> upload(@RequestPart("file") Mono<FilePart> filePart) {
        return discordStorageService.create(filePart);
    }

    @GetMapping("/{id}")
    public Mono<Void> download(@PathVariable String id, ServerHttpResponse response) {
        return discordStorageService.download(response, id);
    }

}
