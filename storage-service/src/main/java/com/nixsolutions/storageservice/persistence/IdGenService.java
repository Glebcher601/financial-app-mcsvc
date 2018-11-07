package com.nixsolutions.storageservice.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class IdGenService implements DbSequenceService
{
  @Autowired
  private ReactiveMongoTemplate mongoTemplate;

  @Override
  public Mono<Long> nextValue(String counterName)
  {
    return increaseSequence(counterName);
  }

  public Mono<Long> increaseSequence(String counterName)
  {
    Query query = new Query(Criteria.where("name").is(counterName));
    Update update = new Update().inc("value", 1);
    Mono<MongoSequence> counter = mongoTemplate.findAndModify(query, update, MongoSequence.class);

    return counter.map(MongoSequence::getValue);
  }
}
