package com.mangareader.manga.manga;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/manga")
public class MangaController {

    private final MangaService mangaService;

    @GetMapping
    public Flux<Manga> getAll() {
        return mangaService.getAll();
    }

    @GetMapping("/id/{id}")
    public Mono<Manga> getById(@PathVariable String id) {
        return mangaService.getById(id);
    }

    @GetMapping("/title/{mainTitle}")
    public Mono<Manga> getByMainTitle(@PathVariable String mainTitle) {
        return mangaService.getByMainTitle(mainTitle);
    }

    @DeleteMapping("/id/{id}")
    public Mono<Void> deleteById(@PathVariable String id) {
        return mangaService.deleteById(id);
    }

    @PostMapping
    public Mono<Manga> create(@RequestBody Manga manga) {
        return mangaService.create(manga);
    }

}
