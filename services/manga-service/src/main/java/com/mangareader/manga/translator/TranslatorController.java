package com.mangareader.manga.translator;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/api/v1/manga/translator")
@RestController
@RequiredArgsConstructor
public class TranslatorController {

    private final TranslatorService translatorService;

    @PostMapping
    public Mono<Translator> create(@RequestBody Translator translator) {
        return translatorService.create(translator);
    }

    @PutMapping
    public Mono<Translator> update(@RequestBody Translator translator) {
        return translatorService.update(translator);
    }

    @GetMapping("/{id}")
    public Mono<Translator> findById(@PathVariable String id) {
        return translatorService.findById(id);
    }

    @GetMapping("/scan/{scan}")
    public Mono<Translator> findTranslatorByName(@PathVariable String scan) {
        return translatorService.findTranslatorByName(scan);
    }

    @GetMapping
    public Flux<Translator> findAll(Pageable pageable) {
        return translatorService.findAll(pageable);
    }

    @DeleteMapping("/{id}")
    public Mono<Translator> delete(@PathVariable String id) {
        return translatorService.delete(id);
    }

}
