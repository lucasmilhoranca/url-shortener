package tech.run.urlshortener.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tech.run.urlshortener.entity.UrlEntity;

public interface UrlRepository extends MongoRepository<UrlEntity, String> {
}
