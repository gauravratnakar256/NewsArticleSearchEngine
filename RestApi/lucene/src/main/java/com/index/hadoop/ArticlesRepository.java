package com.index.hadoop;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticlesRepository extends MongoRepository<Articles, String> {


}
