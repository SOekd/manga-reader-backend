package com.mangareader.manga.studio;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioRepository extends ReactiveMongoRepository<Studio, String> {

}
