package com.mangareader.user.rating;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/api/v1/manga/rating")
@RestController
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    public Mono<Rating> create(@RequestBody Rating rating) {
        return ratingService.create(rating);
    }

    @PutMapping
    public Mono<Rating> update(@RequestBody Rating rating) {
        return ratingService.update(rating);
    }

    @GetMapping("/{id}")
    public Mono<Rating> findById(@PathVariable String id) {
        return ratingService.findById(id);
    }

    @GetMapping("/manga/{manga}/user/{user}")
    public Mono<Rating> findByMangaAndUser(@PathVariable String manga, @PathVariable String user) {
        return ratingService.findByMangaAndUser(manga, user);
    }

    @GetMapping("/manga/{manga}")
    public Flux<Rating> findByManga(@PathVariable String manga, Pageable pageable) {
        return ratingService.findByManga(manga, pageable);
    }

    @GetMapping("/user/{user}")
    public Flux<Rating> findByUser(@PathVariable String user, Pageable pageable) {
        return ratingService.findByUser(user, pageable);
    }

    @GetMapping("/manga/{manga}/average")
    public Mono<RatingAverage> findAverageByManga(@PathVariable String manga) {
        return ratingService.findAverageRatingByManga(manga);
    }

    @GetMapping
    public Flux<Rating> findAll(Pageable pageable) {
        return ratingService.findAll(pageable);
    }

    @DeleteMapping("/{id}")
    public Mono<Rating> delete(@PathVariable String id) {
        return ratingService.delete(id);
    }


}
