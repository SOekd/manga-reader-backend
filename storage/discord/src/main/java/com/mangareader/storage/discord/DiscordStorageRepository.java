package com.mangareader.storage.discord;

import com.mangareader.common.storage.CloudFile;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscordStorageRepository extends ReactiveMongoRepository<CloudFile, String> {

}
