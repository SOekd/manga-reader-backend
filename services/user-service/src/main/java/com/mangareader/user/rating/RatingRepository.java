package com.mangareader.user.rating;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface RatingRepository extends ReactiveMongoRepository<Rating, String> {

    Mono<Rating> findByMangaIdAndUserId(String mangaId, String userId);

    Flux<Rating> findByMangaId(String mangaId, Pageable pageable);

    Flux<Rating> findByUserId(String userId, Pageable pageable);

    Flux<Rating> findAllBy(Pageable pageable);

}
