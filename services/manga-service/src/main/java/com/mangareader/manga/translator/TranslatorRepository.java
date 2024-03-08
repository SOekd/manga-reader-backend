package com.mangareader.manga.translator;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TranslatorRepository extends ReactiveMongoRepository<Translator, String> {

    Mono<Translator> findByScan(String scan);

    Flux<Translator> findAllBy(Pageable pageable);

}
