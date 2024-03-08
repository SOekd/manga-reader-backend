package com.mangareader.user.rating;

import com.mangareader.user.rating.exception.RatingAlreadyExistsException;
import com.mangareader.user.rating.exception.RatingNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<Rating> create(@NotNull Rating rating) {
        return ratingRepository
                .findByMangaIdAndUserId(rating.getMangaId(), rating.getUserId())
                .flatMap(rate -> Mono.<Rating>error(new RatingAlreadyExistsException("A rating already exists for this manga and user")))
                .switchIfEmpty(ratingRepository.save(rating));
    }

    public Mono<Rating> update(@NotNull Rating rating) {
        return ratingRepository.findById(rating.getId())
                .flatMap(rate -> ratingRepository.save(rating))
                .switchIfEmpty(Mono.error(new RatingNotFoundException("Rating not found")));
    }

    public Mono<Rating> findById(String id) {
        return ratingRepository.findById(id)
                .switchIfEmpty(Mono.error(new RatingNotFoundException("Rating not found with id " + id)));
    }

    public Mono<Rating> findByMangaAndUser(String manga, String user) {
        return ratingRepository.findByMangaIdAndUserId(manga, user)
                .switchIfEmpty(Mono.error(new RatingNotFoundException("Rating not found with manga " + manga + " and user " + user)));
    }

    public Flux<Rating> findByManga(String manga, Pageable pageable) {
        return ratingRepository.findByMangaId(manga, pageable);
    }

    public Flux<Rating> findByUser(String user, Pageable pageable) {
        return ratingRepository.findByUserId(user, pageable);
    }

    public Flux<Rating> findAll(Pageable pageable) {
        return ratingRepository.findAllBy(pageable);
    }

    public Mono<Rating> delete(String id) {
        return ratingRepository.findById(id)
                .switchIfEmpty(Mono.error(new RatingNotFoundException(id)))
                .flatMap(translator -> ratingRepository.delete(translator).thenReturn(translator));
    }

    public Mono<RatingAverage> findAverageRatingByManga(@NotNull String mangaId) {
        MatchOperation matchOperation = Aggregation.match(Criteria.where("mangaId").is(mangaId));
        Aggregation aggregation = Aggregation.newAggregation(
                matchOperation,
                Aggregation.group("mangaId").avg("rating").as("averageRating")
        );

        return reactiveMongoTemplate.aggregate(aggregation, "ratings", Document.class)
                .next()
                .map(result -> new RatingAverage(mangaId, result.getDouble("averageRating")));
    }


}
