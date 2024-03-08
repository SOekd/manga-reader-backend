package com.mangareader.manga.manga;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MangaRepository extends ReactiveMongoRepository<Manga, String> {

    Mono<Manga> findMangaByMainTitle(@NotNull String mainTitle);

}
