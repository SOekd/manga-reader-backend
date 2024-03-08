package com.mangareader.manga.manga;

import com.mangareader.manga.gender.Gender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MangaService {

    private final MangaRepository mangaRepository;

    public Flux<Manga> getAll() {
        return mangaRepository.findAll();
    }

    public Mono<Manga> getById(String id) {
        return mangaRepository.findById(id);
    }

    public Mono<Manga> getByMainTitle(String mainTitle) {
        return mangaRepository.findMangaByMainTitle(mainTitle);
    }
    public Flux<Manga> getByGenders(List<Gender> mangaGenders) {
        return null;
    }

    public Flux<Manga> getWithRatingAbove(int rating) {
        return null;
    }

    public Flux<Manga> getWithRatingBelow(int rating) {
        return null;
    }

    public Flux<Manga> getMostSeenWeekly() {
        return null;
    }

    public Flux<Manga> getMostRecentlyUpdated() {
        return null;
    }

    public Flux<Manga> getMostRecentlyPublished() {
        return null;
    }

    public Mono<Manga> create(Manga manga) {
        return mangaRepository.save(manga);
    }

    public Mono<Manga> update(Manga manga) {
        return mangaRepository.save(manga);
    }

    public Mono<Void> deleteById(String id) {
        return mangaRepository.deleteById(id);
    }

}
