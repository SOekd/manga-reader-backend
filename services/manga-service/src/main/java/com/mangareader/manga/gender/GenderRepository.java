package com.mangareader.manga.gender;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenderRepository extends ReactiveMongoRepository<Gender, String> {

}
