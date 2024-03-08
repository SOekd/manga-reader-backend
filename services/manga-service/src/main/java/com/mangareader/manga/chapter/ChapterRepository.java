package com.mangareader.manga.chapter;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChapterRepository extends ReactiveMongoRepository<Chapter, String> {

}
